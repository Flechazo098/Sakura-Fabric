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
        if (direction == null || direction == Direction.UP) {
            if (slot < SLOTS_INPUT) {
                return storage.insertSlot(slot, resource, maxAmount, transaction);
            }
        }
        return 0;
    }

    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
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
        if (direction != null && direction != Direction.UP) {
            return 0;
        }

        long totalInserted = 0;
        for (int slot = 0; slot < SLOTS_INPUT; slot++) {
            if (slot >= getSlotCount()) break;

            if (!storage.isItemValid(slot, resource, (int) Math.min(maxAmount, Integer.MAX_VALUE))) {
                continue;
            }

            long remaining = maxAmount - totalInserted;
            if (remaining <= 0) break;

            long inserted = storage.insertSlot(slot, resource, remaining, transaction);
            totalInserted += inserted;

            if (totalInserted >= maxAmount) break;
        }
        return totalInserted;
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        long totalExtracted = 0;

        if (direction == null || direction == Direction.UP) {
            for (int slot = 0; slot < SLOTS_INPUT; slot++) {
                totalExtracted += extractFromSlot(slot, resource, maxAmount - totalExtracted, transaction);
                if (totalExtracted >= maxAmount) break;
            }
        } else {
            totalExtracted += extractFromSlot(SLOT_OUTPUT, resource, maxAmount, transaction);
        }

        return totalExtracted;
    }

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
}