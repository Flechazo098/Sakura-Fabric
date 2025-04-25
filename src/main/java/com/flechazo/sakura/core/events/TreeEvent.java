package com.flechazo.sakura.core.events;

import com.flechazo.sakura.init.BlockRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;

public class TreeEvent {

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.getItemInHand(hand).getItem() instanceof AxeItem) {
                BlockState state = world.getBlockState(hitResult.getBlockPos());

                BlockState newState = null;

                if (state.is(BlockRegistry.SAKURA_LOG)) {
                    newState = BlockRegistry.STRIPPED_SAKURA_LOG.withPropertiesOf(state);
                } else if (state.is(BlockRegistry.MAPLE_LOG)) {
                    newState = BlockRegistry.STRIPPED_MAPLE_LOG.withPropertiesOf(state);
                } else if (state.is(BlockRegistry.MAPLE_SAP_LOG)) {
                    newState = BlockRegistry.STRIPPED_MAPLE_LOG.withPropertiesOf(state);
                } else if (state.is(BlockRegistry.SAKURA_WOOD)) {
                    newState = BlockRegistry.STRIPPED_SAKURA_WOOD.withPropertiesOf(state);
                } else if (state.is(BlockRegistry.MAPLE_WOOD)) {
                    newState = BlockRegistry.STRIPPED_MAPLE_WOOD.withPropertiesOf(state);
                }

                if (newState != null) {
                    world.playSound(player, hitResult.getBlockPos(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.setBlock(hitResult.getBlockPos(), newState, 11);

                    if (!player.getAbilities().instabuild) {
                        player.getItemInHand(hand).hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                    }

                    return InteractionResult.SUCCESS;
                }
            }

            return InteractionResult.PASS;
        });
    }
}