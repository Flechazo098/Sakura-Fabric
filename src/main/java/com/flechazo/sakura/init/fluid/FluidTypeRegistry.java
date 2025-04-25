package com.flechazo.sakura.init.fluid;

import com.flechazo.sakura.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import io.github.fabricators_of_create.porting_lib.fluids.sound.SoundActions;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidTypeRegistry {
    public static final List<FluidType> FLUID_TYPES = new ArrayList<>();
    private static final Map<FluidType, Integer> FLUID_COLORS = new HashMap<>();

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

    public static void initialize() {
        FOOD_OIL = register("food_oil", 0xFFFFF050);
        DOBUROKU = register("doburoku", 0xFFCCC299);
        SAKE = register("sake", 0xFFFFF8CC);
        SHOUCHU = register("shouchu", 0xFFFFFCF2);
        BEER = register("beer", 0xFFF2A918);
        WHISKEY = register("whiskey", 0xFFA52121);
        RUM = register("rum", 0xFFFFAA32);
        RED_WINE = register("red_wine", 0xFFA71844);
        WHITE_WINE = register("white_wine", 0xFFFFF8B2);
        CHAMPAGNE = register("champagne", 0xFFFFE772);
        BRANDY = register("brandy", 0xFFBF2F00);
    }

    private static FluidType register(String name, int color) {
        // 确保颜色包含 alpha 通道
        if ((color & 0xFF000000) == 0) {
            color = color | 0xFF000000; // 添加完全不透明的 alpha 通道
        }

        FluidType fluidType = new FluidType(FluidType.Properties.create()
                .temperature(27)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                .density(3000).viscosity(1000));

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

    public static int getFluidColor(FluidType fluidType) {
        // 确保颜色包含 alpha 通道
        int color = FLUID_COLORS.getOrDefault(fluidType, 0xFFFFFFFF);

        // 如果颜色没有 alpha 通道，添加完全不透明的 alpha
        if ((color & 0xFF000000) == 0) {
            color = color | 0xFF000000;
        }


        return color;
    }
}