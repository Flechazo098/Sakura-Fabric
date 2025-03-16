package com.flechazo.sakuraFabric.level;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

public class WorldGenerationRegistry {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_BAMBOOSHOOT_KEY = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "patch_bambooshoot"));
    public static final ConfiguredFeature<?, ?> FEATURE_PATCH_BAMBOOSHOOT = new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(
            64, 1, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new NoiseThresholdProvider(496156461L,
            new NormalNoise.NoiseParameters(0, 1.0), 0.005F, -0.8F, 0.33333334F, BlockRegistry.BAMBOOSHOOT.get().defaultBlockState(),
            List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState()),
            List.of(BlockRegistry.BAMBOOSHOOT.get().defaultBlockState()))))));
    public static final ResourceKey<PlacedFeature> PATCH_BAMBOOSHOOT_KEY = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(SakuraFabric.MODID, "patch_bambooshoot"));
    public static final PlacedFeature PATCH_BAMBOOSHOOT = new PlacedFeature(Holder.direct(FEATURE_PATCH_BAMBOOSHOOT),
            List.of(PlacementUtils.HEIGHTMAP,
                    InSquarePlacement.spread(),
                    BiomeFilter.biome(),
                    PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING),
                    RarityFilter.onAverageOnceEvery(30)));

//    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);
//
//    private static ConfiguredFeature<?, ?> wildPlantFeature(Supplier<Block> wildCrop, TagKey<Block> blockTag) {
//        return new ConfiguredFeature<>(Feature.RANDOM_PATCH, getWildCropConfiguration(wildCrop.get(),
//                64, 1, BlockPredicate.matchesTag(BLOCK_BELOW,blockTag)));
//    }
//    private static PlacedFeature wildPlantPatch(ConfiguredFeature<?, ?> feature,
//            PlacementModifier... modifiers) {
//        return new PlacedFeature(Holder.direct(feature), Lists.newArrayList(modifiers));
//    }
//
//    private static RandomPatchConfiguration getWildCropConfiguration(Block block, int tries, int xzSpread, BlockPredicate plantedOn) {
//        return new RandomPatchConfiguration(tries, xzSpread, 3, PlacementUtils.filtered(
//                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
//                        BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
//    }
}
