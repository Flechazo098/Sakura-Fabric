package com.flechazo.sakuraFabric.data;

import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.data.loot.SakuraBlockLoot;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SakuraLootTableProvider extends LootTableProvider {
    // 需要生成的战利品表类型列表
    private static final List<SubProviderEntry> LOOT_PROVIDERS = List.of(
            new SubProviderEntry(SakuraBlockLoot::new, LootContextParamSets.BLOCK)
    );

    public SakuraLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), LOOT_PROVIDERS);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return super.run(cachedOutput);
    }
//
//    // 自定义方块战利品表生成类
//    public static class SakuraBlockLoot extends BlockLootSubProvider {
//        private final List<Block> knownBlocks = List.of(
//                // 在此添加需要生成战利品表的方块
//                BlockRegistry.SAKURA_PLANKS,
//                BlockRegistry.MAPLE_LOG
//        );
//
//        protected SakuraBlockLoot() {
//            super(Set.of(), LootContextParamSets.BLOCK);
//        }
//
//        @Override
//        public void generate () {
//            // 为每个方块生成战利品表
//            knownBlocks.forEach(this::dropSelf);
//
//            // 示例：特殊方块的自定义掉落
//            add(BlockRegistry.SAKURA_LEAVES,
//                    LootTable.lootTable()
//                            .withPool(LootPool.lootPool()
//                                    .add(applyExplosionCondition(BlockRegistry.SAKURA_LEAVES,
//                                            LootItem.lootTableItem(Items.APPLE))
//                                            .when(BonusLevelTableCondition.bonusLevelFlatChance(
//                                                    Enchantments.BLOCK_FORTUNE,
//                                                    0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F
//                                            ))
//                                    )
//                            )
//            );
//        }
//
//        @Override
//        protected Iterable<Block> getKnownBlocks() {
//            return knownBlocks;
//        }
//    }
}