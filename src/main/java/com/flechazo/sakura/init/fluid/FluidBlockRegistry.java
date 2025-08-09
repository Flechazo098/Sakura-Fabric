package com.flechazo.sakura.init.fluid;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.FluidRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;

public class FluidBlockRegistry {

    public static LiquidBlock FOOD_OIL_BLOCK;
    public static LiquidBlock DOBUROKU_BLOCK;
    public static LiquidBlock SAKE_BLOCK;
    public static LiquidBlock SHOUCHU_BLOCK;
    public static LiquidBlock BEER_BLOCK;
    public static LiquidBlock WHISKEY_BLOCK;
    public static LiquidBlock RED_WINE_BLOCK;
    public static LiquidBlock WHITE_WINE_BLOCK;
    public static LiquidBlock BRANDY_BLOCK;
    public static LiquidBlock RUM_BLOCK;
    public static LiquidBlock CHAMPAGNE_BLOCK;

    public static void initialize() {
        FOOD_OIL_BLOCK = register("food_oil",
                new LiquidBlock(FluidRegistry.FOOD_OIL, Block.Properties.copy(Blocks.WATER).noLootTable()));

        DOBUROKU_BLOCK = register("doburoku",
                new LiquidBlock(FluidRegistry.DOBUROKU, Block.Properties.copy(Blocks.WATER).noLootTable()));

        SAKE_BLOCK = register("sake",
                new LiquidBlock(FluidRegistry.SAKE, Block.Properties.copy(Blocks.WATER).noLootTable()));

        SHOUCHU_BLOCK = register("shouchu",
                new LiquidBlock(FluidRegistry.SHOUCHU, Block.Properties.copy(Blocks.WATER).noLootTable()));

        BEER_BLOCK = register("beer",
                new LiquidBlock(FluidRegistry.BEER, Block.Properties.copy(Blocks.WATER).noLootTable()));

        WHISKEY_BLOCK = register("whiskey",
                new LiquidBlock(FluidRegistry.WHISKEY, Block.Properties.copy(Blocks.WATER).noLootTable()));

        RED_WINE_BLOCK = register("red_wine",
                new LiquidBlock(FluidRegistry.RED_WINE, Block.Properties.copy(Blocks.WATER).noLootTable()));

        WHITE_WINE_BLOCK = register("white_wine",
                new LiquidBlock(FluidRegistry.WHITE_WINE, Block.Properties.copy(Blocks.WATER).noLootTable()));

        BRANDY_BLOCK = register("brandy",
                new LiquidBlock(FluidRegistry.BRANDY, Block.Properties.copy(Blocks.WATER).noLootTable()));

        RUM_BLOCK = register("rum",
                new LiquidBlock(FluidRegistry.RUM, Block.Properties.copy(Blocks.WATER).noLootTable()));

        CHAMPAGNE_BLOCK = register("champagne",
                new LiquidBlock(FluidRegistry.CHAMPAGNE, Block.Properties.copy(Blocks.WATER).noLootTable()));
    }

    private static <T extends LiquidBlock> T register(String name, T block) {
        return Registry.register(
                BuiltInRegistries.BLOCK,
                new ResourceLocation(SakuraFabric.MODID, name),
                block
        );
    }
}