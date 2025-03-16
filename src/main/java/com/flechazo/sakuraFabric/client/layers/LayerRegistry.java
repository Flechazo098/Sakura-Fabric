package com.flechazo.sakuraFabric.client.layers;

import com.flechazo.sakuraFabric.SakuraFabric;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SakuraFabric.MODID, value = Dist.CLIENT)
public class LayerRegistry {
    public static final ModelLayerLocation STONE_MORTAR = register("stone_mortar");

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(STONE_MORTAR, StoneMortarRenderer::createLayer);
    }

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String part) {
        return new ModelLayerLocation(new ResourceLocation(SakuraFabric.MODID, path), part);
    }
}
