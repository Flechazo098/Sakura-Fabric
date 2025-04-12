package com.flechazo.sakura.client.render.fluid;

import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.fluid.FluidTypeRegistry;
import com.flechazo.sakura.utils.IClientFluidTypeExtensions;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * 客户端专用的流体扩展实现
 */
@Environment(EnvType.CLIENT)
public class ClientFluidExtensions {

    /**
     * 初始化客户端流体扩展
     */
    public static void initialize() {
        // 注册客户端流体扩展
        registerFluidExtensions();
    }

    /**
     * 注册流体扩展
     */
    private static void registerFluidExtensions() {
        // 为每个流体注册客户端扩展
        registerFluidExtension(FluidRegistry.FOOD_OIL);
        registerFluidExtension(FluidRegistry.FOOD_OIL_FLOWING);
        registerFluidExtension(FluidRegistry.DOBUROKU);
        registerFluidExtension(FluidRegistry.DOBUROKU_FLOWING);
        registerFluidExtension(FluidRegistry.SAKE);
        registerFluidExtension(FluidRegistry.SAKE_FLOWING);
        registerFluidExtension(FluidRegistry.SHOUCHU);
        registerFluidExtension(FluidRegistry.SHOUCHU_FLOWING);
        registerFluidExtension(FluidRegistry.BEER);
        registerFluidExtension(FluidRegistry.BEER_FLOWING);
        registerFluidExtension(FluidRegistry.WHISKEY);
        registerFluidExtension(FluidRegistry.WHISKEY_FLOWING);
        registerFluidExtension(FluidRegistry.RUM);
        registerFluidExtension(FluidRegistry.RUM_FLOWING);
        registerFluidExtension(FluidRegistry.RED_WINE);
        registerFluidExtension(FluidRegistry.RED_WINE_FLOWING);
        registerFluidExtension(FluidRegistry.WHITE_WINE);
        registerFluidExtension(FluidRegistry.WHITE_WINE_FLOWING);
        registerFluidExtension(FluidRegistry.CHAMPAGNE);
        registerFluidExtension(FluidRegistry.CHAMPAGNE_FLOWING);
        registerFluidExtension(FluidRegistry.BRANDY);
        registerFluidExtension(FluidRegistry.BRANDY_FLOWING);
    }

    /**
     * 为流体注册客户端扩展
     */
    private static void registerFluidExtension(Fluid fluid) {
        if (fluid instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            FluidType fluidType = sourceFluid.getFluidType();
            int color = FluidTypeRegistry.getFluidColor(fluidType);

            String fluidName = fluid.getClass().getSimpleName().toLowerCase();
            if (fluidName.contains("source")) {
                fluidName = fluidName.replace("source", "");
            } else if (fluidName.contains("flowing")) {
                fluidName = fluidName.replace("flowing", "");
            }

            sourceFluid.setClientExtensions(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture () {
                    return IClientFluidTypeExtensions.super.getStillTexture();
                }

                @Override
                public ResourceLocation getFlowingTexture () {
                    return IClientFluidTypeExtensions.super.getFlowingTexture();
                }

                @Override
                public int getTintColor() {
                    return color;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return color;
                }
            });
        } else if (fluid instanceof FluidRegistry.CustomFlowingFluid flowingFluid) {
            FluidType fluidType = flowingFluid.getFluidType();
            int color = FluidTypeRegistry.getFluidColor(fluidType);

            String fluidName = fluid.getClass().getSimpleName().toLowerCase();
            if (fluidName.contains("source")) {
                fluidName = fluidName.replace("source", "");
            } else if (fluidName.contains("flowing")) {
                fluidName = fluidName.replace("flowing", "");
            }

            final String finalFluidName = fluidName.trim();

            flowingFluid.setClientExtensions(new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture() {
                    return new ResourceLocation("sakura", "block/fluid/" + finalFluidName + "_still");
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return new ResourceLocation("sakura", "block/fluid/" + finalFluidName + "_flow");
                }

                @Override
                public int getTintColor() {
                    return color;
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return color;
                }
            });
        }
    }
}