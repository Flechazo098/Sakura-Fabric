package com.flechazo.sakura.core.events;

import com.flechazo.sakura.init.BlockItemRegistry;
import com.flechazo.sakura.init.ItemRegistry;
import com.flechazo.sakura.item.enums.SakuraNormalItemSet;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class BurnTimeEvent {
    public static void registerBurnTime() {
        register(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO), 400);
        register(BlockItemRegistry.BAMBOO_BLOCK, 4000);
        register(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT), 400);
        register(BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT, 4000);
        register(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL), 1600);
        register(BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK, 16000);
    }

    private static void register(Supplier<? extends Item> item, int burnTime) {
        register(item.get(), burnTime);
    }

    private static void register(Item item, int burnTime) {
        FuelRegistry.INSTANCE.add(item, burnTime);
    }
}