package com.flechazo.sakura.fluid;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.FluidRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;

import java.util.ArrayList;
import java.util.List;

public class FluidBlockRegistry {
    // 存储所有注册的流体方块
    public static final List<LiquidBlock> BLOCKS = new ArrayList<>();

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
                new LiquidBlock(FluidRegistry.FOOD_OIL, Block.Properties.copy(Blocks.WATER)));

        DOBUROKU_BLOCK = register("doburoku",
                new LiquidBlock(FluidRegistry.DOBUROKU, Block.Properties.copy(Blocks.WATER)));

        SAKE_BLOCK = register("sake",
                new LiquidBlock(FluidRegistry.SAKE, Block.Properties.copy(Blocks.WATER)));

        SHOUCHU_BLOCK = register("shouchu",
                new LiquidBlock(FluidRegistry.SHOUCHU, Block.Properties.copy(Blocks.WATER)));

        BEER_BLOCK = register("beer",
                new LiquidBlock(FluidRegistry.BEER, Block.Properties.copy(Blocks.WATER)));

        WHISKEY_BLOCK = register("whiskey",
                new LiquidBlock(FluidRegistry.WHISKEY, Block.Properties.copy(Blocks.WATER)));

        RED_WINE_BLOCK = register("red_wine",
                new LiquidBlock(FluidRegistry.RED_WINE, Block.Properties.copy(Blocks.WATER)));

        WHITE_WINE_BLOCK = register("white_wine",
                new LiquidBlock(FluidRegistry.WHITE_WINE, Block.Properties.copy(Blocks.WATER)));

        BRANDY_BLOCK = register("brandy",
                new LiquidBlock(FluidRegistry.BRANDY, Block.Properties.copy(Blocks.WATER)));

        RUM_BLOCK = register("rum",
                new LiquidBlock(FluidRegistry.RUM, Block.Properties.copy(Blocks.WATER)));

        CHAMPAGNE_BLOCK = register("champagne",
                new LiquidBlock(FluidRegistry.CHAMPAGNE, Block.Properties.copy(Blocks.WATER)));
    }

    private static <T extends LiquidBlock> T register(String name, T block) {
        T registeredBlock = Registry.register(
                BuiltInRegistries.BLOCK,
                new ResourceLocation(SakuraFabric.MODID, name),
                block
        );
        BLOCKS.add(registeredBlock);
        return registeredBlock;
    }
}