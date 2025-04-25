package com.flechazo.sakura.client.handler;


import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.client.render.fluid.ClientFluidExtensions;
import com.flechazo.sakura.client.render.fluid.FluidRenderingRegistry;
import com.flechazo.sakura.client.gui.ScreensRegistry;
import com.flechazo.sakura.client.render.layers.LayerRegistry;
import com.flechazo.sakura.client.particle.FallenLeafParticle;
import com.flechazo.sakura.client.particle.ParticleRegistry;
import com.flechazo.sakura.client.render.blockentity.ChoppingBoardRender;
import com.flechazo.sakura.client.render.blockentity.ObonRender;
import com.flechazo.sakura.client.render.blockentity.StoneMortarRenderer;
import com.flechazo.sakura.init.FluidRegistry;

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

        FluidRenderingRegistry.registerFluidAttributes();
        FluidRenderingRegistry.registerFluidRenderHandlers();

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

        FluidRegistry.FLUIDS.forEach(fluid -> {
            BlockRenderLayerMap.INSTANCE.putBlock(fluid, RenderType.translucent());
        });
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