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

    @Override
    public int getSlotCount() {
        return storage.getSlotCount();
    }

    @Override
    public SingleSlotStorage<ItemVariant> getSlot(int slot) {
        return storage.getSlot(slot);
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 如果没有指定方向或者是从上方插入，则尝试插入到输入槽
        if (direction == null || direction == Direction.UP) {
            long inserted = 0;
            // 尝试按顺序插入到每个输入槽
            for (int slot = 0; slot < SLOTS_INPUT; slot++) {
                inserted += storage.insertSlot(slot, resource, maxAmount - inserted, transaction);
                if (inserted >= maxAmount) {
                    break;
                }
            }
            return inserted;
        }
        return 0; // 其他方向不允许插入
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 如果没有指定方向或者是从上方提取，则允许从输入槽提取
        if (direction == null || direction == Direction.UP) {
            long extracted = 0;
            // 尝试从每个输入槽提取
            for (int slot = 0; slot < SLOTS_INPUT; slot++) {
                extracted += storage.extractSlot(slot, resource, maxAmount - extracted, transaction);
                if (extracted >= maxAmount) {
                    break;
                }
            }
            return extracted;
        } else {
            // 从侧面只能提取输出槽
            long extracted = storage.extractSlot(SLOT_OUTPUT, resource, maxAmount, transaction);
            if (extracted < maxAmount) {
                extracted += storage.extractSlot(SLOT_OUTPUT_EXTRA, resource, maxAmount - extracted, transaction);
            }
            return extracted;
        }
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        return storage.iterator();
        }
}
