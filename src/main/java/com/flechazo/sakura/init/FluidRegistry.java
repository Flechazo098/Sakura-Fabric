package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.fluid.BucketItemRegistry;
import com.flechazo.sakura.init.fluid.FluidBlockRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import io.github.fabricators_of_create.porting_lib.fluids.BaseFlowingFluid;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class FluidRegistry {

    public static FlowingFluid FOOD_OIL;
    public static FlowingFluid FOOD_OIL_FLOWING;
    public static FlowingFluid DOBUROKU;
    public static FlowingFluid DOBUROKU_FLOWING;
    public static FlowingFluid SAKE;
    public static FlowingFluid SAKE_FLOWING;
    public static FlowingFluid SHOUCHU;
    public static FlowingFluid SHOUCHU_FLOWING;
    public static FlowingFluid BEER;
    public static FlowingFluid BEER_FLOWING;
    public static FlowingFluid WHISKEY;
    public static FlowingFluid WHISKEY_FLOWING;
    public static FlowingFluid RUM;
    public static FlowingFluid RUM_FLOWING;
    public static FlowingFluid RED_WINE;
    public static FlowingFluid RED_WINE_FLOWING;
    public static FlowingFluid WHITE_WINE;
    public static FlowingFluid WHITE_WINE_FLOWING;
    public static FlowingFluid CHAMPAGNE;
    public static FlowingFluid CHAMPAGNE_FLOWING;
    public static FlowingFluid BRANDY;
    public static FlowingFluid BRANDY_FLOWING;

    public static void initialize() {
        // FOOD_OIL
        FOOD_OIL = registerFluid("food_oil", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.FOOD_OIL,
                () -> FOOD_OIL,
                () -> FOOD_OIL_FLOWING,
                () -> FluidBlockRegistry.FOOD_OIL_BLOCK,
                () -> BucketItemRegistry.FOOD_OIL_BUCKET
        )));
        FOOD_OIL_FLOWING = registerFluid("food_oil_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.FOOD_OIL,
                () -> FOOD_OIL,
                () -> FOOD_OIL_FLOWING,
                () -> FluidBlockRegistry.FOOD_OIL_BLOCK,
                () -> BucketItemRegistry.FOOD_OIL_BUCKET
        )));

        // DOBUROKU
        DOBUROKU = registerFluid("doburoku", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.DOBUROKU,
                () -> DOBUROKU,
                () -> DOBUROKU_FLOWING,
                () -> FluidBlockRegistry.DOBUROKU_BLOCK,
                () -> BucketItemRegistry.DOBUROKU_BUCKET
        )));
        DOBUROKU_FLOWING = registerFluid("doburoku_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.DOBUROKU,
                () -> DOBUROKU,
                () -> DOBUROKU_FLOWING,
                () -> FluidBlockRegistry.DOBUROKU_BLOCK,
                () -> BucketItemRegistry.DOBUROKU_BUCKET
        )));

        // SAKE
        SAKE = registerFluid("sake", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.SAKE,
                () -> SAKE,
                () -> SAKE_FLOWING,
                () -> FluidBlockRegistry.SAKE_BLOCK,
                () -> BucketItemRegistry.SAKE_BUCKET
        )));
        SAKE_FLOWING = registerFluid("sake_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.SAKE,
                () -> SAKE,
                () -> SAKE_FLOWING,
                () -> FluidBlockRegistry.SAKE_BLOCK,
                () -> BucketItemRegistry.SAKE_BUCKET
        )));

        // SHOUCHU
        SHOUCHU = registerFluid("shouchu", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.SHOUCHU,
                () -> SHOUCHU,
                () -> SHOUCHU_FLOWING,
                () -> FluidBlockRegistry.SHOUCHU_BLOCK,
                () -> BucketItemRegistry.SHOUCHU_BUCKET
        )));
        SHOUCHU_FLOWING = registerFluid("shouchu_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.SHOUCHU,
                () -> SHOUCHU,
                () -> SHOUCHU_FLOWING,
                () -> FluidBlockRegistry.SHOUCHU_BLOCK,
                () -> BucketItemRegistry.SHOUCHU_BUCKET
        )));

        // BEER
        BEER = registerFluid("beer", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.BEER,
                () -> BEER,
                () -> BEER_FLOWING,
                () -> FluidBlockRegistry.BEER_BLOCK,
                () -> BucketItemRegistry.BEER_BUCKET
        )));
        BEER_FLOWING = registerFluid("beer_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.BEER,
                () -> BEER,
                () -> BEER_FLOWING,
                () -> FluidBlockRegistry.BEER_BLOCK,
                () -> BucketItemRegistry.BEER_BUCKET
        )));

        // WHISKEY
        WHISKEY = registerFluid("whiskey", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.WHISKEY,
                () -> WHISKEY,
                () -> WHISKEY_FLOWING,
                () -> FluidBlockRegistry.WHISKEY_BLOCK,
                () -> BucketItemRegistry.WHISKEY_BUCKET
        )));
        WHISKEY_FLOWING = registerFluid("whiskey_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.WHISKEY,
                () -> WHISKEY,
                () -> WHISKEY_FLOWING,
                () -> FluidBlockRegistry.WHISKEY_BLOCK,
                () -> BucketItemRegistry.WHISKEY_BUCKET
        )));

        // RUM
        RUM = registerFluid("rum", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.RUM,
                () -> RUM,
                () -> RUM_FLOWING,
                () -> FluidBlockRegistry.RUM_BLOCK,
                () -> BucketItemRegistry.RUM_BUCKET
        )));
        RUM_FLOWING = registerFluid("rum_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.RUM,
                () -> RUM,
                () -> RUM_FLOWING,
                () -> FluidBlockRegistry.RUM_BLOCK,
                () -> BucketItemRegistry.RUM_BUCKET
        )));

        // RED_WINE
        RED_WINE = registerFluid("red_wine", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.RED_WINE,
                () -> RED_WINE,
                () -> RED_WINE_FLOWING,
                () -> FluidBlockRegistry.RED_WINE_BLOCK,
                () -> BucketItemRegistry.RED_WINE_BUCKET
        )));
        RED_WINE_FLOWING = registerFluid("red_wine_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.RED_WINE,
                () -> RED_WINE,
                () -> RED_WINE_FLOWING,
                () -> FluidBlockRegistry.RED_WINE_BLOCK,
                () -> BucketItemRegistry.RED_WINE_BUCKET
        )));

        // WHITE_WINE
        WHITE_WINE = registerFluid("white_wine", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.WHITE_WINE,
                () -> WHITE_WINE,
                () -> WHITE_WINE_FLOWING,
                () -> FluidBlockRegistry.WHITE_WINE_BLOCK,
                () -> BucketItemRegistry.WHITE_WINE_BUCKET
        )));
        WHITE_WINE_FLOWING = registerFluid("white_wine_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.WHITE_WINE,
                () -> WHITE_WINE,
                () -> WHITE_WINE_FLOWING,
                () -> FluidBlockRegistry.WHITE_WINE_BLOCK,
                () -> BucketItemRegistry.WHITE_WINE_BUCKET
        )));

        // CHAMPAGNE
        CHAMPAGNE = registerFluid("champagne", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.CHAMPAGNE,
                () -> CHAMPAGNE,
                () -> CHAMPAGNE_FLOWING,
                () -> FluidBlockRegistry.CHAMPAGNE_BLOCK,
                () -> BucketItemRegistry.CHAMPAGNE_BUCKET
        )));
        CHAMPAGNE_FLOWING = registerFluid("champagne_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.CHAMPAGNE,
                () -> CHAMPAGNE,
                () -> CHAMPAGNE_FLOWING,
                () -> FluidBlockRegistry.CHAMPAGNE_BLOCK,
                () -> BucketItemRegistry.CHAMPAGNE_BUCKET
        )));

        // BRANDY
        BRANDY = registerFluid("brandy", new BaseFlowingFluid.Source(createProperties(
                () -> FluidTypeRegistry.BRANDY,
                () -> BRANDY,
                () -> BRANDY_FLOWING,
                () -> FluidBlockRegistry.BRANDY_BLOCK,
                () -> BucketItemRegistry.BRANDY_BUCKET
        )));
        BRANDY_FLOWING = registerFluid("brandy_flowing", new BaseFlowingFluid.Flowing(createProperties(
                () -> FluidTypeRegistry.BRANDY,
                () -> BRANDY,
                () -> BRANDY_FLOWING,
                () -> FluidBlockRegistry.BRANDY_BLOCK,
                () -> BucketItemRegistry.BRANDY_BUCKET
        )));
    }

    private static BaseFlowingFluid.Properties createProperties(
            Supplier<FluidType> fluidType,
            Supplier<Fluid> still,
            Supplier<Fluid> flowing,
            Supplier<LiquidBlock> block,
            Supplier<Item> bucket) {
        return new BaseFlowingFluid.Properties(fluidType, still, flowing)
                .block(block)
                .bucket(bucket)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1)
                .explosionResistance(100.0F)
                .tickRate(5);
    }

    private static <T extends Fluid> T registerFluid(String name, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(SakuraFabric.MODID, name), fluid);
    }
}