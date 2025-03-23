package com.flechazo.sakuraFabric.data.client;

import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.data.AbstractBlockStateProvider;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SakuraBlockStateProvider extends AbstractBlockStateProvider {

    public SakuraBlockStateProvider(PackOutput packOutput, String modid, ExistingFileHelper exFileHelper) {
        super(packOutput, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(BlockRegistry.SAKURA_LEAVES);
        simpleBlock(BlockRegistry.SAKURA_PLANK);
        simpleBlock(BlockRegistry.MAPLE_PLANK);
        simpleBlock(BlockRegistry.BAMBOO_PLANK);

        simpleBlock(BlockRegistry.STRAW_BLOCK);

        simpleBlock(BlockRegistry.MAPLE_LEAVES_RED);
        simpleBlock(BlockRegistry.MAPLE_LEAVES_YELLOW);
        simpleBlock(BlockRegistry.MAPLE_LEAVES_GREEN);
        simpleBlock(BlockRegistry.MAPLE_LEAVES_ORANGE);

        log(() ->BlockRegistry.SAKURA_LOG);
        log(() ->BlockRegistry.STRIPPED_SAKURA_LOG);
        log(() ->BlockRegistry.MAPLE_LOG);
        log(() ->BlockRegistry.STRIPPED_MAPLE_LOG);
        log(() ->BlockRegistry.BAMBOO_BLOCK);
        log(() ->BlockRegistry.BAMBOO_BLOCK_SUNBURNT);
        log(() ->BlockRegistry.BAMBOO_CHARCOAL_BLOCK);

        horizontalBlock(BlockRegistry.FERMENTER, models().getExistingFile(new ResourceLocation("sakura:block/fermenter")));
        crossBlock(() ->BlockRegistry.SAKURA_SAPLING);
        crossBlock(() ->BlockRegistry.MAPLE_SAPLING_RED);
        crossBlock(() ->BlockRegistry.MAPLE_SAPLING_YELLOW);
        crossBlock(() ->BlockRegistry.MAPLE_SAPLING_GREEN);
        crossBlock(() ->BlockRegistry.MAPLE_SAPLING_ORANGE);

        stageBlock(() ->BlockRegistry.BUCKWHEAT_CROP, BlockStateProperties.AGE_7);
        stageBlock(() ->BlockRegistry.RAPESEED_CROP, BlockStateProperties.AGE_7);
        stageBlock(() ->BlockRegistry.REDBEAN_CROP, BlockStateProperties.AGE_3);
        stageBlock(() ->BlockRegistry.TARO_CROP, BlockStateProperties.AGE_3);

        horizontalBlock(BlockRegistry.TATAMI,
                texture("tatami"),
                texture("tatami"),
                texture("tatami"));
        horizontalBlock(BlockRegistry.TATAMI_SUNBURNT,
                texture("tatami_tan"),
                texture("tatami_tan"),
                texture("tatami_tan"));

        facingSlabBlock(() ->BlockRegistry.TATAMI_SLAB,
                texture("tatami"),
                texture("tatami"),
                texture("tatami")
        );
        facingSlabBlock(() ->BlockRegistry.TATAMI_SLAB_SUNBURNT,
                texture("tatami_tan"),
                texture("tatami_tan"),
                texture("tatami_tan")
        );
    }

}
