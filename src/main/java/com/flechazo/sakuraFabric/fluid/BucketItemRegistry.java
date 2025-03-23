package com.flechazo.sakuraFabric.fluid;

import com.flechazo.sakuraFabric.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BucketItemRegistry {
    // 移除 DeferredRegister 和 EventBusSubscriber 注解

    public static final Item FOOD_OIL_BUCKET = registerItem("food_oil_bucket",
            new BucketItem(FluidRegistry.FOOD_OIL, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item DOBUROKU_BUCKET = registerItem("doburoku_bucket",
            new BucketItem(FluidRegistry.DOBUROKU, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item SAKE_BUCKET = registerItem("sake_bucket",
            new BucketItem(FluidRegistry.SAKE, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item SHOUCHU_BUCKET = registerItem("shouchu_bucket",
            new BucketItem(FluidRegistry.SHOUCHU, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item BEER_BUCKET = registerItem("beer_bucket",
            new BucketItem(FluidRegistry.BEER, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item WHISKEY_BUCKET = registerItem("whiskey_bucket",
            new BucketItem(FluidRegistry.WHISKEY, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item RED_WINE_BUCKET = registerItem("red_wine_bucket",
            new BucketItem(FluidRegistry.RED_WINE, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item WHITE_WINE_BUCKET = registerItem("white_wine_bucket",
            new BucketItem(FluidRegistry.WHITE_WINE, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item CHAMPAGNE_BUCKET = registerItem("champagne_bucket",
            new BucketItem(FluidRegistry.CHAMPAGNE, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item RUM_BUCKET = registerItem("rum_bucket",
            new BucketItem(FluidRegistry.RUM, new Item.Properties().craftRemainder(Items.BUCKET)));

    public static final Item BRANDY_BUCKET = registerItem("brandy_bucket",
            new BucketItem(FluidRegistry.BRANDY, new Item.Properties().craftRemainder(Items.BUCKET)));

    private static <T extends Item> T registerItem(String name, T item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SakuraFabric.MODID, name), item);
    }
}