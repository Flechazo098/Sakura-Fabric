package com.flechazo.sakura.loot_modifier;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.ItemRegistry;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Map;

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

    static final Map<Item, Double> seedWeights = ImmutableMap.<Item, Double>builder()
            .put(ItemRegistry.CABBAGE_SEEDS, 1.0)
            .put(ItemRegistry.EGGPLANT_SEEDS, 1.0)
            .put(ItemRegistry.ONION_SEEDS, 1.0)
            .put(ItemRegistry.RADISH_SEEDS, 1.0)
            .put(ItemRegistry.TOMATO_SEEDS, 1.0)
            .put(ItemRegistry.RICE_SEEDS, 1.0)
            .put(ItemRegistry.RAPESEEDS, 1.0)
            .put(ItemRegistry.TARO, 0.5)
            .put(ItemRegistry.BUCKWHEAT, 0.5)
            .put(ItemRegistry.SOYBEAN, 0.2)
            .put(ItemRegistry.RED_BEAN, 0.2)
            .build();

    static double R = SakuraFabric.INSTANCE.seedDropConfig.globalDropRate;

    static double W = seedWeights.values().stream().mapToDouble(Double::doubleValue).sum();

    private static LootPool.Builder createSeedsLootPool() {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1));

        seedWeights.forEach((seedRegObj, weight) -> {
            float pi = (float) (R * (weight / W));
            pool.add(
                    LootItem.lootTableItem(seedRegObj)
                            .when(LootItemRandomChanceCondition.randomChance(pi))
            );
        });

        return pool;
    }
}