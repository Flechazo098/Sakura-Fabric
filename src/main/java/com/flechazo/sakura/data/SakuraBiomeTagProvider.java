package com.flechazo.sakura.data;

import com.flechazo.sakura.tags.SakuraBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class SakuraBiomeTagProvider extends BiomeTagsProvider {
    public SakuraBiomeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagAppender<Biome> tagAppender = this.tag(SakuraBiomeTags.CAN_SPAWN_BAMBOO);

        tagAppender.add(Biomes.MEADOW);
        tagAppender.add(Biomes.CHERRY_GROVE);
        tagAppender.add(Biomes.BIRCH_FOREST);
        tagAppender.add(Biomes.OLD_GROWTH_BIRCH_FOREST);
        tagAppender.add(Biomes.FOREST);
        tagAppender.add(Biomes.FLOWER_FOREST);
        tagAppender.add(Biomes.DARK_FOREST);
        tagAppender.add(Biomes.PLAINS);
        tagAppender.add(Biomes.SUNFLOWER_PLAINS);
        tagAppender.add(Biomes.MANGROVE_SWAMP);
        tagAppender.add(Biomes.SWAMP);
        tagAppender.add(Biomes.JUNGLE);
        tagAppender.add(Biomes.BAMBOO_JUNGLE);
        tagAppender.add(Biomes.SPARSE_JUNGLE);

    }
}
