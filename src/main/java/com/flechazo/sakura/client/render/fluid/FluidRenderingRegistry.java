package com.flechazo.sakura.client.render.fluid;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.wrapper.FabricFluidTypeWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("UnstableApiUsage")
@Environment(EnvType.CLIENT)
public class FluidRenderingRegistry {

    public static void initialize() {
        registerFluidAttributes();
        registerFluidRenderHandlers();
    }

    private static void registerFluidAttributes() {
        FluidVariantAttributes.register(FluidRegistry.FOOD_OIL, new FabricFluidTypeWrapper(FluidTypeRegistry.FOOD_OIL));
        FluidVariantAttributes.register(FluidRegistry.DOBUROKU, new FabricFluidTypeWrapper(FluidTypeRegistry.DOBUROKU));
        FluidVariantAttributes.register(FluidRegistry.SAKE, new FabricFluidTypeWrapper(FluidTypeRegistry.SAKE));
        FluidVariantAttributes.register(FluidRegistry.SHOUCHU, new FabricFluidTypeWrapper(FluidTypeRegistry.SHOUCHU));
        FluidVariantAttributes.register(FluidRegistry.BEER, new FabricFluidTypeWrapper(FluidTypeRegistry.BEER));
        FluidVariantAttributes.register(FluidRegistry.WHISKEY, new FabricFluidTypeWrapper(FluidTypeRegistry.WHISKEY));
        FluidVariantAttributes.register(FluidRegistry.RUM, new FabricFluidTypeWrapper(FluidTypeRegistry.RUM));
        FluidVariantAttributes.register(FluidRegistry.RED_WINE, new FabricFluidTypeWrapper(FluidTypeRegistry.RED_WINE));
        FluidVariantAttributes.register(FluidRegistry.WHITE_WINE, new FabricFluidTypeWrapper(FluidTypeRegistry.WHITE_WINE));
        FluidVariantAttributes.register(FluidRegistry.CHAMPAGNE, new FabricFluidTypeWrapper(FluidTypeRegistry.CHAMPAGNE));
        FluidVariantAttributes.register(FluidRegistry.BRANDY, new FabricFluidTypeWrapper(FluidTypeRegistry.BRANDY));
    }

    private static void registerFluidRenderHandlers() {
        registerFluidRenderHandler("food_oil", FluidRegistry.FOOD_OIL, FluidRegistry.FOOD_OIL_FLOWING, FluidTypeRegistry.FOOD_OIL);
        registerFluidRenderHandler("doburoku", FluidRegistry.DOBUROKU, FluidRegistry.DOBUROKU_FLOWING, FluidTypeRegistry.DOBUROKU);
        registerFluidRenderHandler("sake", FluidRegistry.SAKE, FluidRegistry.SAKE_FLOWING, FluidTypeRegistry.SAKE);
        registerFluidRenderHandler("shouchu", FluidRegistry.SHOUCHU, FluidRegistry.SHOUCHU_FLOWING, FluidTypeRegistry.SHOUCHU);
        registerFluidRenderHandler("beer", FluidRegistry.BEER, FluidRegistry.BEER_FLOWING, FluidTypeRegistry.BEER);
        registerFluidRenderHandler("whiskey", FluidRegistry.WHISKEY, FluidRegistry.WHISKEY_FLOWING, FluidTypeRegistry.WHISKEY);
        registerFluidRenderHandler("rum", FluidRegistry.RUM, FluidRegistry.RUM_FLOWING, FluidTypeRegistry.RUM);
        registerFluidRenderHandler("red_wine", FluidRegistry.RED_WINE, FluidRegistry.RED_WINE_FLOWING, FluidTypeRegistry.RED_WINE);
        registerFluidRenderHandler("white_wine", FluidRegistry.WHITE_WINE, FluidRegistry.WHITE_WINE_FLOWING, FluidTypeRegistry.WHITE_WINE);
        registerFluidRenderHandler("champagne", FluidRegistry.CHAMPAGNE, FluidRegistry.CHAMPAGNE_FLOWING, FluidTypeRegistry.CHAMPAGNE);
        registerFluidRenderHandler("brandy", FluidRegistry.BRANDY, FluidRegistry.BRANDY_FLOWING, FluidTypeRegistry.BRANDY);
    }

    private static void registerFluidRenderHandler(String name, Fluid still, Fluid flowing, FluidType fluidType) {
        ResourceLocation stillTexture = new ResourceLocation(SakuraFabric.MODID, "block/" + name + "_still");
        ResourceLocation flowingTexture = new ResourceLocation(SakuraFabric.MODID, "block/" + name + "_flow");
        int tintColor = FluidTypeRegistry.getFluidColor(fluidType);

        FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing,
                new SimpleFluidRenderHandler(stillTexture, flowingTexture, tintColor)
        );
    }
}