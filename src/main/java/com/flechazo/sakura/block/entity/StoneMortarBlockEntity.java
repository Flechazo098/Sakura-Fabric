package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.api.ItemHandlerComponent;
import com.flechazo.sakura.container.StoneMortarContainer;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.inventory.StoneMortarItemHandler;
import com.flechazo.sakura.recipes.RecipeTypeRegistry;
import com.flechazo.sakura.recipes.StoneMortarRecipe;
import com.flechazo.sakura.utils.LevelUtils;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

public class StoneMortarBlockEntity extends SyncedBlockEntity implements MenuProvider {

    private final ItemStackHandlerContainer inventory;
    private final LazyOptional<SlottedStackStorage> inputHandler;
    private final LazyOptional<SlottedStackStorage> outputHandler;

    protected final ContainerData tileData;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

    private int recipeTime;
    private int recipeTimeTotal;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public StoneMortarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.STONE_MORTAR, pos, state);

        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new StoneMortarItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new StoneMortarItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
        this.experienceTracker = new Object2IntOpenHashMap<>();
    }

    // 创建物品处理组件
    public static ItemHandlerComponent createItemHandlerComponent(StoneMortarBlockEntity blockEntity) {
        return new ItemHandlerComponent() {
            @Override
            public void readFromNbt(CompoundTag compoundTag) {
                // 从NBT中读取物品栏数据
                if (compoundTag.contains("Inventory")) {
                    blockEntity.inventory.deserializeNBT(compoundTag.getCompound("Inventory"));
                }
                if (compoundTag.contains("RecipeTime")) {
                    blockEntity.recipeTime = compoundTag.getInt("RecipeTime");
                }
                if (compoundTag.contains("RecipeTimeTotal")) {
                    blockEntity.recipeTimeTotal = compoundTag.getInt("RecipeTimeTotal");
                }
                if (compoundTag.contains("RecipesUsed")) {
                    CompoundTag recipesUsed = compoundTag.getCompound("RecipesUsed");
                    for (String key : recipesUsed.getAllKeys()) {
                        blockEntity.experienceTracker.put(new ResourceLocation(key), recipesUsed.getInt(key));
                    }
                }
            }

            @Override
            public void writeToNbt(CompoundTag compoundTag) {
                // 将物品栏数据写入NBT
                compoundTag.put("Inventory", blockEntity.inventory.serializeNBT());
                compoundTag.putInt("RecipeTime", blockEntity.recipeTime);
                compoundTag.putInt("RecipeTimeTotal", blockEntity.recipeTimeTotal);

                // 保存经验追踪数据
                CompoundTag recipesUsed = new CompoundTag();
                blockEntity.experienceTracker.forEach((recipeId, craftedAmount) ->
                        recipesUsed.putInt(recipeId.toString(), craftedAmount));
                compoundTag.put("RecipesUsed", recipesUsed);
            }

            @Override
            public SlottedStackStorage getItemHandler(Direction direction) {
                return blockEntity.inventory;
            }
        };
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, StoneMortarBlockEntity blockEntity) {
        boolean didInventoryChange = false;

        if (blockEntity.hasInput()) {
            Optional<StoneMortarRecipe> recipe = blockEntity
                    .getMatchingRecipe(blockEntity.inventory);
            if (recipe.isPresent() && blockEntity.canWork(recipe.get())) {
                didInventoryChange = blockEntity.processRecipe(recipe.get());
            } else {
                blockEntity.recipeTime = 0;
            }
        } else if (blockEntity.recipeTime > 0) {
            blockEntity.recipeTime = 0;
        }

        if (didInventoryChange) {
            blockEntity.inventoryChanged();
        }
    }

    private boolean hasInput() {
        for (int i = 0; i < 4; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Optional<StoneMortarRecipe> getMatchingRecipe(ItemStackHandlerContainer inventoryWrapper) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipeID != null) {
            StoneMortarRecipe recipe = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.STONE_MORTAR_RECIPE_TYPE).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst().get();
            if (recipe instanceof StoneMortarRecipe) {
                if (recipe.matches(inventoryWrapper, level)) {
                    return Optional.of(recipe);
                }
            }
        }

        if (checkNewRecipe) {
            Optional<StoneMortarRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeTypeRegistry.STONE_MORTAR_RECIPE_TYPE,
                    inventoryWrapper, level);
            if (recipe.isPresent()) {
                lastRecipeID = recipe.get().getId();
                return recipe;
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    protected boolean canWork(StoneMortarRecipe recipe) {
        if (hasInput()) {
            boolean check_extra = false;
            ItemStack resultStack = recipe.getResultItem(null);

            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outStack = inventory.getStackInSlot(4);
                if (outStack.isEmpty()) {
                    check_extra = true;
                } else if (!ItemStack.isSameItem(outStack, resultStack)) {
                    return false;
                } else {
                    check_extra = outStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }

                ItemStack resultExtraStack = recipe.getResultItemList().size() > 1 ? recipe.getResultItemList().get(1)
                        : ItemStack.EMPTY;
                if (resultExtraStack.isEmpty()) {
                    return check_extra;
                } else if (check_extra) {
                    ItemStack extraStack = inventory.getStackInSlot(5);
                    if (extraStack.isEmpty()) {
                        return true;
                    } else if (!ItemStack.isSameItem(extraStack, resultExtraStack)) {
                        return false;
                    } else {
                        return extraStack.getCount() + resultExtraStack.getCount() <= resultExtraStack
                                .getMaxStackSize();
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private boolean processRecipe(StoneMortarRecipe recipe) {
        if (level == null) {
            return false;
        }

        ++recipeTime;
        recipeTimeTotal = recipe.getRecipeTime();
        if (recipeTime < recipeTimeTotal) {
            return false;
        }

        recipeTime = 0;

        ItemStack resultStack = recipe.getResultItem(null);
        ItemStack outStack = inventory.getStackInSlot(4);
        ItemStack extraStack = inventory.getStackInSlot(5);
        ItemStack resultExtraStack = recipe.getResultItemList().size() > 1 ? recipe.getResultItemList().get(1)
                : ItemStack.EMPTY;
        if (outStack.isEmpty()) {
            inventory.setStackInSlot(4, resultStack.copy());
        } else if (ItemStack.isSameItem(outStack, resultStack)) {
            outStack.grow(resultStack.getCount());
        }
        if (!resultExtraStack.isEmpty()) {
            if (extraStack.isEmpty()) {
                inventory.setStackInSlot(5, resultExtraStack.copy());
            } else if (ItemStack.isSameItem(extraStack, resultExtraStack)) {
                extraStack.grow(resultExtraStack.getCount());
            }
        }

        trackRecipeExperience(recipe);

        for (int i = 0; i < 4; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.getItem().hasCraftingRemainingItem()) {
                double x = worldPosition.getX() + 0.5;
                double y = worldPosition.getY() + 0.7;
                double z = worldPosition.getZ() + 0.5;
                LevelUtils.spawnItemEntity(level, slotStack.getItem().getCraftingRemainingItem().getDefaultInstance(), x, y, z, 0F, 0.25F,
                        0F);
            }
            if (!slotStack.isEmpty()) {
                slotStack.shrink(1);
            }
        }
        return true;
    }

    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            experienceTracker.addTo(recipeID, 1);
        }
    }

    public void clearUsedRecipes(Player player) {
        grantStoredRecipeExperience(player.level(), player.position());
        experienceTracker.clear();
    }

    public void grantStoredRecipeExperience(Level world, Vec3 pos) {
        for (Object2IntMap.Entry<ResourceLocation> entry : experienceTracker.object2IntEntrySet()) {
            world.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> LevelUtils.splitAndSpawnExperience(world,
                    pos, entry.getIntValue(), ((StoneMortarRecipe) recipe).getExperience()));
        }
    }
    public ItemStackHandler getInventory() {
        return inventory;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 6; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("RecipeTime", recipeTime);
        compound.putInt("RecipeTimeTotal", recipeTimeTotal);
        compound.put("Inventory", inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker
                .forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ItemStackHandlerContainer createHandler() {
        return new ItemStackHandlerContainer(6) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < 4) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return StoneMortarBlockEntity.this.recipeTime;
                    case 1:
                        return StoneMortarBlockEntity.this.recipeTimeTotal;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        StoneMortarBlockEntity.this.recipeTime = value;
                        break;
                    case 1:
                        StoneMortarBlockEntity.this.recipeTimeTotal = value;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new StoneMortarContainer(id, player, this, tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.stone_mortar");
    }

    @Environment(EnvType.CLIENT)
    public int getRotation() {
        return this.recipeTime != 0 ? 360 * this.recipeTime / this.recipeTimeTotal : 0;
    }

}