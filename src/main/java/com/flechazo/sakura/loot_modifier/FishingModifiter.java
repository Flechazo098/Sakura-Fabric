package com.flechazo.sakura.loot_modifier;

import com.flechazo.sakura.init.FoodRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class FishingModifiter {
    private static final ResourceLocation FISHING_ID = new ResourceLocation("minecraft", "gameplay/fishing");

    public static void register() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (FISHING_ID.equals(id)) {
                if (FoodRegistry.FOODSET.containsKey(SakuraFoodSet.SHRIMP)) {
                    LootPool.Builder customFishPool = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP))
                                    .when(LootItemRandomChanceCondition.randomChance(0.05f)));

                    tableBuilder.withPool(customFishPool);
                }
            }
        });
    }
}