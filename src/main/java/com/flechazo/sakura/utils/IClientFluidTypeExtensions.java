package com.flechazo.sakura.utils;

import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.flechazo.sakura.init.FluidRegistry;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
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
        // 首先尝试从流体获取扩展
        if (fluid instanceof FluidExtensionProvider clientProvider) {
            IClientFluidTypeExtensions extensions = clientProvider.getExtensions();
            if (extensions != null) {
                return extensions;
            }
        }

        // 如果流体不是 FluidExtensionProvider 的实例，尝试创建一个基于流体类型的扩展
        if (fluid instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            FluidType fluidType = sourceFluid.getFluidType();
            int color = FluidTypeRegistry.getFluidColor(fluidType);

            return new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation("block/water_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation("block/water_flow");
                }

                @Override
                public int getTintColor() {
                    return color;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return color;
                }
            };
        } else if (fluid instanceof FluidRegistry.CustomFlowingFluid flowingFluid) {
            FluidType fluidType = flowingFluid.getFluidType();
            int color = FluidTypeRegistry.getFluidColor(fluidType);

            return new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation("block/water_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation("block/water_flow");
                }

                @Override
                public int getTintColor() {
                    return color;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return color;
                }
            };
        }

        // 如果以上方法都失败，使用默认扩展
        return DEFAULT;
    }


    @Environment(EnvType.CLIENT)
    interface FluidExtensionProvider extends com.flechazo.sakura.utils.FluidExtensionProvider {
        IClientFluidTypeExtensions getExtensions();
    }
}