package com.flechazo.sakura.init;

import com.flechazo.sakura.item.food.IFoodLike;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.ComposterBlock;

public class ComposterRegistry {

    public static void registerCompost() {
        FoodRegistry.FOODSET.values().forEach(ComposterRegistry::register);

        FoodRegistry.CUISINES.values().forEach(ComposterRegistry::register);

        register(ItemRegistry.CABBAGE_SEEDS, 0.3F);
        register(ItemRegistry.BUCKWHEAT, 0.3F);
        register(ItemRegistry.RED_BEAN, 0.3F);
        register(ItemRegistry.SOYBEAN, 0.3F);
        register(ItemRegistry.RADISH_SEEDS, 0.3F);
        register(ItemRegistry.ONION_SEEDS, 0.3F);
        register(ItemRegistry.RICE_SEEDS, 0.3F);
        register(ItemRegistry.TOMATO_SEEDS, 0.3F);
        register(ItemRegistry.TARO, 0.3F);
        register(ItemRegistry.EGGPLANT_SEEDS, 0.3F);
    }

    private static void register(Item item) {
        if(item instanceof IFoodLike food) {
            if(food.getFoodInfo().getCompostChance() > 0) register(item, food.getFoodInfo().getCompostChance());
        }
    }

    private static void register(Item item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item, chance);
    }
}