package com.flechazo.sakuraFabric.inventory;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Iterator;

public class StoneMortarItemHandler implements SlottedStackStorage {
    private static final int SLOTS_INPUT = 4;
    private static final int SLOT_OUTPUT = 4;
    private static final int SLOT_OUTPUT_EXTRA = 5;

    private final SlottedStackStorage storage;
    private final Direction direction;

    public StoneMortarItemHandler(SlottedStackStorage storage, @Nullable Direction direction) {
        this.storage = storage;
        this.direction = direction;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return storage.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        storage.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlotLimit(int slot) {
        return storage.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, int count) {
        return storage.isItemValid(slot, resource, count);
    }

    @Override
    public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 限制输入方向：只能从上方插入输入槽
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.insertSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 输出限制：只能从侧面提取输出槽
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        } else {
            if (slot == SLOT_OUTPUT || slot == SLOT_OUTPUT_EXTRA) {
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    // 实现 SlottedStorage 接口的必须方法
    @Override
    public int getSlotCount() {
        return storage.getSlotCount();
    }

    @Override
    public SingleSlotStorage<ItemVariant> getSlot(int slot) {
        return storage.getSlot(slot);
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        return storage.iterator();
        }
}
