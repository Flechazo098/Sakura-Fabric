package com.flechazo.sakura.events;

import com.flechazo.sakura.block.BlockRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;

public class TreeEvent {

    // 初始化事件注册
    public static void register() {
        // 注册方块使用事件（斧头剥皮）
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (player.getItemInHand(hand).getItem() instanceof AxeItem) {
                BlockState state = world.getBlockState(hitResult.getBlockPos());

                // 尝试剥皮
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

                // 如果找到了对应的剥皮方块，应用变化
                if (newState != null) {
                    world.playSound(player, hitResult.getBlockPos(), SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    world.setBlock(hitResult.getBlockPos(), newState, 11);

                    // 如果不是创造模式，损耗工具耐久
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