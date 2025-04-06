package com.flechazo.sakura.client.layers;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.client.render.StoneMortarRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class LayerRegistry {
    public static final ModelLayerLocation STONE_MORTAR = register("stone_mortar");

    // 注册所有模型层
    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(STONE_MORTAR, StoneMortarRenderer::createLayer);
    }

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String part) {
        return new ModelLayerLocation(new ResourceLocation(SakuraFabric.MODID, path), part);
    }
}