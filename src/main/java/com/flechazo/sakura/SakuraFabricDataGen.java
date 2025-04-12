package com.flechazo.sakura;

import com.flechazo.sakura.core.data.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SakuraFabricDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator (FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FluidModelProvider::new);
        pack.addProvider((FabricDataGenerator.Pack.Factory<SakuraLootTableProvider>) SakuraLootTableProvider::new);
    }
}