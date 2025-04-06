package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.api.FluidHandlerComponent;
import com.flechazo.sakura.api.ItemHandlerComponent;
import com.flechazo.sakura.container.FermenterContainer;
import com.flechazo.sakura.inventory.FermenterItemHandler;
import com.flechazo.sakura.recipes.FermenterRecipe;
import com.flechazo.sakura.recipes.RecipeTypeRegistry;
import com.flechazo.sakura.utils.FluidIngredient;
import com.flechazo.sakura.utils.LevelUtils;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class FermenterBlockEntity extends SyncedBlockEntity implements MenuProvider {

    public static final int TANK_CAPACITY = 8000;
    private final ItemStackHandlerContainer inventory;
    private LazyOptional<SlottedStackStorage> inputHandler;
    private LazyOptional<SlottedStackStorage> outputHandler;

    private LazyOptional<FluidTank> inputfluidTank;
    private LazyOptional<FluidTank> outputfluidTank;
    protected final ContainerData tileData;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

    private int recipeTime;
    private int recipeTimeTotal;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public FermenterBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.FERMENTER, pos, state);

        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new FermenterItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new FermenterItemHandler(inventory, Direction.DOWN));
        this.tileData = createIntArray();
        this.inputfluidTank = LazyOptional.of(this::createInputFluidHandler);
        this.outputfluidTank = LazyOptional.of(this::createFluidHandler);
        this.experienceTracker = new Object2IntOpenHashMap<>();
    }

    // 创建物品处理组件
    public static ItemHandlerComponent createItemHandlerComponent(FermenterBlockEntity blockEntity) {
        return new ItemHandlerComponent() {
            @Override
            public void readFromNbt(CompoundTag compoundTag) {
                // 从NBT中读取物品栏数据
                if (compoundTag.contains("Inventory")) {
                    blockEntity.inventory.deserializeNBT(compoundTag.getCompound("Inventory"));
                }
            }

            @Override
            public void writeToNbt(CompoundTag compoundTag) {
                // 将物品栏数据写入NBT
                compoundTag.put("Inventory", blockEntity.inventory.serializeNBT());
            }

            @Override
            public SlottedStackStorage getItemHandler(Direction direction) {
                return blockEntity.inventory;
            }
        };
    }

    // 创建流体处理组件
    public static FluidHandlerComponent createFluidHandlerComponent(FermenterBlockEntity blockEntity) {
        return new FluidHandlerComponent() {
            @Override
            public void readFromNbt(CompoundTag compoundTag) {
                // 从NBT中读取流体罐数据
                if (compoundTag.contains("InputFluidTank")) {
                    blockEntity.inputfluidTank.ifPresent(tank ->
                            tank.readFromNBT(compoundTag.getCompound("InputFluidTank")));
                }
                if (compoundTag.contains("OutputFluidTank")) {
                    blockEntity.outputfluidTank.ifPresent(tank ->
                            tank.readFromNBT(compoundTag.getCompound("OutputFluidTank")));
                }
            }

            @Override
            public void writeToNbt(CompoundTag compoundTag) {
                // 将流体罐数据写入NBT
                blockEntity.inputfluidTank.ifPresent(tank -> {
                    CompoundTag inputTankTag = new CompoundTag();
                    tank.writeToNBT(inputTankTag);
                    compoundTag.put("InputFluidTank", inputTankTag);
                });

                blockEntity.outputfluidTank.ifPresent(tank -> {
                    CompoundTag outputTankTag = new CompoundTag();
                    tank.writeToNBT(outputTankTag);
                    compoundTag.put("OutputFluidTank", outputTankTag);
                });
            }

            @Override
            public FluidTank getFluidHandler(Direction direction) {
                if (direction == null || !(direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH))) {
                    return blockEntity.getInputFluidTank().orElse(new FluidTank(0));
                } else {
                    return blockEntity.getOutputFluidTank().orElse(new FluidTank(0));
                }
            }

            @Override
            public FluidTank getInputFluidHandler() {
                return blockEntity.getInputFluidTank().orElse(new FluidTank(0));
            }

            @Override
            public FluidTank getOutputFluidHandler() {
                return blockEntity.getOutputFluidTank().orElse(new FluidTank(0));
            }
        };
    }

    public static void workingTick(Level level, BlockPos pos, BlockState state, FermenterBlockEntity blockEntity) {
        boolean didInventoryChange = false;
        if (blockEntity.hasInput()) {
            Optional<FermenterRecipe> recipe = blockEntity.getMatchingRecipe(blockEntity.inventory);
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
        if(this.inputfluidTank.isPresent()) {
            return !this.inputfluidTank.orElse(new FluidTank(0)).isEmpty();
        }

        for (int i = 0; i < 3; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }
    private Optional<FermenterRecipe> getMatchingRecipe(ItemStackHandlerContainer inventoryWrapper) {
        if (level == null) {
            return Optional.empty();
        }

        if (lastRecipeID != null) {
            Recipe<ItemStackHandlerContainer> recipe = level.getRecipeManager()
                    .getAllRecipesFor(RecipeTypeRegistry.FERMENTER_RECIPE_TYPE).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst().get();
            if (recipe instanceof FermenterRecipe cookingRecipe) {
                if (cookingRecipe.matchesWithFluid(this.inputfluidTank.orElse(new FluidTank(0)).getFluid(),
                        inventoryWrapper, level)) {
                    return Optional.of(cookingRecipe);
                }
            }
        }

        if (checkNewRecipe) {
            List<FermenterRecipe> recipes = level.getRecipeManager()
                    .getRecipesFor(RecipeTypeRegistry.FERMENTER_RECIPE_TYPE, inventoryWrapper, level);
            for(FermenterRecipe recipe : recipes) {
                if (recipe.matchesWithFluid(
                        this.inputfluidTank.orElse(new FluidTank(0)).getFluid(), inventoryWrapper, level)) {
                    lastRecipeID = recipe.getId();
                    return Optional.of(recipe);
                }
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    protected boolean canWork(FermenterRecipe recipe) {
        if (hasInput()) {
            NonNullList<ItemStack> resultStacks = recipe.getResultItemList();
            boolean fluid_flag = !(recipe.getResultFluid().isEmpty());
            if (this.outputfluidTank.isPresent()) {
                FluidTank outTank = this.outputfluidTank.orElse(null);
                fluid_flag = (outTank.getFluid().isFluidEqual(recipe.getResultFluid())
                        && outTank.getSpace() >= recipe.getResultFluid().getAmount())
                        || outTank.isEmpty()
                        || recipe.getResultFluid().isEmpty();
            }
            if (resultStacks.size() <= 0) {
                return fluid_flag && recipe.getRequiredFluid() != FluidIngredient.EMPTY;
            } else {
                boolean flag = true;
                for (int i = 3; i < resultStacks.size() + 3; i++) {
                    if (!flag)
                        break;
                    ItemStack resultStack = resultStacks.get(i - 3);
                    ItemStack outputStack = inventory.getStackInSlot(i);
                    if (outputStack.isEmpty()) {
                        flag = true;
                    } else if (!ItemStack.isSameItem(outputStack, resultStack)) {
                        flag = false;
                    } else if (outputStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(i)) {
                        flag = true;
                    } else {
                        flag = outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                    }
                }
                return fluid_flag && flag;
            }
        } else {
            return false;
        }
    }

    private boolean processRecipe(FermenterRecipe recipe) {
        if (level == null) {
            return false;
        }

        ++recipeTime;
        recipeTimeTotal = recipe.getRecipeTime();
        if (recipeTime < recipeTimeTotal) {
            return false;
        }

        recipeTime = 0;

        NonNullList<ItemStack> resultStacks = recipe.getResultItemList();
        for (int i = 3; i < resultStacks.size() + 3; i++) {
            ItemStack outStack = inventory.getStackInSlot(i);
            if (outStack.isEmpty()) {
                inventory.setStackInSlot(i, resultStacks.get(i - 3).copy());
            } else if (ItemStack.isSameItem(outStack,resultStacks.get(i - 3))) {
                outStack.grow(resultStacks.get(i - 3).getCount());
            }
        }

        if (recipe.getRequiredFluid() != FluidIngredient.EMPTY) {
            FluidTank inputTank = this.inputfluidTank.orElse(new FluidTank(0));
            long requiredAmount = recipe.getRequiredFluid().getRequiredAmount();

            inputTank.extract(
                    inputTank.getFluid().getType(),
                    requiredAmount,
                    null
            );
        }

        if (!recipe.getResultFluid().isEmpty()) {
            FluidTank outputTank = this.outputfluidTank.orElse(new FluidTank(0));
            FluidStack resultFluid = recipe.getResultFluid();

            outputTank.insert(
                    resultFluid.getType(),
                    resultFluid.getAmount(),
                    null
            );
        }

        trackRecipeExperience(recipe);

        for (int i = 0; i < 3; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.getItem().hasCraftingRemainingItem()) {
                double x = worldPosition.getX() + 0.5;
                double y = worldPosition.getY() + 0.7;
                double z = worldPosition.getZ() + 0.5;
                LevelUtils.spawnItemEntity(level, inventory.getStackInSlot(i).getItem().getCraftingRemainingItem().getDefaultInstance(), x, y, z, 0F, 0.25F,
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
                    pos, entry.getIntValue(), ((FermenterRecipe) recipe).getExperience()));
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
        inputfluidTank.invalidate();
        outputfluidTank.invalidate();
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
        inputfluidTank.ifPresent(fluid -> fluid.readFromNBT(compound.getCompound("InputFluidTank")));
        outputfluidTank.ifPresent(fluid -> fluid.readFromNBT(compound.getCompound("OutputFluidTank")));
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
        inputfluidTank.ifPresent(fluid -> compound.put("InputFluidTank", fluid.writeToNBT(nbt)));
        CompoundTag nbt2 = new CompoundTag();
        outputfluidTank.ifPresent(fluid -> compound.put("OutputFluidTank", fluid.writeToNBT(nbt2)));
        CompoundTag compoundRecipes = new CompoundTag();
        experienceTracker
                .forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private ItemStackHandlerContainer createHandler() {
        return new ItemStackHandlerContainer(6) {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < 3) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    private FluidTank createFluidHandler() {
        return new FluidTank(TANK_CAPACITY) {
            @Override
            protected void onContentsChanged() {
                inventoryChanged();
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir();
            }

        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return FermenterBlockEntity.this.recipeTime;
                    case 1:
                        return FermenterBlockEntity.this.recipeTimeTotal;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        FermenterBlockEntity.this.recipeTime = value;
                        break;
                    case 1:
                        FermenterBlockEntity.this.recipeTimeTotal = value;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private FluidTank createInputFluidHandler() {
        return new FluidTank(TANK_CAPACITY) {
            @Override
            protected void onContentsChanged() {
                inventoryChanged();
                checkNewRecipe = true;
                super.onContentsChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return !stack.getFluid().getFluidType().isLighterThanAir();
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new FermenterContainer(id, player, this, this.tileData);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.sakura.fermenter");
    }

    public LazyOptional<FluidTank> getInputFluidTank() {
        return inputfluidTank;
    }

    public LazyOptional<FluidTank> getOutputFluidTank() {
        return outputfluidTank;
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        inputHandler.invalidate();
        outputHandler.invalidate();
        inputfluidTank.invalidate();
        outputfluidTank.invalidate();
    }
}