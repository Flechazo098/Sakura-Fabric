package com.flechazo.sakura.loot_modifier;

import com.flechazo.sakura.item.ItemRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * 草方块掉落种子的战利品表修改器
 */
public class SeedsDrop {
    // 草方块掉落种子的战利品表ID
    private static final ResourceLocation GRASS_BLOCK_ID = new ResourceLocation("minecraft", "blocks/grass");
    // 高草丛掉落种子的战利品表ID
    private static final ResourceLocation TALL_GRASS_ID = new ResourceLocation("minecraft", "blocks/tall_grass");

    /**
     * 注册草方块和高草丛掉落种子的战利品表修改
     */
    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // 修改草方块和高草丛的战利品表
            if (GRASS_BLOCK_ID.equals(id) || TALL_GRASS_ID.equals(id)) {
                // 创建一个新的战利品池，添加种子
                LootPool.Builder seedsPool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ItemRegistry.CABBAGE_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.EGGPLANT_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.ONION_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.RADISH_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.TOMATO_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.RICE_SEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.RAPESEEDS)
                                .when(LootItemRandomChanceCondition.randomChance(0.08f)))
                        .add(LootItem.lootTableItem(ItemRegistry.TARO)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .add(LootItem.lootTableItem(ItemRegistry.BUCKWHEAT)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .add(LootItem.lootTableItem(ItemRegistry.SOYBEAN)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .add(LootItem.lootTableItem(ItemRegistry.RED_BEAN)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)));

                // 将新的战利品池添加到现有的战利品表中
                tableBuilder.withPool(seedsPool);
            }
        });
    }
}