package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.block.entity.BlockEntityRegistry;
import com.flechazo.sakuraFabric.block.entity.StoneMortarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class StoneMortarItem extends BlockItem {

    public StoneMortarItem() {
        super(BlockRegistry.STONE_MORTAR.get(), SakuraFabric.defaultItemProperties());
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            BlockEntityWithoutLevelRenderer myRenderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (Minecraft.getInstance().getEntityRenderDispatcher() != null && myRenderer == null) {
                    myRenderer = new BlockEntityWithoutLevelRenderer(
                            Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                            Minecraft.getInstance().getEntityModels()) {
                        private StoneMortarBlockEntity blockEntity;

                        @Override
                        public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType,
                                                 @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
                            if (blockEntity == null) {
                                blockEntity = BlockEntityRegistry.STONE_MORTAR.get().create(BlockPos.ZERO,
                                        BlockRegistry.STONE_MORTAR.get().defaultBlockState());
                            }
                            Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, matrix,
                                    buffer, x, y);
                        }
                    };
                }

                return myRenderer;
            }
        });
    }
}
