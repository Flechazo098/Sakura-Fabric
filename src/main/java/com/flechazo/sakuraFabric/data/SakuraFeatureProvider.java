package com.flechazo.sakuraFabric.data;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.level.WorldGenerationRegistry;
import com.flechazo.sakuraFabric.level.tree.SakuraTreeFeatures;
import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SakuraFeatureProvider extends DatapackBuiltinEntriesProvider {

    public static final ResourceKey<BiomeModifier> ADD_FEATURES = ResourceKey.create(
            ForgeRegistries.Keys.BIOME_MODIFIERS, // The registry this key is for
            new ResourceLocation(SakuraFabric.MODID, "add_features") // The registry name
    );

    public SakuraFeatureProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries, new RegistrySetBuilder()
                        .add(Registries.CONFIGURED_FEATURE, bootstrap -> {
                            SakuraTreeFeatures.ENTRY.forEach(
                                    e -> bootstrap.register(e.getA(), e.getB())
                            );
                            bootstrap.register(WorldGenerationRegistry.FEATURE_PATCH_BAMBOOSHOOT_KEY, WorldGenerationRegistry.FEATURE_PATCH_BAMBOOSHOOT);
                        })
                        .add(Registries.PLACED_FEATURE, bootstrap -> {
                            bootstrap.register(WorldGenerationRegistry.PATCH_BAMBOOSHOOT_KEY, WorldGenerationRegistry.PATCH_BAMBOOSHOOT);
                        })
                , Set.of(SakuraFabric.MODID));
    }

}