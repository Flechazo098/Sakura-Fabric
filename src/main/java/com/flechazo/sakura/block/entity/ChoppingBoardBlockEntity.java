package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.api.ItemHandlerComponent;
import com.flechazo.sakura.block.machines.ChoppingBoardBlock;
import com.flechazo.sakura.recipes.ChoppingRecipe;
import com.flechazo.sakura.recipes.RecipeTypeRegistry;
import com.flechazo.sakura.utils.LevelUtils;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class ChoppingBoardBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandlerContainer inventory;
    private final LazyOptional<SlottedStackStorage> inputHandler;
    private ResourceLocation lastRecipeID;

    private int recipeTime;
    private int recipeTimeTotal;

    public ChoppingBoardBlockEntity (BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CHOPPING_BOARD, pos, state);
        inventory = createHandler();
        inputHandler = LazyOptional.of(() -> inventory);
    }

    // 创建物品处理组件
    public static ItemHandlerComponent createItemHandlerComponent(ChoppingBoardBlockEntity blockEntity) {
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
    @Override
    public void load (CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        recipeTime = compound.getInt("RecipeTime");
        recipeTimeTotal = compound.getInt("RecipeTimeTotal");
    }

    @Override
    public void saveAdditional (CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.putInt("RecipeTime", this.recipeTime);
        compound.putInt("RecipeTimeTotal", this.recipeTimeTotal);
    }

    public int getRecipeTime () {
        return recipeTime;
    }

    public boolean processStoredItemUsingTool (ItemStack toolStack, @Nullable Player player) {
        if (level == null)
            return false;

        Optional<ChoppingRecipe> matchingRecipe = getMatchingRecipe(inventory, toolStack, player);

        matchingRecipe.ifPresent(recipe -> {
            this.recipeTimeTotal = recipe.getRecipeTime();

            List<ItemStack> results = recipe.rollByproducts(level.random,
                    EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, toolStack));
            for (ItemStack resultStack : results) {
                Direction direction = getBlockState().getValue(ChoppingBoardBlock.FACING).getCounterClockWise();
                LevelUtils.spawnItemEntity(level, resultStack.copy(),
                        worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2,
                        worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2), direction.getStepX() * 0.2F, 0.0F,
                        direction.getStepZ() * 0.2F);
            }
            if (player != null) {
                toolStack.hurtAndBreak(1, player, (user) -> user.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            } else {
                if (toolStack.hurt(1, level.random, null)) {
                    toolStack.setCount(0);
                }
            }
            playProcessingSound(toolStack, getStoredItem());
            if (this.recipeTime < recipeTimeTotal - 1) {
                this.recipeTime++;
            } else {
                if (! setResult(recipe))
                    removeItem();
            }
        });

        return matchingRecipe.isPresent();
    }

    private Optional<ChoppingRecipe> getMatchingRecipe (ItemStackHandlerContainer recipeWrapper, ItemStack toolStack,
                                                        @Nullable Player player) {
        if (level == null)
            return Optional.empty();

        if (lastRecipeID != null) {
            ChoppingRecipe recipe = level.getRecipeManager()
                    .getAllRecipesFor(RecipeTypeRegistry.CHOPPING_RECIPE_TYPE).stream()
                    .filter(now -> now.getId().equals(lastRecipeID)).findFirst().get();
            if (recipe instanceof ChoppingRecipe && recipe.matches(recipeWrapper, level)
                    && recipe.getTool().test(toolStack)) {
                return Optional.of(recipe);
            }
        }

        List<ChoppingRecipe> recipeList = level.getRecipeManager()
                .getRecipesFor(RecipeTypeRegistry.CHOPPING_RECIPE_TYPE, recipeWrapper, level);
        if (recipeList.isEmpty()) {
            if (player != null)
                player.displayClientMessage(Component.translatable("sakura.block.chopping_board.invalid_item"), true);
            return Optional.empty();
        }
        Optional<ChoppingRecipe> recipe = recipeList.stream()
                .filter(cuttingRecipe -> cuttingRecipe.getTool().test(toolStack)).findFirst();
        if (! recipe.isPresent()) {
            if (player != null)
                player.displayClientMessage(Component.translatable("sakura.block.chopping_board.invalid_tool"), true);
            return Optional.empty();
        }
        lastRecipeID = recipe.get().getId();
        return recipe;
    }

    public void playProcessingSound (ItemStack tool, ItemStack boardItem) {
        if (tool.is(Tags.Items.SHEARS)) {
            playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
        } else if (boardItem.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            SoundType soundType = block.defaultBlockState().getSoundType();
            playSound(soundType.getBreakSound(), 1.0F, 0.8F);
        } else {
            playSound(SoundEvents.WOOD_HIT, 1.0F, 0.8F);
        }
    }

    public void playSound (SoundEvent sound, float volume, float pitch) {
        if (level != null)
            level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F,
                    sound, SoundSource.BLOCKS, volume, pitch);
    }

    public boolean addItem (ItemStack itemStack) {
        if (isEmpty() && ! itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public boolean setResult (ChoppingRecipe recipe) {
        ItemStack resultItem = recipe.getResultItem(null);
        if (! resultItem.isEmpty()) {
            if (resultItem.getCount() > 1) {
                for (int i = 1; i < resultItem.getCount(); i++) {
                    Direction direction = getBlockState().getValue(ChoppingBoardBlock.FACING).getCounterClockWise();
                    LevelUtils.spawnItemEntity(level, resultItem.copy().split(1),
                            worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2,
                            worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2), direction.getStepX() * 0.2F, 0.0F,
                            direction.getStepZ() * 0.2F);
                }
            }
            inventory.setStackInSlot(0, resultItem.copy().split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem () {
        if (! isEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public SlottedStackStorage getInventory () {
        return inventory;
    }

    public ItemStack getStoredItem () {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty () {
        return inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    protected void inventoryChanged () {
        this.recipeTime = 0;
        super.inventoryChanged();
    }

    @Override
    public void setRemoved () {
        super.setRemoved();
    }

    private ItemStackHandlerContainer createHandler () {
        return new ItemStackHandlerContainer(1) { // 初始槽位数为1
            @Override
            public int getSlotLimit (int slot) {
                return 1; // 保持每个槽位最大堆叠数为1
            }

            @Override
            protected void onContentsChanged (int slot) {
                inventoryChanged();
            }
        };
    }
}
