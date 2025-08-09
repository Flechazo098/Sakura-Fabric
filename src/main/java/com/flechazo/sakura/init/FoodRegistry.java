package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.item.enums.SakuraCuisineSet;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import com.flechazo.sakura.item.food.ItemFoodBase;
import com.flechazo.sakura.item.food.info.FoodInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.EnumMap;
import java.util.Map;

public class FoodRegistry {

    public static final Map<SakuraFoodSet, ItemFoodBase> FOODSET = new EnumMap<>(SakuraFoodSet.class);
    public static final Map<SakuraCuisineSet, ItemFoodBase> CUISINES = new EnumMap<>(SakuraCuisineSet.class);

    public static void initialize() {
        for (SakuraFoodSet food : SakuraFoodSet.values()) {
            FOODSET.put(food, registerItem(food.getFoodInfo().getName(), normalFood(food.getFoodInfo())));
        }

        for (SakuraCuisineSet cuisine : SakuraCuisineSet.values()) {
            CUISINES.put(cuisine, registerItem(cuisine.getFoodInfo().getName(),
                    normalFood(cuisine.getFoodInfo(), cuisine.getContainer())));
        }
    }

    private static ItemFoodBase normalFood(FoodInfo info) {
        return new ItemFoodBase(SakuraFabric.defaultItemProperties(), info);
    }

    private static ItemFoodBase normalFood(FoodInfo info, Item container) {
        if (container == null)
            return normalFood(info);
        return new ItemFoodBase(SakuraFabric.defaultItemProperties().craftRemainder(container), info);
    }

    private static <V extends Item> V registerItem(String name, V item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SakuraFabric.MODID, name), item);
    }
}