package com.flechazo.sakuraFabric.tags;

import com.flechazo.sakuraFabric.SakuraFabric;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class SakuraBiomeTags {
    public static final TagKey<Biome> CAN_SPAWN_BAMBOO = TagKey.create(Registries.BIOME, new ResourceLocation(SakuraFabric.MODID,"can_spawn_bamboo"));
}
