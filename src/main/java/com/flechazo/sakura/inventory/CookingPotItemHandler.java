package com.flechazo.sakura.inventory;

import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Iterator;

public class CookingPotItemHandler implements SlottedStackStorage {
    private static final int SLOTS_INPUT = 9;
    private static final int SLOT_OUTPUT = 9;

    private final SlottedStackStorage storage;
    private final Direction direction;

    public CookingPotItemHandler(SlottedStackStorage storage, @Nullable Direction direction) {
        this.storage = storage;
        this.direction = direction;
    }

    @Override
    public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 只允许从上方插入输入槽（0-8）
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.insertSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 输出槽（9号槽）可以从任意方向提取，输入槽只能从上方提取
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        } else {
            if (slot == SLOT_OUTPUT) {
                return storage.extractSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
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
    public int getSlotCount() {
        return storage.getSlotCount();
    }

    @Override
    public SingleSlotStorage<ItemVariant> getSlot(int slot) {
        return storage.getSlot(slot);
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        // 根据方向限制只允许从上方插入输入槽
        if (direction != null && direction != Direction.UP) {
            return 0;
        }

        long totalInserted = 0;
        // 遍历所有输入槽（0-8）
        for (int slot = 0; slot < SLOTS_INPUT; slot++) {
            // 检查槽位是否有效
            if (slot >= getSlotCount()) break;

            // 检查物品是否有效
            if (!storage.isItemValid(slot, resource, (int) Math.min(maxAmount, Integer.MAX_VALUE))) {
                continue;
            }

            // 计算剩余可插入数量
            long remaining = maxAmount - totalInserted;
            if (remaining <= 0) break;

            // 执行插入操作
            long inserted = storage.insertSlot(slot, resource, remaining, transaction);
            totalInserted += inserted;

            if (totalInserted >= maxAmount) break;
        }
        return totalInserted;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        long totalExtracted = 0;

        // 根据方向确定可提取的槽位范围
        if (direction == null || direction == Direction.UP) {
            // 允许从输入槽（0-8）提取
            for (int slot = 0; slot < SLOTS_INPUT; slot++) {
                totalExtracted += extractFromSlot(slot, resource, maxAmount - totalExtracted, transaction);
                if (totalExtracted >= maxAmount) break;
            }
        } else {
            // 只允许从输出槽（9号槽）提取
            totalExtracted += extractFromSlot(SLOT_OUTPUT, resource, maxAmount, transaction);
        }

        return totalExtracted;
    }

    // 辅助方法：从单个槽位提取
    private long extractFromSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        if (slot >= getSlotCount()) return 0;

        StorageView<ItemVariant> view = storage.getSlot(slot);
        if (!view.getResource().matches(resource.toStack())) return 0;

        return storage.extractSlot(slot, resource, maxAmount, transaction);
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        return storage.iterator();
    }

    // 事务包装方法
    public static ItemStack extractItemWrapper(SlottedStackStorage storage, int slot, int amount) {
        try (Transaction tx = Transaction.openOuter()) {
            long extracted = storage.extractSlot(
                    slot,
                    ItemVariant.of(storage.getStackInSlot(slot)),
                    amount,
                    tx
            );

            if (extracted > 0) {
                tx.commit();
                return storage.getStackInSlot(slot).copyWithCount((int) extracted);
            }
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack insertItemWrapper(SlottedStackStorage storage, int slot, ItemStack stack) {
        try (Transaction tx = Transaction.openOuter()) {
            long inserted = storage.insertSlot(
                    slot,
                    ItemVariant.of(stack),
                    stack.getCount(),
                    tx
            );

            if (inserted > 0) {
                tx.commit();
                return stack.copyWithCount(stack.getCount() - (int) inserted);
            }
            return stack;
        }
    }
}