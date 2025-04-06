package com.flechazo.sakura.item;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.BlockRegistry;
import com.flechazo.sakura.block.entity.BlockEntityRegistry;
import com.flechazo.sakura.block.entity.StoneMortarBlockEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;

public class StoneMortarItem extends BlockItem {
    private static StoneMortarBlockEntity blockEntity;

    public StoneMortarItem() {
        super(BlockRegistry.STONE_MORTAR, SakuraFabric.defaultItemProperties());

        // 在客户端初始化时注册自定义渲染器
        if (SakuraFabric.isClientSide()) {
            registerRenderer();
        }
    }

    // 在客户端侧调用此方法注册渲染器
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