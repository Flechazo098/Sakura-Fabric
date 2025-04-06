package com.flechazo.sakura.block;

import io.github.fabricators_of_create.porting_lib.tool.ToolAction;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import io.github.fabricators_of_create.porting_lib.tool.extensions.BlockExtensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class MapleTreeLogBlock extends RotatedPillarBlock implements BlockExtensions {

    public MapleTreeLogBlock() {
        super(Properties.copy(Blocks.OAK_LOG).mapColor(
                        state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.WOOD
                                : MapColor.PODZOL))
                .strength(2.0F).sound(SoundType.WOOD));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult hitresult) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.canPerformAction(ToolActions.SHEARS_CARVE)) {
            if (!level.isClientSide) {
                level.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, BlockRegistry.MAPLE_SAP_LOG.withPropertiesOf(state)
                        .setValue(MapleTreeSapLogBlock.EXHAUSTION, false), 11);
                itemstack.hurtAndBreak(1, player, tool -> {
                    tool.broadcastBreakEvent(hand);
                });
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, hitresult);
        }
    }

    // 实现 BlockExtensions 接口的方法
    @Override
    @Nullable
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.SHEARS_CARVE) {
            if (simulate) {
                // 只是模拟，不实际修改世界
                return BlockRegistry.MAPLE_SAP_LOG.withPropertiesOf(state)
                        .setValue(MapleTreeSapLogBlock.EXHAUSTION, false);
            }

            // 实际修改世界
            Level level = context.getLevel();
            BlockPos pos = context.getClickedPos();
            Player player = context.getPlayer();

            if (player != null && !level.isClientSide) {
                level.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                ItemStack itemstack = context.getItemInHand();
                itemstack.hurtAndBreak(1, player, tool -> {
                    tool.broadcastBreakEvent(context.getHand());
                });

                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }

            return BlockRegistry.MAPLE_SAP_LOG.withPropertiesOf(state)
                    .setValue(MapleTreeSapLogBlock.EXHAUSTION, false);
        }

        return null;
    }
}