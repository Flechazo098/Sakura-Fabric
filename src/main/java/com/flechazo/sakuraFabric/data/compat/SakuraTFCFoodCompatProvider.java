package com.flechazo.sakuraFabric.data.compat;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.item.FoodRegistry;
import com.flechazo.sakuraFabric.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;

public class SakuraTFCFoodCompatProvider extends TFCFoodDefinitionProvider {

    public SakuraTFCFoodCompatProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, existingFileHelper, SakuraFabric.MODID);
    }

    @Override
    public void addDatas() {
        FoodRegistry.ITEMS.getEntries().forEach(item -> {
            this.addData(item.get());
        });
        ItemRegistry.ITEMS.getEntries().forEach(item -> {
            this.addData(item.get());
        });
    }

    @Override
    public String getName() {
        return "Sakura TFC FoodDefinition Provider";
    }
}
