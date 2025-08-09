package com.flechazo.sakura.client.handler;

import com.flechazo.sakura.client.gui.ScreensRegistry;
import com.flechazo.sakura.client.particle.FallenLeafParticle;
import com.flechazo.sakura.client.particle.ParticleRegistry;
import com.flechazo.sakura.client.render.blockentity.ChoppingBoardRender;
import com.flechazo.sakura.client.render.blockentity.ObonRender;
import com.flechazo.sakura.client.render.blockentity.StoneMortarRenderer;
import com.flechazo.sakura.client.render.fluid.ClientFluidExtensions;
import com.flechazo.sakura.client.render.layers.LayerRegistry;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.init.fluid.FluidBlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.BushBlock;

public class SakuraClientEventHandler implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientFluidExtensions.initialize();

        ScreensRegistry.register();

        LayerRegistry.register();

        registerParticles();

        setRenderLayers();

        registerBlockEntityRenderers();
    }

    private static void setRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.SAKURA_SAPLING, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.RICE_CROP_ROOT, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAMBOO_PLANT, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.BAMBOOSHOOT, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.COOKING_POT, RenderType.cutoutMipped());

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.NABE_ODEN, RenderType.cutoutMipped());
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.NABE_SUKIYAKI, RenderType.cutoutMipped());

        BlockRegistry.BLOCKS.forEach(block -> {
            if (block instanceof BushBlock) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped());
            }
        });

        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.FOOD_OIL_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.DOBUROKU_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.SAKE_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.SHOUCHU_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.BEER_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.WHISKEY_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.RUM_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.RED_WINE_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.WHITE_WINE_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.CHAMPAGNE_BLOCK, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FluidBlockRegistry.BRANDY_BLOCK, RenderType.translucent());

        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.FOOD_OIL, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.DOBUROKU, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.SAKE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.SHOUCHU, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.BEER, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.WHISKEY, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.RUM, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.RED_WINE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.WHITE_WINE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.CHAMPAGNE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.BRANDY, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.FOOD_OIL_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.DOBUROKU_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.SAKE_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.SHOUCHU_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.BEER_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.WHISKEY_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.RUM_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.RED_WINE_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.WHITE_WINE_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.CHAMPAGNE_FLOWING, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(FluidRegistry.BRANDY_FLOWING, RenderType.translucent());
    }

    private static void registerBlockEntityRenderers() {
        BlockEntityRenderers.register(BlockEntityRegistry.STONE_MORTAR, StoneMortarRenderer::new);
        BlockEntityRenderers.register(BlockEntityRegistry.CHOPPING_BOARD, ChoppingBoardRender::new);
        BlockEntityRenderers.register(BlockEntityRegistry.OBON, ObonRender::new);
    }

    private static void registerParticles() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.SAKURA_LEAF, FallenLeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.RED_MAPLE_LEAF, FallenLeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.YELLOW_MAPLE_LEAF, FallenLeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.GREEN_MAPLE_LEAF, FallenLeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.ORANGE_MAPLE_LEAF, FallenLeafParticle.Factory::new);
    }
}