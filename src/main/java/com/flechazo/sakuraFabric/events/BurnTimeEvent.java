package com.flechazo.sakuraFabric.events;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

@Mod.EventBusSubscriber()
public class BurnTimeEvent {
    @SubscribeEvent
    public static void registerBurnTime(FurnaceFuelBurnTimeEvent event) {
        register(event, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO), 400);
        register(event, BlockItemRegistry.BAMBOO_BLOCK, 4000);
        register(event, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT), 400);
        register(event, BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT, 4000);
        register(event, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL), 1600);
        register(event, BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK, 16000);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, Supplier<? extends Item> item, int burnTime) {
        register(event, item.get(), burnTime);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, Item item, int burnTime) {
        register(event, new ItemStack(item), burnTime);
    }

    private static void register(FurnaceFuelBurnTimeEvent event, ItemStack item, int burnTime) {
        if (item.getItem() == event.getItemStack().getItem())
            event.setBurnTime(burnTime);
    }
}
