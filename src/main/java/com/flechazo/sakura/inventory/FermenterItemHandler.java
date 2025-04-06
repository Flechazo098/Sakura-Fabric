package com.flechazo.sakura.inventory;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Iterator;

public class FermenterItemHandler implements SlottedStackStorage {
    private static final int SLOTS_INPUT = 3;
    private static final int SLOT_OUTPUT_BEGIN = 3;
    private static final int SLOT_OUTPUT_END = 5;

    private final SlottedStackStorage storage;
    private final Direction direction;

    public FermenterItemHandler(SlottedStackStorage storage, @Nullable Direction direction) {
        this.storage = storage;
        this.direction = direction;
    }

    // 核心方法实现
    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 只允许从上方插入输入槽
        if (direction != null && direction != Direction.UP) {
            return 0;
        }

        long totalInserted = 0;
        for (int slot = 0; slot < SLOTS_INPUT; slot++) {
            if (slot >= getSlotCount()) break;

            long remaining = maxAmount - totalInserted;
            if (remaining <= 0) break;

            long inserted = insertSlot(slot, resource, remaining, transaction);
            totalInserted += inserted;
        }
        return totalInserted;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        long totalExtracted = 0;

        // 根据方向确定可提取槽位
        if (direction == null || direction == Direction.UP) {
            // 允许提取输入槽 (0-2)
            for (int slot = 0; slot < SLOTS_INPUT; slot++) {
                totalExtracted += extractFromSlot(slot, resource, maxAmount - totalExtracted, transaction);
            }
        } else {
            // 允许提取输出槽 (3-5)
            for (int slot = SLOT_OUTPUT_BEGIN; slot <= SLOT_OUTPUT_END; slot++) {
                if (slot >= getSlotCount()) break;
                totalExtracted += extractFromSlot(slot, resource, maxAmount - totalExtracted, transaction);
            }
        }
        return totalExtracted;
    }

    // 单槽插入实现
    @Override
    public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.insertSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    // 单槽提取实现
    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        } else {
            if (slot >= SLOT_OUTPUT_BEGIN && slot <= SLOT_OUTPUT_END) { // 修复原代码的条件错误
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    // 辅助方法：从指定槽位提取
    private long extractFromSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        StorageView<ItemVariant> view = getSlot(slot);
        if (view.getResource().matches(resource.toStack())) {
            return extractSlot(slot, resource, maxAmount, transaction);
        }
        return 0;
    }

    // 代理方法实现
    @Override public ItemStack getStackInSlot(int slot) { return storage.getStackInSlot(slot); }
    @Override public void setStackInSlot(int slot, ItemStack stack) { storage.setStackInSlot(slot, stack); }
    @Override public int getSlotLimit(int slot) { return storage.getSlotLimit(slot); }
    @Override public boolean isItemValid(int slot, ItemVariant resource, int count) { return storage.isItemValid(slot, resource, count); }
    @Override public int getSlotCount() { return storage.getSlotCount(); }
    @Override public SingleSlotStorage<ItemVariant> getSlot(int slot) { return storage.getSlot(slot); }
    @Override public Iterator<StorageView<ItemVariant>> iterator() { return storage.iterator(); }
}