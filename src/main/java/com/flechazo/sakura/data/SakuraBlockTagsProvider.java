package com.flechazo.sakura.data;

import com.flechazo.sakura.block.BlockRegistry;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class SakuraBlockTagsProvider extends BlockTagProvider {

    public SakuraBlockTagsProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput,lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.LOGS).add(BlockRegistry.STRIPPED_SAKURA_WOOD, BlockRegistry.STRIPPED_MAPLE_WOOD,
                BlockRegistry.SAKURA_WOOD, BlockRegistry.MAPLE_WOOD,
                BlockRegistry.STRIPPED_SAKURA_LOG, BlockRegistry.STRIPPED_MAPLE_LOG,
                BlockRegistry.SAKURA_LOG, BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_SAP_LOG);
        this.tag(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.STRIPPED_SAKURA_WOOD,
                BlockRegistry.STRIPPED_MAPLE_WOOD, BlockRegistry.SAKURA_WOOD,
                BlockRegistry.MAPLE_WOOD, BlockRegistry.STRIPPED_SAKURA_LOG,
                BlockRegistry.STRIPPED_MAPLE_LOG, BlockRegistry.SAKURA_LOG, BlockRegistry.MAPLE_LOG,
                BlockRegistry.MAPLE_SAP_LOG);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(BlockRegistry.STONE_MORTAR);

        this.tag(BlockTags.LEAVES).add(BlockRegistry.SAKURA_LEAVES, BlockRegistry.MAPLE_LEAVES_RED,
                BlockRegistry.MAPLE_LEAVES_GREEN, BlockRegistry.MAPLE_LEAVES_ORANGE,
                BlockRegistry.MAPLE_LEAVES_YELLOW);

        this.tag(BlockTags.SAPLINGS).add(BlockRegistry.SAKURA_SAPLING, BlockRegistry.MAPLE_SAPLING_RED,
                BlockRegistry.MAPLE_SAPLING_GREEN, BlockRegistry.MAPLE_SAPLING_ORANGE,
                BlockRegistry.MAPLE_SAPLING_YELLOW);

        this.tag(BlockTags.CROPS).add(BlockRegistry.RICE_CROP, BlockRegistry.BUCKWHEAT_CROP,
                BlockRegistry.CABBAGE_CROP, BlockRegistry.EGGPLANT_CROP, BlockRegistry.ONION_CROP,
                BlockRegistry.RADISH_CROP, BlockRegistry.RAPESEED_CROP, BlockRegistry.REDBEAN_CROP,
                BlockRegistry.RICE_CROP_ROOT, BlockRegistry.TARO_CROP, BlockRegistry.TOMATO_CROP);

        this.tag(BlockTags.PLANKS).add(BlockRegistry.SAKURA_PLANK, BlockRegistry.BAMBOO_PLANK,
                BlockRegistry.MAPLE_PLANK);
    }

    @Override
    public String getName() {
        return "Sakura Blocks' Tags";
    }


}