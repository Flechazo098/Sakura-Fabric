package com.flechazo.sakura.item;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RiceSeedsItem extends ItemNameBlockItem {

    public RiceSeedsItem() {
        super(BlockRegistry.RICE_CROP_ROOT, SakuraFabric.defaultItemProperties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.place(new BlockPlaceContext(context));
        if (result.equals(InteractionResult.FAIL)) {
            Player player = context.getPlayer();
            BlockState targetState = context.getLevel().getBlockState(context.getClickedPos());
            if (player != null && context.getClickedFace().equals(Direction.UP)
                    && (targetState.is(BlockTags.DIRT) || targetState.getBlock() instanceof FarmBlock)) {
                player.displayClientMessage(
                        Component.translatable(SakuraFabric.MODID + "." + "block.rice.invalid_placement"), true);
            }
        }
        return !result.consumesAction() && this.isEdible()
                ? this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult()
                : result;
    }
}
