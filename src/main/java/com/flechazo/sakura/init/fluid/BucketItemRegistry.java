package com.flechazo.sakura.init.fluid;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.FluidRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class BucketItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static Item FOOD_OIL_BUCKET;
    public static Item DOBUROKU_BUCKET;
    public static Item SAKE_BUCKET;
    public static Item SHOUCHU_BUCKET;
    public static Item BEER_BUCKET;
    public static Item WHISKEY_BUCKET;
    public static Item RED_WINE_BUCKET;
    public static Item WHITE_WINE_BUCKET;
    public static Item CHAMPAGNE_BUCKET;
    public static Item RUM_BUCKET;
    public static Item BRANDY_BUCKET;

    public static void initialize() {

        FOOD_OIL_BUCKET = registerItem("food_oil_bucket",
                new BucketItem(FluidRegistry.FOOD_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.FOOD_OIL instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(FOOD_OIL_BUCKET);
        }

        DOBUROKU_BUCKET = registerItem("doburoku_bucket",
                new BucketItem(FluidRegistry.DOBUROKU, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.DOBUROKU instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(DOBUROKU_BUCKET);
        }

        SAKE_BUCKET = registerItem("sake_bucket",
                new BucketItem(FluidRegistry.SAKE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.SAKE instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(SAKE_BUCKET);
        }

        SHOUCHU_BUCKET = registerItem("shouchu_bucket",
                new BucketItem(FluidRegistry.SHOUCHU, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.SHOUCHU instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(SHOUCHU_BUCKET);
        }

        BEER_BUCKET = registerItem("beer_bucket",
                new BucketItem(FluidRegistry.BEER, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.BEER instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(BEER_BUCKET);
        }

        WHISKEY_BUCKET = registerItem("whiskey_bucket",
                new BucketItem(FluidRegistry.WHISKEY, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

        if (FluidRegistry.WHISKEY instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(WHISKEY_BUCKET);
        }

        RED_WINE_BUCKET = registerItem("red_wine_bucket",
                new BucketItem(FluidRegistry.RED_WINE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.RED_WINE instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(RED_WINE_BUCKET);
        }

        WHITE_WINE_BUCKET = registerItem("white_wine_bucket",
                new BucketItem(FluidRegistry.WHITE_WINE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.WHITE_WINE instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(WHITE_WINE_BUCKET);
        }

        CHAMPAGNE_BUCKET = registerItem("champagne_bucket",
                new BucketItem(FluidRegistry.CHAMPAGNE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.CHAMPAGNE instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(CHAMPAGNE_BUCKET);
        }

        RUM_BUCKET = registerItem("rum_bucket",
                new BucketItem(FluidRegistry.RUM, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.RUM instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(RUM_BUCKET);
        }

        BRANDY_BUCKET = registerItem("brandy_bucket",
                new BucketItem(FluidRegistry.BRANDY, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
        if (FluidRegistry.BRANDY instanceof FluidRegistry.CustomSourceFluid sourceFluid) {
            sourceFluid.setBucketItem(BRANDY_BUCKET);
        }
    }

    private static <T extends Item> T registerItem(String name, T item) {
        T registeredItem = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SakuraFabric.MODID, name), item);
        ITEMS.add(registeredItem);
        return registeredItem;
    }

    public static List<Item> getEntries() {
        return ITEMS;
    }
}