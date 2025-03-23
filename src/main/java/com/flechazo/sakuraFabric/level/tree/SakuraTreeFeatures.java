package com.flechazo.sakuraFabric.level.tree;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class SakuraTreeFeatures {

    public static List<Pair<ResourceKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>>> ENTRY = new ArrayList<>();

    public static final ResourceKey<ConfiguredFeature<?, ?>> SAKURA_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "sakura"));
    public static final ConfiguredFeature<?, ?> SAKURA = registryTree(SAKURA_KEY, createSimpleBlobTree(BlockRegistry.SAKURA_LOG, BlockRegistry.SAKURA_LEAVES).ignoreVines());

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SAKURA_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "fancy_sakura"));
    public static final ConfiguredFeature<?, ?> FANCY_SAKURA = registryTree(FANCY_SAKURA_KEY, createFancyTree(BlockRegistry.SAKURA_LOG, BlockRegistry.SAKURA_LEAVES));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_RED_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "maple_red"));
    public static final ConfiguredFeature<?, ?> MAPLE_RED = registryTree(MAPLE_RED_KEY, createStraightBlobTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_RED, 5, 2, 0, 2).ignoreVines());

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_RED_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "fancy_maple_red"));
    public static final ConfiguredFeature<?, ?> FANCY_MAPLE_RED = registryTree(FANCY_MAPLE_RED_KEY, createFancyTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_RED));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_YELLOW_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "maple_yellow"));
    public static final ConfiguredFeature<?, ?> MAPLE_YELLOW = registryTree(MAPLE_YELLOW_KEY, createStraightBlobTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_YELLOW, 5, 2, 0, 2).ignoreVines());

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_YELLOW_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "fancy_maple_yellow"));
    public static final ConfiguredFeature<?, ?> FANCY_MAPLE_YELLOW = registryTree(FANCY_MAPLE_YELLOW_KEY, createFancyTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_YELLOW));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_ORANGE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "maple_orange"));
    public static final ConfiguredFeature<?, ?> MAPLE_ORANGE = registryTree(MAPLE_ORANGE_KEY, createStraightBlobTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_ORANGE, 5, 2, 0, 2).ignoreVines());

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_ORANGE_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "fancy_maple_orange"));
    public static final ConfiguredFeature<?, ?> FANCY_MAPLE_ORANGE = registryTree(FANCY_MAPLE_ORANGE_KEY, createFancyTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_ORANGE));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_GREEN_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "maple_green"));
    public static final ConfiguredFeature<?, ?> MAPLE_GREEN = registryTree(MAPLE_GREEN_KEY, createStraightBlobTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_GREEN, 5, 2, 0, 2).ignoreVines());

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE_GREEN_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "fancy_maple_green"));
    public static final ConfiguredFeature<?, ?> FANCY_MAPLE_GREEN = registryTree(FANCY_MAPLE_GREEN_KEY, createFancyTree(BlockRegistry.MAPLE_LOG, BlockRegistry.MAPLE_LEAVES_GREEN));

    private static ConfiguredFeature<?, ?> registryTree(ResourceKey<ConfiguredFeature<?, ?>> key, TreeConfiguration.TreeConfigurationBuilder tree) {
        ConfiguredFeature<TreeConfiguration, Feature<TreeConfiguration>> feature = new ConfiguredFeature<>(Feature.TREE, tree.build());
        ENTRY.add(new Pair<>(key, feature));
        return feature;
    }

    private static TreeConfiguration.TreeConfigurationBuilder createSimpleBlobTree(Block log, Block leaves) {
        return createStraightBlobTree(log, leaves, 4, 2, 0, 2);
    }

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block log, Block leaves,
                                                                                     int baseHeight, int heightRandA, int heightRandB, int leaves_radius) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new StraightTrunkPlacer(baseHeight, heightRandA, heightRandB), BlockStateProvider.simple(leaves),
                new BlobFoliagePlacer(ConstantInt.of(leaves_radius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createFancyTree(Block log, Block leaves) {
        return (new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(log),
                new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(leaves),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines();
    }
}
