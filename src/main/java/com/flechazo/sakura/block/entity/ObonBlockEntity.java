package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.capability.ItemHandlerComponent;
import com.flechazo.sakura.init.BlockEntityRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ObonBlockEntity extends SyncedBlockEntity {
    private final ItemStackHandler inventory;
    private final LazyOptional<SlottedStackStorage> inputHandler;

    public ObonBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.OBON, pos, state);

        inventory = createHandler();
        inputHandler = LazyOptional.of(() -> inventory);
    }


    // 创建物品处理组件
    public static ItemHandlerComponent createItemHandlerComponent(ObonBlockEntity blockEntity) {
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
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Inventory", inventory.serializeNBT());
    }

    public boolean addItem(ItemStack itemStack) {
        if (isEmpty() && !itemStack.isEmpty()) {
            inventory.setStackInSlot(0, itemStack.split(1));
            inventoryChanged();
            return true;
        }
        return false;
    }

    public ItemStack removeItem() {
        if (!isEmpty()) {
            ItemStack item = getStoredItem().split(1);
            inventoryChanged();
            return item;
        }
        return ItemStack.EMPTY;
    }

    public SlottedStackStorage getInventory() {
        return inventory;
    }

    public ItemStack getStoredItem() {
        return inventory.getStackInSlot(0);
    }

    public boolean isEmpty() {
        return inventory.getStackInSlot(0).isEmpty();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler() {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }
}
