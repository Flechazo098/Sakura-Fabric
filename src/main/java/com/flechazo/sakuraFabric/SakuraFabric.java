package com.flechazo.sakuraFabric;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;

import java.rmi.registry.Registry;

public class SakuraFabric implements ModInitializer {
    public static final String MODID = "sakura";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static FabricItemSettings defaultItemProperties() {
        return new FabricItemSettings();
    }

    public static void registerBiomeModifications() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE,
                        new ResourceLocation(SakuraFabric.MODID, "patch_bambooshoot"))
        );
    }
    @Override
    public void onInitialize () {
        registerBiomeModifications();
    }
}
