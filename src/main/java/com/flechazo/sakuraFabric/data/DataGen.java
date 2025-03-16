package com.flechazo.sakuraFabric.data;

import com.flechazo.sakuraFabric.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = SakuraFabric.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        dataGenerator.addProvider(event.includeClient(),new SakuraBlockStateProvider(packOutput, SakuraFabric.MODID, existingFileHelper));
        dataGenerator.addProvider(event.includeClient(),new SakuraItemModelProvider(packOutput, SakuraFabric.MODID, existingFileHelper));
        SakuraBlockTagsProvider block_tag = new SakuraBlockTagsProvider(packOutput, provider, SakuraFabric.MODID, existingFileHelper);
        dataGenerator.addProvider(event.includeServer(),block_tag);
        dataGenerator.addProvider(event.includeServer(),new SakuraItemTagsProvider(packOutput, provider, block_tag, SakuraFabric.MODID, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(),new SakuraFluidTagsProvider(packOutput, provider, SakuraFabric.MODID, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(),new SakuraBiomeTagProvider(packOutput, provider, SakuraFabric.MODID, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(),new SakuraRecipeProvider(packOutput));
        dataGenerator.addProvider(event.includeServer(),new SakuraLootTableProvider(packOutput));
        dataGenerator.addProvider(event.includeServer(),new SakuraFeatureProvider(packOutput, provider));
//        dataGenerator.addProvider(event.includeServer(),new SakuraLootModifierProvider(packOutput, SakuraMod.MODID));
        dataGenerator.addProvider(event.includeServer(),new SakuraTFCFoodCompatProvider(packOutput, existingFileHelper));
    }
}
