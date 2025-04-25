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

public class SeedsDrop {
    private static final ResourceLocation GRASS_ID = new ResourceLocation("minecraft", "blocks/grass");
    private static final ResourceLocation FERN_ID = new ResourceLocation("minecraft", "blocks/fern");
    private static final ResourceLocation TALL_GRASS_ID = new ResourceLocation("minecraft", "blocks/tall_grass");
    private static final ResourceLocation LARGE_FERN_ID = new ResourceLocation("minecraft", "blocks/large_fern");

    private static final ResourceLocation SEEDS_DROPS = new ResourceLocation(SakuraFabric.MODID, "blocks/seeds_drops");

    public static void register() {
        createSeedsDropsLootTable();

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (GRASS_ID.equals(id) || FERN_ID.equals(id) || TALL_GRASS_ID.equals(id) || LARGE_FERN_ID.equals(id)) {
                LootPool.Builder seedsPool = createSeedsLootPool();
                tableBuilder.withPool(seedsPool);
            }
        });
    }

    private static void createSeedsDropsLootTable() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, original, source) -> {
            if (SEEDS_DROPS.equals(id)) {
                return LootTable.lootTable().withPool(createSeedsLootPool()).build();
            }
            return null;
        });
    }

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