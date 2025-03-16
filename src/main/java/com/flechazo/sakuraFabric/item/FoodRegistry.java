package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.item.enums.SakuraCuisineSet;
import com.flechazo.sakuraFabric.item.enums.SakuraFoodSet;
import com.flechazo.sakuraFabric.item.info.FoodInfo;
import com.flechazo.sakuraFabric.utils.ItemRegistryUtil;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.function.Supplier;

public class FoodRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SakuraFabric.MODID);

    public static final Map<SakuraFoodSet, RegistryObject<ItemFoodBase>> FOODSET = ItemRegistryUtil.mapOfKeys(
            SakuraFoodSet.class, info -> register(info.getFoodInfo().getName(), () -> normalFood(info.getFoodInfo())));

    public static final Map<SakuraCuisineSet, RegistryObject<ItemFoodBase>> CUISINES = ItemRegistryUtil.mapOfKeys(
            SakuraCuisineSet.class,
            info -> register(info.getFoodInfo().getName(), () -> normalFood(info.getFoodInfo(), info.getContainer().get())));

    private static ItemFoodBase normalFood(FoodInfo info) {
        return new ItemFoodBase(SakuraFabric.defaultItemProperties(), info);
    }

    private static ItemFoodBase normalFood(FoodInfo info, Item container) {
        if(container == null)
            return normalFood(info);
        return new ItemFoodBase(SakuraFabric.defaultItemProperties().craftRemainder(container), info);
    }

    private static <V extends Item> RegistryObject<V> register(String name, Supplier<V> item) {
        return ITEMS.register(name, item);
    }
}
