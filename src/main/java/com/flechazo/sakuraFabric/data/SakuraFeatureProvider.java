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

    // 在 Fabric 中，生物群系修改通常通过 JSON 数据包或使用 Fabric API 的 BiomeModifications 完成
    // 可以在主类中添加以下代码来注册生物群系修改
    /*
    public static void registerBiomeModifications() {
        BiomeModifications.addFeature(
            BiomeSelectors.foundInOverworld(),
            GenerationStep.Feature.VEGETAL_DECORATION,
            ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,
                new ResourceLocation(SakuraFabric.MODID, "patch_bambooshoot"))
        );

        // 添加其他特性...
    }
    */
}