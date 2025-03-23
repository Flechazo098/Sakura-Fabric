package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.item.enums.SakuraNormalItemSet;
import com.flechazo.sakuraFabric.item.info.FoodInfo;
import com.flechazo.sakuraFabric.utils.ItemRegistryUtil;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;

import java.util.Map;
public class ItemRegistry {

    public static final Item RICE_SEEDS = registerItem("rice_seeds", new RiceSeedsItem());

    public static final Item ONION_SEEDS = registerItem("onion_seeds",
            seed(BlockRegistry.ONION_CROP));
    public static final Item RADISH_SEEDS = registerItem("radish_seeds",
            seed(BlockRegistry.RADISH_CROP));
    public static final Item CABBAGE_SEEDS = registerItem("cabbage_seeds",
            seed(BlockRegistry.CABBAGE_CROP));
    public static final Item RAPESEEDS = registerItem("rapeseeds",
            seed(BlockRegistry.RAPESEED_CROP));
    public static final Item RED_BEAN = registerItem("red_bean",
            seed(BlockRegistry.REDBEAN_CROP));
    public static final Item SOYBEAN = registerItem("soybean",
            seed(BlockRegistry.SOYBEAN_CROP));
    public static final Item BUCKWHEAT = registerItem("buckwheat",
            seed(BlockRegistry.BUCKWHEAT_CROP));

    public static final Item EGGPLANT_SEEDS = registerItem("eggplant_seeds",
            seed(BlockRegistry.EGGPLANT_CROP));
    public static final Item TOMATO_SEEDS = registerItem("tomato_seeds",
            seed(BlockRegistry.TOMATO_CROP));

    public static final ItemFoodSeeds TARO = registerItem("taro",
            seed(BlockRegistry.TARO_CROP,
                    FoodInfo.builder().name("taro").amountAndCalories(2, 0.2F).water(0F).nutrients(2F, 2F, 0F, 0F, 0F)
                            .decayModifier(2F).heatCapacity(1F).cookingTemp(480F).build()));

    public static final Map<SakuraNormalItemSet, Item> MATERIALS = ItemRegistryUtil
            .mapOfKeys(SakuraNormalItemSet.class, material -> registerItem(material.getName(), normalItem()));

    public static final Item IRON_FISH_KNIFE = registerItem("knife_fish", new KnifeItem(Tiers.IRON, 1F, -2.0F, SakuraFabric.defaultItemProperties().stacksTo(1)));
    public static final Item IRON_NOODLE_KNIFE = registerItem("knife_noodle", new KnifeItem(Tiers.IRON, 2F, -3.0F, SakuraFabric.defaultItemProperties().stacksTo(1)));

    private static Item normalItem() {
        return new Item(SakuraFabric.defaultItemProperties());
    }

    private static ItemNameBlockItem seed(Block block) {
        return new ItemNameBlockItem(block, SakuraFabric.defaultItemProperties());
    }

    private static ItemFoodSeeds seed(Block block, FoodInfo info) {
        return new ItemFoodSeeds(block, SakuraFabric.defaultItemProperties(), info);
    }

    private static <V extends Item> V registerItem(String name, V item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SakuraFabric.MODID, name), item);
    }
}
