package com.flechazo.sakura.item;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.fluid.BucketItemRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
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
                        // 添加方块物品
                        Arrays.stream(BlockItemRegistry.class.getDeclaredFields())
                                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                                .forEach(field -> {
                                    try {
                                        output.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                                    } catch (Exception e) {
                                        SakuraFabric.LOGGER.error("Error adding block item to creative tab", e);
                                    }
                                });

                        // 添加普通物品
                        Arrays.stream(ItemRegistry.class.getDeclaredFields())
                                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                                .forEach(field -> {
                                    try {
                                        output.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                                    } catch (Exception e) {
                                        SakuraFabric.LOGGER.error("Error adding item to creative tab", e);
                                    }
                                });

                        // 添加食物物品
                        FoodRegistry.FOODSET.values().forEach(item -> output.accept(new ItemStack(item)));
                        FoodRegistry.CUISINES.values().forEach(item -> output.accept(new ItemStack(item)));

                        // 添加桶物品
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

    // 初始化方法，添加物品到原版创造模式标签页
    public static void init() {
        // 添加桶物品到原版的"材料"标签页
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {
            // 添加所有桶物品到材料标签页
            Arrays.stream(BucketItemRegistry.class.getDeclaredFields())
                    .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                    .forEach(field -> {
                        try {
                            content.accept(new ItemStack((net.minecraft.world.item.Item) field.get(null)));
                        } catch (Exception e) {
                            SakuraFabric.LOGGER.error("Error adding bucket item to ingredients tab", e);
                        }
                    });
        });

        SakuraFabric.LOGGER.info("注册创造模式物品栏");
    }
}