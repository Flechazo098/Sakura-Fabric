package com.flechazo.sakura.fluid;

import com.flechazo.sakura.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import io.github.fabricators_of_create.porting_lib.fluids.sound.SoundActions;
import io.github.fabricators_of_create.porting_lib.fluids.wrapper.FabricFluidTypeWrapper;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidTypeRegistry {
    // 存储所有注册的流体类型
    public static final List<FluidType> FLUID_TYPES = new ArrayList<>();
    // 存储流体类型对应的颜色
    private static final Map<FluidType, Integer> FLUID_COLORS = new HashMap<>();

    // 声明静态字段但不初始化
    public static FluidType FOOD_OIL;
    public static FluidType DOBUROKU;
    public static FluidType SAKE;
    public static FluidType SHOUCHU;
    public static FluidType BEER;
    public static FluidType WHISKEY;
    public static FluidType RUM;
    public static FluidType RED_WINE;
    public static FluidType WHITE_WINE;
    public static FluidType CHAMPAGNE;
    public static FluidType BRANDY;

    // 添加初始化方法
    public static void initialize() {
        FOOD_OIL = register("food_oil", 0xFFFFF050);
        DOBUROKU = register("doburoku", 0xFFCCC299);
        SAKE = register("sake", 0xDDFFF8CC);
        SHOUCHU = register("shouchu", 0xBBFFFCF2);
        BEER = register("beer", 0xFFF2A918);
        WHISKEY = register("whiskey", 0xFFA52121);
        RUM = register("rum", 0xFFFFAA32);
        RED_WINE = register("red_wine", 0xFFA71844);
        WHITE_WINE = register("white_wine", 0xFFFFF8B2);
        CHAMPAGNE = register("champagne", 0xFFFFE772);
        BRANDY = register("brandy", 0xFFBF2F00);
    }

    private static FluidType register(String name, int color) {
        // 创建基本的流体类型
        FluidType fluidType = new FluidType(FluidType.Properties.create()
                .temperature(27)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .density(3000).viscosity(1000));

        // 注册流体类型
        FluidType registeredType = Registry.register(
                PortingLibFluids.FLUID_TYPES,
                new ResourceLocation(SakuraFabric.MODID, name),
                fluidType
        );

        // 存储流体类型和对应的颜色
        FLUID_COLORS.put(registeredType, color);
        FLUID_TYPES.add(registeredType);
        return registeredType;
    }

    // 获取流体类型的颜色
    public static int getFluidColor(FluidType fluidType) {
        return FLUID_COLORS.getOrDefault(fluidType, -1);
    }

    // 为流体注册属性处理器
    public static void registerFluidAttributes() {
        // 为每个流体注册 FluidVariantAttributeHandler
        registerFluidAttribute(FluidRegistry.FOOD_OIL, FOOD_OIL);
        registerFluidAttribute(FluidRegistry.DOBUROKU, DOBUROKU);
        registerFluidAttribute(FluidRegistry.SAKE, SAKE);
        registerFluidAttribute(FluidRegistry.SHOUCHU, SHOUCHU);
        registerFluidAttribute(FluidRegistry.BEER, BEER);
        registerFluidAttribute(FluidRegistry.WHISKEY, WHISKEY);
        registerFluidAttribute(FluidRegistry.RUM, RUM);
        registerFluidAttribute(FluidRegistry.RED_WINE, RED_WINE);
        registerFluidAttribute(FluidRegistry.WHITE_WINE, WHITE_WINE);
        registerFluidAttribute(FluidRegistry.CHAMPAGNE, CHAMPAGNE);
        registerFluidAttribute(FluidRegistry.BRANDY, BRANDY);
    }

    private static void registerFluidAttribute(Fluid fluid, FluidType type) {
        // 使用 FabricFluidTypeWrapper 将 FluidType 包装为 FluidVariantAttributeHandler
        FluidVariantAttributes.register(fluid, new FabricFluidTypeWrapper(type));
    }

    // 客户端初始化方法，应该在客户端初始化时调用
    public static void setupFluidRendering() {
        // 为每个流体设置渲染处理器
        setupFluidRendering(FluidRegistry.FOOD_OIL, FluidRegistry.FOOD_OIL_FLOWING, getFluidColor(FOOD_OIL));
        setupFluidRendering(FluidRegistry.DOBUROKU, FluidRegistry.DOBUROKU_FLOWING, getFluidColor(DOBUROKU));
        setupFluidRendering(FluidRegistry.SAKE, FluidRegistry.SAKE_FLOWING, getFluidColor(SAKE));
        setupFluidRendering(FluidRegistry.SHOUCHU, FluidRegistry.SHOUCHU_FLOWING, getFluidColor(SHOUCHU));
        setupFluidRendering(FluidRegistry.BEER, FluidRegistry.BEER_FLOWING, getFluidColor(BEER));
        setupFluidRendering(FluidRegistry.WHISKEY, FluidRegistry.WHISKEY_FLOWING, getFluidColor(WHISKEY));
        setupFluidRendering(FluidRegistry.RUM, FluidRegistry.RUM_FLOWING, getFluidColor(RUM));
        setupFluidRendering(FluidRegistry.RED_WINE, FluidRegistry.RED_WINE_FLOWING, getFluidColor(RED_WINE));
        setupFluidRendering(FluidRegistry.WHITE_WINE, FluidRegistry.WHITE_WINE_FLOWING, getFluidColor(WHITE_WINE));
        setupFluidRendering(FluidRegistry.CHAMPAGNE, FluidRegistry.CHAMPAGNE_FLOWING, getFluidColor(CHAMPAGNE));
        setupFluidRendering(FluidRegistry.BRANDY, FluidRegistry.BRANDY_FLOWING, getFluidColor(BRANDY));
    }

    // 设置流体渲染的辅助方法
    private static void setupFluidRendering(Fluid still, Fluid flowing, int color) {
        ResourceLocation stillTexture = new ResourceLocation("block/water_still");
        ResourceLocation flowingTexture = new ResourceLocation("block/water_flow");

        FluidRenderHandlerRegistry.INSTANCE.register(
                still, flowing,
                new SimpleFluidRenderHandler(stillTexture, flowingTexture, color)
        );
    }
}