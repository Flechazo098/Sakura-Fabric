package com.flechazo.sakura.fluid;

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
        SAKE = register("sake", 0xFFFFF8CC);  // 修改为0xFFFFF8CC
        SHOUCHU = register("shouchu", 0xFFFFFCF2);  // 修改为0xFFFFFCF2
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
}