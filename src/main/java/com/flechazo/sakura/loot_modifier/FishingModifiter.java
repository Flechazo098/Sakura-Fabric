package com.flechazo.sakura.loot_modifier;

import com.flechazo.sakura.item.FoodRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

/**
 * 钓鱼战利品表修改器
 */
public class FishingModifiter {
    // 钓鱼战利品表ID
    private static final ResourceLocation FISHING_ID = new ResourceLocation("minecraft", "gameplay/fishing");
    // 钓鱼战利品表中的鱼类战利品表ID
    private static final ResourceLocation FISHING_FISH_ID = new ResourceLocation("minecraft", "gameplay/fishing/fish");
    // 钓鱼战利品表中的垃圾战利品表ID
    private static final ResourceLocation FISHING_JUNK_ID = new ResourceLocation("minecraft", "gameplay/fishing/junk");
    // 钓鱼战利品表中的宝藏战利品表ID
    private static final ResourceLocation FISHING_TREASURE_ID = new ResourceLocation("minecraft", "gameplay/fishing/treasure");

    /**
     * 注册钓鱼战利品表修改
     */
    public static void register () {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // 只修改钓鱼的战利品表
            if (FISHING_ID.equals(id)) {
                // 确保FoodRegistry已经初始化
                if (FoodRegistry.FOODSET.containsKey(SakuraFoodSet.SHRIMP)) {
                    // 创建一个新的战利品池，添加钓鱼物品
                    LootPool.Builder customFishPool = LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP))
                                    .when(LootItemRandomChanceCondition.randomChance(0.05f))); // 5%几率钓到虾

                    // 将新的战利品池添加到现有的战利品表中
                    tableBuilder.withPool(customFishPool);
                }
            }
        });
    }
}