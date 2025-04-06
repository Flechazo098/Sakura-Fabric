//package com.flechazo.sakura;
//
//import com.flechazo.sakura.data.*;
//import com.flechazo.sakura.data.client.SakuraBlockStateProvider;
//import com.flechazo.sakura.data.client.SakuraItemModelProvider;
//import com.flechazo.sakura.data.compat.SakuraTFCFoodCompatProvider;
//import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
//import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
//import net.minecraft.core.RegistrySetBuilder;
//import net.minecraft.core.registries.Registries;
//
//public class SakuraFabricDataGen implements DataGeneratorEntrypoint {
//
//    public static final String MOD_ID = SakuraFabric.MODID;
//
//    @Override
//    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
//        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
//
//
//        // 添加数据生成器
//        pack.addProvider(SakuraBlockStateProvider::new);
//        pack.addProvider(SakuraItemModelProvider::new);
//
//        // 方块标签提供者
//        SakuraBlockTagsProvider blockTagProvider = pack.addProvider(SakuraBlockTagsProvider::new);
//
//        // 物品标签提供者（依赖方块标签）
//        pack.addProvider((output, registriesFuture) ->
//                new SakuraItemTagsProvider(output, registriesFuture, blockTagProvider.contentsGetter())
//        );
//
//        pack.addProvider(SakuraFluidTagsProvider::new
//        );
//
//        pack.addProvider(SakuraBiomeTagProvider::new
//        );
//
//        pack.addProvider(SakuraRecipeProvider::new);
////        pack.addProvider(SakuraLootTableProvider::new);
//        pack.addProvider(SakuraFeatureProvider::new
//        );
//
//        // 如果需要TFC食物兼容
//        pack.addProvider(SakuraTFCFoodCompatProvider::new);
//    }
//
//    @Override
//    public void buildRegistry(RegistrySetBuilder registryBuilder) {
//        // 添加世界生成相关的注册
//        registryBuilder.add(Registries.CONFIGURED_FEATURE, SakuraConfiguredFeature::bootstrap);
//        registryBuilder.add(Registries.PLACED_FEATURE, SakuraPlacedFeatures::bootstrap);
//        // 可以根据需要添加更多注册
//    }
//}