package com.flechazo.sakura.core.data.compat;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.item.food.FoodRegistry;
import com.flechazo.sakura.init.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;

import java.util.Arrays;

public class SakuraTFCFoodCompatProvider extends TFCFoodDefinitionProvider {

    public SakuraTFCFoodCompatProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, SakuraFabric.MODID);
    }

    @Override
    public void addDatas() {
        // 使用反射获取所有食物物品
        if (FoodRegistry.FOODSET != null) {
            FoodRegistry.FOODSET.values().forEach(this::addData);
        }

        if (FoodRegistry.CUISINES != null) {
            FoodRegistry.CUISINES.values().forEach(this::addData);
        }

        Arrays.stream(ItemRegistry.class.getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(net.minecraft.world.item.Item.class))
                .forEach(field -> {
                    try {
                        addData((net.minecraft.world.item.Item) field.get(null));
                    } catch (Exception e) {
                        SakuraFabric.LOGGER.error("Error adding item to TFC food compat", e);
                    }
                });
    }

    @Override
    public String getName() {
        return "Sakura TFC FoodDefinition Provider";
    }
}