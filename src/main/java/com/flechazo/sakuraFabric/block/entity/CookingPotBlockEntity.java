package com.flechazo.sakuraFabric.block.entity;

import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.block.machines.CookingPotBlock;
import com.flechazo.sakuraFabric.container.CookingPotContainer;
import com.flechazo.sakuraFabric.inventory.CookingPotItemHandler;
import com.flechazo.sakuraFabric.recipes.CookingPotRecipe;
import com.flechazo.sakuraFabric.recipes.RecipeTypeRegistry;
import com.flechazo.sakuraFabric.utils.FluidAction;
import com.flechazo.sakuraFabric.utils.FluidIngredient;
import com.flechazo.sakuraFabric.utils.LevelUtils;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CookingPotBlockEntity extends SyncedBlockEntity implements MenuProvider, HeatableBlockEntity {

    public static final int TANK_CAPACITY = 8000;
    private final ItemStackHandler inventory;
    private LazyOptional<SlottedStackStorage > inputHandler;
    private LazyOptional<SlottedStackStorage> outputHandler;

    private LazyOptional<FluidTank> fluidTank;
    protected final ContainerData tileData;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

    private int recipeTime;
    private int recipeTimeTotal;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public CookingPotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.COOKING_POT, pos, state);

        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new CookingPotItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new CookingPotItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
        this.fluidTank = LazyOptional.of(this::createFluidHandler);
        this.experienceTracker = new Object2IntOpenHashMap<>();
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, CookingPotBlockEntity blockEntity) {
        boolean didInventoryChange = false;
        if (blockEntity.isHeated(level, pos) && blockEntity.hasInput()) {
            Optional<CookingPotRecipe> recipe = blockEntity.getMatchingRecipe(new RecipeWrapper(blockEntity.inventory));
            if (recipe.isPresent() && blockEntity.canWork(recipe.get(),level)) {
                didInventoryChange = blockEntity.processRecipe(recipe.get(),level);
            } else {
                blockEntity.recipeTime = 0;
            }
        } else if (blockEntity.recipeTime > 0) {
            if (state.is(BlockRegistry.COOKING_POT))
                state.setValue(CookingPotBlock.OPEN, true);
            blockEntity.recipeTime = 0;
        }

        if (didInventoryChange) {
            blockEntity.inventoryChanged();
        }
    }

    private boolean hasInput() {
        for (int i = 0; i < 9; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private Optional<CookingPotRecipe> getMatchingRecipe(Container inventoryWrapper) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipeID != null) {
            Recipe<Container> recipe = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst().get();
            if (recipe instanceof CookingPotRecipe cookingRecipe) {
                if (cookingRecipe.matchesWithFluid(this.fluidTank.orElse(new FluidTank(0)).getFluid(), inventoryWrapper,
                        level)) {
                    return Optional.of(cookingRecipe);
                }
            }
        }

        if (checkNewRecipe) {
            List<CookingPotRecipe> recipes = level.getRecipeManager().getRecipesFor(RecipeTypeRegistry.COOKING_RECIPE_TYPE,
                    inventoryWrapper, level);
            for(CookingPotRecipe recipe : recipes) {
                if(recipe.matchesWithFluid(this.fluidTank.orElse(new FluidTank(0)).getFluid(),
                        inventoryWrapper, level)) {
                    lastRecipeID = recipe.getId();
                    return Optional.of(recipe);
                }
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    protected boolean canWork(CookingPotRecipe recipe,Level level) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem(level.registryAccess());
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack outputStack = inventory.getStackInSlot(9);
                if (outputStack.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(outputStack, resultStack)) {
                    return false;
                } else if (outputStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(9)) {
                    return true;
                } else {
                    return outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean processRecipe(CookingPotRecipe recipe, Level level) {
        if (level == null) {
            return false;
        }

        ++recipeTime;
        recipeTimeTotal = recipe.getRecipeTime();
        if (recipeTime < recipeTimeTotal) {
            return false;
        }

        recipeTime = 0;

        ItemStack resultStack = recipe.getResultItem(level.registryAccess());
        ItemStack outStack = inventory.getStackInSlot(9);

        if (outStack.isEmpty()) {
            inventory.setStackInSlot(9, resultStack.copy());
        } else if (ItemStack.isSameItem(outStack, resultStack)) {
            outStack.grow(resultStack.getCount());
        }

        // 修复流体消耗逻辑
        if(recipe.getRequiredFluid() != FluidIngredient.EMPTY) {
            FluidTank tank = this.fluidTank.orElse(new FluidTank(0));
            // 使用 drain 方法的替代实现
            int amount = recipe.getRequiredFluid().getRequiredAmount();
            if (tank.getFluidAmount() >= amount) {
                tank.setFluid(new FluidStack(tank.getFluid().getFluid(), tank.getFluidAmount() - amount));
            }
        }

        trackRecipeExperience(recipe);

        for (int i = 0; i < 9; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            // 修复合成剩余物品逻辑
            if (hasRecipeRemainder(slotStack)) {
                double x = worldPosition.getX() + 0.5;
                double y = worldPosition.getY() + 0.7;
                double z = worldPosition.getZ() + 0.5;
                LevelUtils.spawnItemEntity(level, getRecipeRemainder(slotStack), x, y, z, 0F, 0.25F, 0F);
            }
            if (!slotStack.isEmpty()) {
                slotStack.shrink(1);
            }
        }
        return true;
    }

    // 添加辅助方法检查物品是否有合成剩余物
    private boolean hasRecipeRemainder(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().hasCraftingRemainingItem();
    }

    // 添加辅助方法获取合成剩余物
    private ItemStack getRecipeRemainder(ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;

        Item item = stack.getItem();
        if (item.hasCraftingRemainingItem()) {
            return new ItemStack(item.getCraftingRemainingItem());
        }
        return ItemStack.EMPTY;
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
                    pos, entry.getIntValue(), ((CookingPotRecipe) recipe).getExperience()));
        }
    }

    public FluidTank getFluidHandler() {
        return fluidTank.orElse(null);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 10; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
        fluidTank.invalidate();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
        fluidTank.ifPresent(fluid -> fluid.readFromNBT(compound.getCompound("FluidTank")));
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        CompoundTag nbt = new CompoundTag();
        compound.putInt("RecipeTime", recipeTime);
        compound.putInt("RecipeTimeTotal", recipeTimeTotal);
        compound.put("Inventory", inventory.serializeNBT());
        fluidTank.ifPresent(fluid -> compound.put("FluidTank", fluid.writeToNBT(nbt)));
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker
                .forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(10) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < 9) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    // 修改 FluidTank 创建方法
    private FluidTank createFluidHandler() {
        return new FluidTank(TANK_CAPACITY) {
            @Override
            public void onContentsChanged() {
                checkNewRecipe = true;
                inventoryChanged();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid().getFluidType() != null &&
                        !stack.getFluid().getFluidType().isLighterThanAir();
            }

            // 添加 drain 方法的替代实现
            public FluidStack drain(int maxDrain, FluidAction action) {
                long drainAmount = Math.min(getFluidAmount(), maxDrain);
                if (drainAmount <= 0) return FluidStack.EMPTY;

                FluidStack stack = new FluidStack(getFluid().getFluid(), drainAmount);
                if (action.execute()) {
                    setFluid(new FluidStack(getFluid().getFluid(), getFluidAmount() - drainAmount));
                    onContentsChanged();
                }
                return stack;
            }

            // 添加 fill 方法的替代实现
            public long fill(FluidStack resource, FluidAction action) {
                if (resource.isEmpty() || !isFluidValid(resource)) return 0;

                long fillAmount = Math.min(getSpace(), resource.getAmount());
                if (fillAmount <= 0) return 0;

                if (action.execute()) {
                    if (isEmpty()) {
                        setFluid(new FluidStack(resource.getFluid(), fillAmount));
                    } else {
                        setFluid(new FluidStack(getFluid().getFluid(), getFluidAmount() + fillAmount));
                    }
                    onContentsChanged();
                }
                return fillAmount;
            }

            // 获取可用空间
            public long getSpace() {
                return getCapacity() - getFluidAmount();
            }
        };
    }
    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CookingPotBlockEntity.this.recipeTime;
                    case 1 -> CookingPotBlockEntity.this.recipeTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        CookingPotBlockEntity.this.recipeTime = value;
                        break;
                    case 1:
                        CookingPotBlockEntity.this.recipeTimeTotal = value;

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
        return new CookingPotContainer(id, player, this, this.tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.cooking_pot");
    }

    public boolean isHeated() {
        if (level == null) {
            return false;
        }
        return this.isHeated(level, worldPosition);
    }

    public LazyOptional<FluidTank> getFluidTank() {
        return fluidTank;
    }

    @Override
    public void inventoryChanged() {
        super.inventoryChanged();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputHandler.invalidate();
        outputHandler.invalidate();
        fluidTank.invalidate();
    }

}
