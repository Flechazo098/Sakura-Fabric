package com.flechazo.sakuraFabric.fluid;

import com.flechazo.sakuraFabric.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class FluidRegistry {
    // 移除 DeferredRegister

    public static final FlowingFluid FOOD_OIL = registerFluid("food_oil",
            new PortingLibFluids.Source(FluidRegistry.FOOD_OIL_PROP));
    public static final FlowingFluid FOOD_OIL_FLOWING = registerFluid("food_oil_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.FOOD_OIL_PROP));

    public static final FlowingFluid DOBUROKU = registerFluid("doburoku",
            new PortingLibFluids.Source(FluidRegistry.DOBUROKU_PROP));
    public static final FlowingFluid DOBUROKU_FLOWING = registerFluid("doburoku_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.DOBUROKU_PROP));

    public static final FlowingFluid SAKE = registerFluid("sake",
            new PortingLibFluids.Source(FluidRegistry.SAKE_PROP));
    public static final FlowingFluid SAKE_FLOWING = registerFluid("sake_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.SAKE_PROP));

    public static final FlowingFluid SHOUCHU = registerFluid("shouchu",
            new PortingLibFluids.Source(FluidRegistry.SHOUCHU_PROP));
    public static final FlowingFluid SHOUCHU_FLOWING = registerFluid("shouchu_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.SHOUCHU_PROP));

    public static final FlowingFluid BEER = registerFluid("beer",
            new PortingLibFluids.Source(FluidRegistry.BEER_PROP));
    public static final FlowingFluid BEER_FLOWING = registerFluid("beer_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.BEER_PROP));

    public static final FlowingFluid WHISKEY = registerFluid("whiskey",
            new PortingLibFluids.Source(FluidRegistry.WHISKEY_PROP));
    public static final FlowingFluid WHISKEY_FLOWING = registerFluid("whiskey_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.WHISKEY_PROP));

    public static final FlowingFluid RUM = registerFluid("rum",
            new PortingLibFluids.Source(FluidRegistry.RUM_PROP));
    public static final FlowingFluid RUM_FLOWING = registerFluid("rum_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.RUM_PROP));

    public static final FlowingFluid RED_WINE = registerFluid("red_wine",
            new PortingLibFluids.Source(FluidRegistry.RED_WINE_PROP));
    public static final FlowingFluid RED_WINE_FLOWING = registerFluid("red_wine_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.RED_WINE_PROP));

    public static final FlowingFluid WHITE_WINE = registerFluid("white_wine",
            new PortingLibFluids.Source(FluidRegistry.WHITE_WINE_PROP));
    public static final FlowingFluid WHITE_WINE_FLOWING = registerFluid("white_wine_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.WHITE_WINE_PROP));

    public static final FlowingFluid CHAMPAGNE = registerFluid("champagne",
            new PortingLibFluids.Source(FluidRegistry.CHAMPAGNE_PROP));
    public static final FlowingFluid CHAMPAGNE_FLOWING = registerFluid("champagne_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.CHAMPAGNE_PROP));

    public static final FlowingFluid BRANDY = registerFluid("brandy",
            new PortingLibFluids.Source(FluidRegistry.BRANDY_PROP));
    public static final FlowingFluid BRANDY_FLOWING = registerFluid("brandy_flowing",
            new PortingLibFluids.Flowing(FluidRegistry.BRANDY_PROP));


    private static final PortingLibFluids.Properties FOOD_OIL_PROP =
            createProp(() -> FOOD_OIL, () -> FOOD_OIL_FLOWING, FluidTypeRegistry.FOOD_OIL, FluidBlockRegistry.FOOD_OIL_BLOCK);

    private static final PortingLibFluids.Properties DOBUROKU_PROP =
            createProp(() -> DOBUROKU, () -> DOBUROKU_FLOWING, FluidTypeRegistry.DOBUROKU, FluidBlockRegistry.DOBUROKU_BLOCK);

    private static final PortingLibFluids.Properties SAKE_PROP =
            createProp(() -> SAKE, () -> SAKE_FLOWING, FluidTypeRegistry.SAKE, FluidBlockRegistry.SAKE_BLOCK);

    private static final PortingLibFluids.Properties SHOUCHU_PROP =
            createProp(() -> SHOUCHU, () -> SHOUCHU_FLOWING, FluidTypeRegistry.SHOUCHU, FluidBlockRegistry.SHOUCHU_BLOCK);

    private static final PortingLibFluids.Properties BEER_PROP =
            createProp(() -> BEER, () -> BEER_FLOWING, FluidTypeRegistry.BEER, FluidBlockRegistry.BEER_BLOCK);

    private static final PortingLibFluids.Properties BRANDY_PROP =
            createProp(() -> BRANDY, () -> BRANDY_FLOWING, FluidTypeRegistry.BRANDY, FluidBlockRegistry.BRANDY_BLOCK);

    private static final PortingLibFluids.Properties WHISKEY_PROP =
            createProp(() -> WHISKEY, () -> WHISKEY_FLOWING, FluidTypeRegistry.WHISKEY, FluidBlockRegistry.WHISKEY_BLOCK);

    private static final PortingLibFluids.Properties RUM_PROP =
            createProp(() -> RUM, () -> RUM_FLOWING, FluidTypeRegistry.RUM, FluidBlockRegistry.RUM_BLOCK);

    private static final PortingLibFluids.Properties RED_WINE_PROP =
            createProp(() -> RED_WINE, () -> RED_WINE_FLOWING, FluidTypeRegistry.RED_WINE, FluidBlockRegistry.RED_WINE_BLOCK);

    private static final PortingLibFluids.Properties WHITE_WINE_PROP =
            createProp(() -> WHITE_WINE, () -> WHITE_WINE_FLOWING, FluidTypeRegistry.WHITE_WINE, FluidBlockRegistry.WHITE_WINE_BLOCK);

    private static final PortingLibFluids.Properties CHAMPAGNE_PROP =
            createProp(() -> CHAMPAGNE, () -> CHAMPAGNE_FLOWING, FluidTypeRegistry.CHAMPAGNE, FluidBlockRegistry.CHAMPAGNE_BLOCK);

    private static PortingLibFluids.Properties createProp (
            Supplier<? extends Fluid> still,
            Supplier<? extends Fluid> flowing,
            FluidType fluidType,
            Supplier<? extends LiquidBlock> block) {

        UnaryOperator<PortingLibFluids.Properties> blockProperties = p -> p.block(block).slopeFindDistance(3).explosionResistance(100F);
        return blockProperties.apply(new PortingLibFluids(fluidType, still, flowing));
    }

    private static <T extends Fluid> T registerFluid (String name, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(SakuraFabric.MODID, name), fluid);
    }

}