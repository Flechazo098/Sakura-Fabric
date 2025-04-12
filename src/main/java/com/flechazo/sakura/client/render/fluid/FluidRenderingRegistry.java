package com.flechazo.sakura.client.render.fluid;

import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.fluid.FluidTypeRegistry;
import com.flechazo.sakura.utils.IClientFluidTypeExtensions;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.wrapper.FabricFluidTypeWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

/**
 * 客户端专用的流体渲染注册类
 */
@Environment(EnvType.CLIENT)
public class FluidRenderingRegistry {

    /**
     * 为流体注册属性处理器
     */
    @Environment(EnvType.CLIENT)
    public static void registerFluidAttributes() {
        // 为每个流体注册 FluidVariantAttributeHandler
        registerFluidAttribute(FluidRegistry.FOOD_OIL, FluidTypeRegistry.FOOD_OIL);
        registerFluidAttribute(FluidRegistry.DOBUROKU, FluidTypeRegistry.DOBUROKU);
        registerFluidAttribute(FluidRegistry.SAKE, FluidTypeRegistry.SAKE);
        registerFluidAttribute(FluidRegistry.SHOUCHU, FluidTypeRegistry.SHOUCHU);
        registerFluidAttribute(FluidRegistry.BEER, FluidTypeRegistry.BEER);
        registerFluidAttribute(FluidRegistry.WHISKEY, FluidTypeRegistry.WHISKEY);
        registerFluidAttribute(FluidRegistry.RUM, FluidTypeRegistry.RUM);
        registerFluidAttribute(FluidRegistry.RED_WINE, FluidTypeRegistry.RED_WINE);
        registerFluidAttribute(FluidRegistry.WHITE_WINE, FluidTypeRegistry.WHITE_WINE);
        registerFluidAttribute(FluidRegistry.CHAMPAGNE, FluidTypeRegistry.CHAMPAGNE);
        registerFluidAttribute(FluidRegistry.BRANDY, FluidTypeRegistry.BRANDY);
    }

    @Environment(EnvType.CLIENT)
    private static void registerFluidAttribute(Fluid fluid, FluidType type) {
        // 使用 FabricFluidTypeWrapper 将 FluidType 包装为 FluidVariantAttributeHandler
        FluidVariantAttributes.register(fluid, new FabricFluidTypeWrapper(type));
    }

    /**
     * 注册流体渲染处理器
     */
    @Environment(EnvType.CLIENT)
    public static void registerFluidRenderHandlers() {
        // 为每个流体注册渲染处理器
        registerFluidRenderHandler(FluidRegistry.FOOD_OIL, FluidRegistry.FOOD_OIL_FLOWING);
        registerFluidRenderHandler(FluidRegistry.DOBUROKU, FluidRegistry.DOBUROKU_FLOWING);
        registerFluidRenderHandler(FluidRegistry.SAKE, FluidRegistry.SAKE_FLOWING);
        registerFluidRenderHandler(FluidRegistry.SHOUCHU, FluidRegistry.SHOUCHU_FLOWING);
        registerFluidRenderHandler(FluidRegistry.BEER, FluidRegistry.BEER_FLOWING);
        registerFluidRenderHandler(FluidRegistry.WHISKEY, FluidRegistry.WHISKEY_FLOWING);
        registerFluidRenderHandler(FluidRegistry.RUM, FluidRegistry.RUM_FLOWING);
        registerFluidRenderHandler(FluidRegistry.RED_WINE, FluidRegistry.RED_WINE_FLOWING);
        registerFluidRenderHandler(FluidRegistry.WHITE_WINE, FluidRegistry.WHITE_WINE_FLOWING);
        registerFluidRenderHandler(FluidRegistry.CHAMPAGNE, FluidRegistry.CHAMPAGNE_FLOWING);
        registerFluidRenderHandler(FluidRegistry.BRANDY, FluidRegistry.BRANDY_FLOWING);
    }

    @Environment(EnvType.CLIENT)
    private static void registerFluidRenderHandler(Fluid still, Fluid flowing) {
        if (still instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            IClientFluidTypeExtensions extensions = sourceFluid.getExtensions();
            if (extensions != null) {
                ResourceLocation stillTexture = extensions.getStillTexture();
                ResourceLocation flowingTexture = extensions.getFlowingTexture();
                int tintColor = extensions.getTintColor();

                FluidRenderHandlerRegistry.INSTANCE.register(
                        still, flowing,
                        new SimpleFluidRenderHandler(stillTexture, flowingTexture, tintColor)
                );
            }
        }
    }

    /**
     * 初始化客户端流体渲染
     */
    @Environment(EnvType.CLIENT)
    public static void initialize() {
        registerFluidAttributes();
        registerFluidRenderHandlers();
    }
}