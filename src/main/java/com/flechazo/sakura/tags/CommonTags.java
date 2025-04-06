package com.flechazo.sakura.tags;

import com.flechazo.sakura.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CommonTags {
    // Blocks that can heat up cooking workstations.
    public static final TagKey<Block> HEAT_SOURCES = TagUtils.modBlockTag("mysterious_mountain_lib", "heat_sources");

    // Blocks that can transfer heat to cooking workstations, if directly above a heat source.
    public static final TagKey<Block> HEAT_CONDUCTORS = TagUtils.modBlockTag("mysterious_mountain_lib", "heat_conductors");
}