package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.fluid.BucketItemRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import com.flechazo.sakura.item.enums.SakuraNormalItemSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class CreativeModeTabRegistry {

    public static final CreativeModeTab GROUP = registerCreativeModeTab("sakura",
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI)))
                    .title(Component.translatable("itemGroup.sakura"))
                    .displayItems((parameters, output) -> {
                        Arrays.stream(BlockItemRegistry.class.getDeclaredFields())
                                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                                .forEach(field -> {
                                    try {
                                        output.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                                    } catch (Exception e) {
                                        SakuraFabric.LOGGER.error("Error adding block item to creative tab", e);
                                    }
                                });

                        Arrays.stream(ItemRegistry.class.getDeclaredFields())
                                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                                .forEach(field -> {
                                    try {
                                        output.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                                    } catch (Exception e) {
                                        SakuraFabric.LOGGER.error("Error adding item to creative tab", e);
                                    }
                                });

                        for (SakuraNormalItemSet itemSet : SakuraNormalItemSet.values()) {
                            output.accept(new ItemStack(ItemRegistry.MATERIALS.get(itemSet)));
                        }

                        FoodRegistry.FOODSET.values().forEach(item -> output.accept(new ItemStack(item)));
                        FoodRegistry.CUISINES.values().forEach(item -> output.accept(new ItemStack(item)));

                        Arrays.stream(BucketItemRegistry.class.getDeclaredFields())
                                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                                .forEach(field -> {
                                    try {
                                        output.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                                    } catch (Exception e) {
                                        SakuraFabric.LOGGER.error("Error adding bucket item to creative tab", e);
                                    }
                                });
                    })
                    .build());

    private static CreativeModeTab registerCreativeModeTab(String name, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(SakuraFabric.MODID, name), tab);
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            Arrays.stream(BucketItemRegistry.class.getDeclaredFields())
                    .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                    .forEach(field -> {
                        try {
                            content.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                        } catch (Exception e) {
                            SakuraFabric.LOGGER.error("Error adding bucket item to ingredients tab", e);
                        }
                    });

            for (SakuraNormalItemSet itemSet : SakuraNormalItemSet.values()) {
                content.accept(new ItemStack(ItemRegistry.MATERIALS.get(itemSet)));
            }
        });

//        SakuraFabric.LOGGER.info("注册创造模式物品栏");
    }
}