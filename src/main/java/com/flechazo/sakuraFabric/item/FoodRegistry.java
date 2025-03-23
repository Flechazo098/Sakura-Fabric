package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.item.enums.SakuraCuisineSet;
import com.flechazo.sakuraFabric.item.enums.SakuraFoodSet;
import com.flechazo.sakuraFabric.item.info.FoodInfo;
import com.flechazo.sakuraFabric.utils.ItemRegistryUtil;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.function.Supplier;

public class FoodRegistry {

    public static final Map<SakuraFoodSet, ItemFoodBase> FOODSET = ItemRegistryUtil.mapOfKeys(
            SakuraFoodSet.class, info -> registerItem(info.getFoodInfo().getName(), normalFood(info.getFoodInfo())));

    public static final Map<SakuraCuisineSet, ItemFoodBase> CUISINES = ItemRegistryUtil.mapOfKeys(
            SakuraCuisineSet.class,
            info -> registerItem(info.getFoodInfo().getName(), normalFood(info.getFoodInfo(), info.getContainer())));

    private static ItemFoodBase normalFood(FoodInfo info) {
        return new ItemFoodBase(SakuraFabric.defaultItemProperties(), info);
    }

    private static ItemFoodBase normalFood(FoodInfo info, Item container) {
        if(container == null)
            return normalFood(info);
        return new ItemFoodBase(SakuraFabric.defaultItemProperties().craftRemainder(container), info);
    }

    private static <V extends Item> V registerItem(String name, V item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(SakuraFabric.MODID, name), item);
    }

}