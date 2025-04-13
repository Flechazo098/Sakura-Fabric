//package com.flechazo.sakura.core.data;
//
//import com.flechazo.sakura.SakuraFabric;
//import com.flechazo.sakura.init.ItemRegistry;
//import com.flechazo.sakura.item.enums.SakuraFoodSet;
//import com.flechazo.sakura.item.food.FoodRegistry;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.storage.loot.LootPool;
//import net.minecraft.world.level.storage.loot.LootTable;
//import net.minecraft.world.level.storage.loot.entries.LootItem;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
//import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
//import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
//import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
//
//import java.util.function.BiConsumer;
//
///**
// * 樱花模组战利品表数据生成器
// */
//public class SakuraLootTableProvider extends SimpleFabricLootTableProvider {
//
//    public SakuraLootTableProvider(FabricDataOutput output) {
//        // 使用GENERIC参数集，因为我们生成多种类型的战利品表
//        super(output, LootContextParamSets.GENERIC);
//    }
//
//    @Override
//    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
//        // 生成钓鱼战利品表修改
//        generateFishingLootTable(output);
//
//        // 生成草方块掉落种子的战利品表修改
//        generateGrassDropsLootTable(output);
//
//        // 添加日志输出，帮助调试
//        System.out.println("SakuraLootTableProvider 正在生成战利品表...");
//    }
//
//    /**
//     * 生成钓鱼战利品表
//     */
//    private void generateFishingLootTable(BiConsumer<ResourceLocation, LootTable.Builder> output) {
//        // 确保FoodRegistry已经初始化
//        try {
//            if (FoodRegistry.FOODSET != null && FoodRegistry.FOODSET.containsKey(SakuraFoodSet.SHRIMP)) {
//                LootTable.Builder fishingLootTable = LootTable.lootTable()
//                        .withPool(LootPool.lootPool()
//                                .setRolls(ConstantValue.exactly(1))
//                                .add(LootItem.lootTableItem(FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP))
//                                        .when(LootItemRandomChanceCondition.randomChance(0.05f))));
//
//                output.accept(new ResourceLocation(SakuraFabric.MODID, "fishing/fishing_modifier"), fishingLootTable);
//                System.out.println("生成钓鱼战利品表: sakura:fishing/fishing_modifier");
//            } else {
//                System.out.println("警告: FoodRegistry.FOODSET 为空或不包含 SHRIMP，跳过钓鱼战利品表生成");
//            }
//        } catch (Exception e) {
//            System.err.println("生成钓鱼战利品表时出错: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 生成草方块掉落种子的战利品表
//     */
//    private void generateGrassDropsLootTable(BiConsumer<ResourceLocation, LootTable.Builder> output) {
//        try {
//            LootTable.Builder grassDropsLootTable = LootTable.lootTable()
//                    .withPool(LootPool.lootPool()
//                            .setRolls(ConstantValue.exactly(1))
//                            .add(LootItem.lootTableItem(ItemRegistry.CABBAGE_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.EGGPLANT_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.ONION_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.RADISH_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.TOMATO_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.RICE_SEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.RAPESEEDS)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.08f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.TARO)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.05f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.BUCKWHEAT)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.05f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.SOYBEAN)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.05f)))
//                            .add(LootItem.lootTableItem(ItemRegistry.RED_BEAN)
//                                    .when(LootItemRandomChanceCondition.randomChance(0.05f))));
//
//            output.accept(new ResourceLocation(SakuraFabric.MODID, "blocks/grass_drops"), grassDropsLootTable);
//            System.out.println("生成草方块掉落战利品表: sakura:blocks/grass_drops");
//        } catch (Exception e) {
//            System.err.println("生成草方块掉落战利品表时出错: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}