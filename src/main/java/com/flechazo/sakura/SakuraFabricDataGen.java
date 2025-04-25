package com.flechazo.sakura;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SakuraFabricDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        System.out.println("SakuraFabricDataGen 初始化数据生成...");
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

//        // 注册战利品表数据生成器
//        pack.addProvider(SakuraLootTableProvider::new);

        System.out.println("已注册 SakuraLootTableProvider 数据生成器");
    }
}