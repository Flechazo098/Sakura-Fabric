package com.flechazo.sakura.utils;

import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;

@SuppressWarnings("UnstableApiUsage")
public class RenderUtils {
    /**
     * Binds a texture for rendering
     */
    public static void bindTexture(ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
    }

    /**
     * Sets up the shader for rendering
     */
    public static void setup(ResourceLocation texture, float red, float green, float blue, float alpha) {
        bindTexture(texture);
        RenderSystem.setShaderColor(red, green, blue, alpha);
    }

    /**
     * Sets up the shader for rendering
     */
    public static void setup(ResourceLocation texture) {
        setup(texture, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void renderFluidStack(int x, int y, int width, int height, float depth, FluidStack fluidStack) {
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        FluidRenderInfo renderInfo = getFluidRenderInfo(fluidStack);

        if (renderInfo.sprite == null) {
            RenderSystem.disableBlend();
            return;
        }

        renderSpriteWithColor(x, y, width, height, depth, renderInfo.sprite, renderInfo.color);
        RenderSystem.disableBlend();
    }

    private static FluidRenderInfo getFluidRenderInfo(FluidStack fluidStack) {
        FluidType fluidType = fluidStack.getFluid().getFluidType();

        FluidVariant variant = FluidVariant.of(fluidStack.getFluid());
        TextureAtlasSprite[] sprites = FluidVariantRendering.getSprites(variant);
        if (sprites != null && sprites.length > 0) {
            int color = FluidVariantRendering.getColor(variant);
            return new FluidRenderInfo(sprites[0], color);
        }

        var handler = FluidRenderHandlerRegistry.INSTANCE.get(fluidStack.getFluid());
        if (handler != null) {
            TextureAtlasSprite[] handlerSprites = handler.getFluidSprites(null, null, fluidStack.getFluid().defaultFluidState());
            if (handlerSprites != null && handlerSprites.length > 0) {
                int color = handler.getFluidColor(null, null, fluidStack.getFluid().defaultFluidState()) | 0xFF000000;
                return new FluidRenderInfo(handlerSprites[0], color);
            }
        }

        int modFluidColor = FluidTypeRegistry.getFluidColor(fluidType);
        if (modFluidColor != 0xFFFFFFFF) {
            ResourceLocation textureLocation = getFluidTextureLocation(fluidStack);
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureLocation);
            return new FluidRenderInfo(sprite, modFluidColor);
        }

        if (fluidType == PortingLibFluids.WATER_TYPE) {
            ResourceLocation texture = new ResourceLocation("minecraft", "block/water_still");
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
            return new FluidRenderInfo(sprite, 0xFF3F76E4);
        } else if (fluidType == PortingLibFluids.LAVA_TYPE) {
            ResourceLocation texture = new ResourceLocation("minecraft", "block/lava_still");
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
            return new FluidRenderInfo(sprite, 0xFFFF6600);
        }

        if (fluidStack.getFluid() == Fluids.WATER) {
            ResourceLocation texture = new ResourceLocation("minecraft", "block/water_still");
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
            return new FluidRenderInfo(sprite, 0xFF3F76E4);
        } else if (fluidStack.getFluid() == Fluids.LAVA) {
            ResourceLocation texture = new ResourceLocation("minecraft", "block/lava_still");
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
            return new FluidRenderInfo(sprite, 0xFFFF6600);
        }

        ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluidStack.getFluid());
        {
            ResourceLocation textureLocation = new ResourceLocation(fluidId.getNamespace(), "block/" + fluidId.getPath() + "_still");
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureLocation);

            if (!sprite.contents().name().getPath().contains("missingno")) {
                return new FluidRenderInfo(sprite, 0xFFFFFFFF);
            }

            String path = fluidId.getPath();
            if (path.endsWith("_flowing")) {
                path = path.substring(0, path.length() - 8);
                textureLocation = new ResourceLocation(fluidId.getNamespace(), "block/" + path + "_still");
                sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(textureLocation);
                if (!sprite.contents().name().getPath().contains("missingno")) {
                    return new FluidRenderInfo(sprite, 0xFFFFFFFF);
                }
            }
        }

        ResourceLocation texture = new ResourceLocation("minecraft", "block/water_still");
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(texture);
        return new FluidRenderInfo(sprite, 0xFFFFFFFF);
    }


    private static ResourceLocation getFluidTextureLocation(FluidStack fluidStack) {
        ResourceLocation fluidId = BuiltInRegistries.FLUID.getKey(fluidStack.getFluid());
        String path = fluidId.getPath();
        if (path.endsWith("_flowing")) {
            path = path.substring(0, path.length() - 8);
        }
        return new ResourceLocation(fluidId.getNamespace(), "block/" + path + "_still");
    }

    private static void renderSpriteWithColor(int x, int y, int width, int height, float depth, TextureAtlasSprite sprite, int color) {
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        float u2 = sprite.getU1();
        float v2 = sprite.getV1();

        do {
            int currentHeight = Math.min(sprite.contents().height(), height);
            height -= currentHeight;
            int x2 = x;
            int width2 = width;

            do {
                int currentWidth = Math.min(sprite.contents().width(), width2);
                width2 -= currentWidth;
                bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                bufferbuilder.vertex(x2, y, depth).uv(u1, v1).color(color >> 16 & 255, color >> 8 & 255, color & 255, 255).endVertex();
                bufferbuilder.vertex(x2, y + currentHeight, depth).uv(u1, v2).color(color >> 16 & 255, color >> 8 & 255, color & 255, 255).endVertex();
                bufferbuilder.vertex(x2 + currentWidth, y + currentHeight, depth).uv(u2, v2).color(color >> 16 & 255, color >> 8 & 255, color & 255, 255).endVertex();
                bufferbuilder.vertex(x2 + currentWidth, y, depth).uv(u2, v1).color(color >> 16 & 255, color >> 8 & 255, color & 255, 255).endVertex();
                tessellator.end();
                x2 += currentWidth;
            } while (width2 > 0);

            y += currentHeight;
        } while (height > 0);
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
        return (c >> 8) & 255;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    private record FluidRenderInfo(TextureAtlasSprite sprite, int color) {
    }
}