package com.flechazo.sakura.loot_modifier;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.ItemRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * 草方块掉落种子的战利品表修改器
 */
public class SeedsDrop {
    // 草方块掉落种子的战利品表ID
    private static final ResourceLocation GRASS_ID = new ResourceLocation("minecraft", "blocks/grass");
    // 蕨类掉落种子的战利品表ID
    private static final ResourceLocation FERN_ID = new ResourceLocation("minecraft", "blocks/fern");
    // 高草丛掉落种子的战利品表ID
    private static final ResourceLocation TALL_GRASS_ID = new ResourceLocation("minecraft", "blocks/tall_grass");
    // 大型蕨类掉落种子的战利品表ID
    private static final ResourceLocation LARGE_FERN_ID = new ResourceLocation("minecraft", "blocks/large_fern");

    // 自定义种子掉落战利品表
    private static final ResourceLocation SEEDS_DROPS = new ResourceLocation(SakuraFabric.MODID, "blocks/seeds_drops");

    /**
     * 注册草方块和蕨类掉落种子的战利品表修改
     */
    public static void register() {
        // 创建种子掉落战利品表
        createSeedsDropsLootTable();

        // 注册战利品表修改事件
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // 修改草方块、蕨类、高草丛和大型蕨类的战利品表
            if (GRASS_ID.equals(id) || FERN_ID.equals(id) ||
                    TALL_GRASS_ID.equals(id) || LARGE_FERN_ID.equals(id)) {

                // 创建一个新的战利品池，添加种子
                LootPool.Builder seedsPool = createSeedsLootPool();

                // 将新的战利品池添加到现有的战利品表中
                tableBuilder.withPool(seedsPool);
            }
        });
    }

    /**
     * 创建种子掉落战利品表
     */
    private static void createSeedsDropsLootTable() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (SEEDS_DROPS.equals(id)) {
                return LootTable.lootTable().withPool(createSeedsLootPool()).build();
            }
            return null;
        });
    }

    /**
     * 创建种子掉落战利品池
     */
    private static LootPool.Builder createSeedsLootPool() {
        return LootPool.lootPool()
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
    }
}