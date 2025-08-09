package com.flechazo.sakura.utils;

import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;

public class RenderUtils {
    /**
     * Binds a texture for rendering
     *
     * @param texture Texture
     */
    public static void bindTexture(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
    }

    /**
     * Sets up the shader for rendering
     *
     * @param texture Texture
     * @param red     Red tint
     * @param green   Green tint
     * @param blue    Blue tint
     * @param alpha   Alpha tint
     */
    public static void setup(ResourceLocation texture, float red, float green, float blue, float alpha) {
        bindTexture(texture);
        RenderSystem.setShaderColor(red, green, blue, alpha);
    }

    /**
     * Sets up the shader for rendering
     *
     * @param texture Texture
     */
    public static void setup(ResourceLocation texture) {
        setup(texture, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void renderFluidStack(int x, int y, int width, int height, float depth, FluidStack fluidStack) {
        if (fluidStack == null || fluidStack.isEmpty()) {
            return; // 如果流体为空，直接返回
        }

        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // 获取流体属性
        IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidStack.getFluid());

        // 获取流体纹理
        ResourceLocation stillTexture = props.getStillTexture();
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);

        // 获取流体颜色
        int col = props.getTintColor(fluidStack);

        // TODO 直接渲染纹理
        if (col == 0 || col == -1) {
            if (fluidStack.getFluid() == Fluids.WATER) {
                col = 0xFF3F76E4;
            } else if (fluidStack.getFluid() == Fluids.LAVA) {
                col = 0xFFFF4500;
            } else if (fluidStack.getFluid() instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
                col = FluidTypeRegistry.getFluidColor(sourceFluid.getFluidType());
            } else {
                col = 0xFFFFFFFF;
            }
        }

        // 提取颜色分量
        float red = ((col >> 16) & 0xFF) / 255.0F;
        float green = ((col >> 8) & 0xFF) / 255.0F;
        float blue = (col & 0xFF) / 255.0F;
        float alpha = ((col >> 24) & 0xFF) / 255.0F;

        // 设置渲染颜色
        RenderSystem.setShaderColor(red, green, blue, alpha);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        float u2 = sprite.getU1();
        float v2 = sprite.getV1();

        // 渲染流体
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(x, y, depth).uv(u1, v1).color(red, green, blue, alpha).endVertex();
        bufferbuilder.vertex(x, y + height, depth).uv(u1, v2).color(red, green, blue, alpha).endVertex();
        bufferbuilder.vertex(x + width, y + height, depth).uv(u2, v2).color(red, green, blue, alpha).endVertex();
        bufferbuilder.vertex(x + width, y, depth).uv(u2, v1).color(red, green, blue, alpha).endVertex();
        tessellator.end();

        // 重置渲染状态
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;
        RenderSystem.setShaderColor(r, g, b, a);
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }
}
