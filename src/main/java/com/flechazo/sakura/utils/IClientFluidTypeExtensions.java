package com.flechazo.sakura.utils;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * Fabric 版本的流体渲染扩展接口
 * 用于获取流体的纹理和颜色信息
 */
@Environment(EnvType.CLIENT)
public interface IClientFluidTypeExtensions {
    IClientFluidTypeExtensions DEFAULT = new IClientFluidTypeExtensions() { };

    /**
     * 获取流体的静态纹理
     */
    default ResourceLocation getStillTexture() {
        return new ResourceLocation("block/water_still");
    }

    /**
     * 获取流体的流动纹理
     */
    default ResourceLocation getFlowingTexture() {
        return new ResourceLocation("block/water_flow");
    }

    /**
     * 获取流体的覆盖纹理
     */
    default ResourceLocation getOverlayTexture() {
        return null;
    }

    /**
     * 获取流体的颜色
     */
    default int getTintColor() {
        return 0xFFFFFFFF;
    }

    /**
     * 获取流体堆栈的颜色
     */
    default int getTintColor(FluidStack stack) {
        return getTintColor();
    }

    /**
     * 从流体获取扩展接口
     */
    static IClientFluidTypeExtensions of(Fluid fluid) {
        if (fluid instanceof FluidExtensionProvider clientProvider) {
            return clientProvider.getExtensions();
        }
        return DEFAULT;
    }

    /**
     * 流体扩展提供者接口
     */
    @Environment(EnvType.CLIENT)
    interface FluidExtensionProvider extends com.flechazo.sakura.utils.FluidExtensionProvider {
        IClientFluidTypeExtensions getExtensions();
    }
}