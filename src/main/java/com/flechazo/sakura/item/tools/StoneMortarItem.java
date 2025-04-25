package com.flechazo.sakura.item.tools;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.block.entity.StoneMortarBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;

public class StoneMortarItem extends BlockItem {
    private static StoneMortarBlockEntity blockEntity;

    public StoneMortarItem() {
        super(BlockRegistry.STONE_MORTAR, SakuraFabric.defaultItemProperties());

        if (SakuraFabric.isClientSide()) {
            registerRenderer();
        }
    }

    private void registerRenderer() {
        BuiltinItemRendererRegistry.INSTANCE.register(this, (stack, mode, matrices, vertexConsumers, light, overlay) -> {
            if (blockEntity == null) {
                blockEntity = BlockEntityRegistry.STONE_MORTAR.create(BlockPos.ZERO,
                        BlockRegistry.STONE_MORTAR.defaultBlockState());
            }
            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, matrices, vertexConsumers, light, overlay);
        });
    }
}