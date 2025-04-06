package com.flechazo.sakura.data.loot;

import com.flechazo.sakura.block.BambooPlant;
import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.block.BlockRegistry;
import com.flechazo.sakura.block.crops.RiceCropRoot;
import com.flechazo.sakura.block.foods.TeishokuBlock;
import com.flechazo.sakura.block.foods.TeishokuFinishedBlock;
import com.flechazo.sakura.item.FoodRegistry;
import com.flechazo.sakura.item.ItemRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import com.flechazo.sakura.item.enums.SakuraNormalItemSet;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public class SakuraBlockLoot extends AbstartctBlockLoot {

    public SakuraBlockLoot(Set<Item> pExplosionResistant) {
        super(pExplosionResistant);
        // TODO Auto-generated constructor stub
    }

    public SakuraBlockLoot() {
        super(Set.of());
    }

    @Override
    public void addTables() {
        dropSelf(BlockRegistry.BAMBOO_BLOCK);

        // 使用反射或直接访问方式获取所有方块
        for (Block block : BlockRegistry.getAllBlocks()) {
            if (block instanceof LeavesBlock) {
                // 叶子方块在下面单独处理
            } else if (block instanceof CropBlock) {
                // 作物方块在下面单独处理
            } else if (block instanceof TeishokuBlock) {
                // 餐盘方块在下面单独处理
            } else if (block instanceof RiceCropRoot) {
                // 水稻根方块在下面单独处理
            } else if (block instanceof BambooPlant) {
                dropOther(block, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO));
            } else if (block instanceof TeishokuFinishedBlock) {
                dropOther(block, BlockItemRegistry.OBON);
            } else {
                dropSelf(block);
            }
        }

        this.add(BlockRegistry.MAPLE_LEAVES_RED, createLeavesDrops(BlockRegistry.MAPLE_LEAVES_RED,
                BlockRegistry.MAPLE_SAPLING_RED, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_ORANGE, createLeavesDrops(BlockRegistry.MAPLE_LEAVES_ORANGE,
                BlockRegistry.MAPLE_SAPLING_ORANGE, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_YELLOW, createLeavesDrops(BlockRegistry.MAPLE_LEAVES_YELLOW,
                BlockRegistry.MAPLE_SAPLING_YELLOW, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.MAPLE_LEAVES_GREEN, createLeavesDrops(BlockRegistry.MAPLE_LEAVES_GREEN,
                BlockRegistry.MAPLE_SAPLING_GREEN, NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(BlockRegistry.SAKURA_LEAVES, createLeavesDrops(BlockRegistry.SAKURA_LEAVES,
                BlockRegistry.SAKURA_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES));

        this.createTeishoku(BlockRegistry.TEISHOUKU_FISH_COOKED);
        this.createTeishoku(BlockRegistry.TEISHOUKU_FISH_RAW);
        this.createTeishoku(BlockRegistry.TEISHOUKU_FISH_SALT);
        this.createTeishoku(BlockRegistry.TEISHOKO_TAMAGOYAKI);

        createCrop(BlockRegistry.CABBAGE_CROP, FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE),
                ItemRegistry.CABBAGE_SEEDS, 7);

        createCrop(BlockRegistry.RADISH_CROP, FoodRegistry.FOODSET.get(SakuraFoodSet.RADISH),
                ItemRegistry.RADISH_SEEDS, 3);

        createCrop(BlockRegistry.ONION_CROP, FoodRegistry.FOODSET.get(SakuraFoodSet.ONION),
                ItemRegistry.ONION_SEEDS, 3);

        createCrop(BlockRegistry.REDBEAN_CROP, ItemRegistry.RED_BEAN, ItemRegistry.RED_BEAN, 3);
        createCrop(BlockRegistry.SOYBEAN_CROP, ItemRegistry.SOYBEAN, ItemRegistry.SOYBEAN, 3);

        createCrop(BlockRegistry.EGGPLANT_CROP, FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT),
                ItemRegistry.EGGPLANT_SEEDS, 7);

        createCrop(BlockRegistry.TOMATO_CROP, FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO),
                ItemRegistry.TOMATO_SEEDS, 7);

        createCrop(BlockRegistry.RICE_CROP, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW),
                ItemRegistry.RICE_SEEDS, 7);

        createCrop(BlockRegistry.RICE_CROP_ROOT, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW),
                ItemRegistry.RICE_SEEDS, 7);

        createCrop(BlockRegistry.RAPESEED_CROP, ItemRegistry.RAPESEEDS, ItemRegistry.RAPESEEDS, 7);

        createCrop(BlockRegistry.TARO_CROP, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARA),
                ItemRegistry.TARO, 3);

        createCrop(BlockRegistry.BUCKWHEAT_CROP, ItemRegistry.BUCKWHEAT, ItemRegistry.BUCKWHEAT, 7);
    }

    private void createTeishoku(Block block) {
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TeishokuBlock.BITES, 0));
        this.add(block, createTeishokuDrops(block, BlockItemRegistry.OBON, block.asItem(), builder));
    }

    protected LootTable.Builder createTeishokuDrops(Block p_124143_, Item p_124144_, Item p_124145_,
                                                    LootItemCondition.Builder p_124146_) {
        return applyExplosionDecay(p_124143_, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(
                        LootItem.lootTableItem(p_124145_).when(p_124146_)))
                .withPool(
                        LootPool.lootPool().when(p_124146_.invert()).add(LootItem.lootTableItem(p_124144_))));
    }

    private void createCrop(Block block, Item crop, Item seeds, int age) {
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, age));
        this.add(block, createCropDrops(block, crop, seeds, builder));
    }
}
