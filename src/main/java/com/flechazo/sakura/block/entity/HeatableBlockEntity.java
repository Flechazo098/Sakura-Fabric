package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.tags.CommonTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import vectorwing.farmersdelight.common.tag.ModTags;

/**
 * Copy and improve from Farmer's Delight, add require tags for other magic jobs.
 */
public interface HeatableBlockEntity {

    default boolean isHeated(Level level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(ModTags.HEAT_SOURCES)) {
            return stateBelow.hasProperty(BlockStateProperties.LIT) ? (Boolean)stateBelow.getValue(BlockStateProperties.LIT) : true;
        } else {
            if (!this.requiresDirectHeat() && stateBelow.is(ModTags.HEAT_CONDUCTORS)) {
                BlockState stateFurtherBelow = level.getBlockState(pos.below(2));
                if (stateFurtherBelow.is(ModTags.HEAT_SOURCES)) {
                    if (stateFurtherBelow.hasProperty(BlockStateProperties.LIT)) {
                        return (Boolean)stateFurtherBelow.getValue(BlockStateProperties.LIT);
                    }

                    return true;
                }
            }

            return false;
        }
    }

    default TagKey<Block> heatSourceTag(){
        return CommonTags.HEAT_SOURCES;
    }

    default TagKey<Block> heatConductorTag(){
        return CommonTags.HEAT_CONDUCTORS;
    }

    /**
     * Determines if this block can only be heated directly, excluding conductors.
     */
    default boolean requiresDirectHeat() {
        return false;
    }
}

