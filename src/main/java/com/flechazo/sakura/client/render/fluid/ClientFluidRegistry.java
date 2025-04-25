package com.flechazo.sakura.client.render.fluid;

import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.flechazo.sakura.utils.IClientFluidTypeExtensions;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * 客户端专用的流体注册类
 * 用于设置流体的客户端渲染属性
 */
@Environment(EnvType.CLIENT)
public class ClientFluidRegistry {

    @Environment(EnvType.CLIENT)
    public static void initialize() {
        setupFluidExtensions();
    }

    @Environment(EnvType.CLIENT)
    private static void setupFluidExtensions() {
        setupSourceFluidExtensions(FluidRegistry.FOOD_OIL, "food_oil");
        setupSourceFluidExtensions(FluidRegistry.DOBUROKU, "doburoku");
        setupSourceFluidExtensions(FluidRegistry.SAKE, "sake");
        setupSourceFluidExtensions(FluidRegistry.SHOUCHU, "shouchu");
        setupSourceFluidExtensions(FluidRegistry.BEER, "beer");
        setupSourceFluidExtensions(FluidRegistry.WHISKEY, "whiskey");
        setupSourceFluidExtensions(FluidRegistry.RUM, "rum");
        setupSourceFluidExtensions(FluidRegistry.RED_WINE, "red_wine");
        setupSourceFluidExtensions(FluidRegistry.WHITE_WINE, "white_wine");
        setupSourceFluidExtensions(FluidRegistry.CHAMPAGNE, "champagne");
        setupSourceFluidExtensions(FluidRegistry.BRANDY, "brandy");

        setupFlowingFluidExtensions(FluidRegistry.FOOD_OIL_FLOWING, "food_oil");
        setupFlowingFluidExtensions(FluidRegistry.DOBUROKU_FLOWING, "doburoku");
        setupFlowingFluidExtensions(FluidRegistry.SAKE_FLOWING, "sake");
        setupFlowingFluidExtensions(FluidRegistry.SHOUCHU_FLOWING, "shouchu");
        setupFlowingFluidExtensions(FluidRegistry.BEER_FLOWING, "beer");
        setupFlowingFluidExtensions(FluidRegistry.WHISKEY_FLOWING, "whiskey");
        setupFlowingFluidExtensions(FluidRegistry.RUM_FLOWING, "rum");
        setupFlowingFluidExtensions(FluidRegistry.RED_WINE_FLOWING, "red_wine");
        setupFlowingFluidExtensions(FluidRegistry.WHITE_WINE_FLOWING, "white_wine");
        setupFlowingFluidExtensions(FluidRegistry.CHAMPAGNE_FLOWING, "champagne");
        setupFlowingFluidExtensions(FluidRegistry.BRANDY_FLOWING, "brandy");
    }

    @Environment(EnvType.CLIENT)
    private static void setupSourceFluidExtensions(Fluid fluid, String name) {
        if (fluid instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            int color = FluidTypeRegistry.getFluidColor(sourceFluid.getFluidType());

            sourceFluid.setClientExtensions(new IClientFluidTypeExtensions() {
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
            });

        }
    }

    @Environment(EnvType.CLIENT)
    private static void setupFlowingFluidExtensions(Fluid fluid, String name) {
        if (fluid instanceof FluidRegistry.CustomFlowingFluid flowingFluid) {
            int color = FluidTypeRegistry.getFluidColor(flowingFluid.getFluidType());

            flowingFluid.setClientExtensions(new IClientFluidTypeExtensions() {
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
            });
        }
    }
}