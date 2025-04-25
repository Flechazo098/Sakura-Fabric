package com.flechazo.sakura;

import com.flechazo.sakura.init.BlockItemRegistry;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.init.ContainerRegistry;
import com.flechazo.sakura.core.events.BurnTimeEvent;
import com.flechazo.sakura.core.events.TreeEvent;
import com.flechazo.sakura.init.fluid.BucketItemRegistry;
import com.flechazo.sakura.init.fluid.FluidBlockRegistry;
import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.flechazo.sakura.init.CreativeModeTabRegistry;
import com.flechazo.sakura.init.ComposterRegistry;
import com.flechazo.sakura.init.FoodRegistry;
import com.flechazo.sakura.init.ItemRegistry;
import com.flechazo.sakura.init.LootModifiterRegistry;
import com.flechazo.sakura.init.RecipeSerializerRegistry;
import com.flechazo.sakura.init.RecipeTypeRegistry;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.slf4j.Logger;

public class SakuraFabric implements ModInitializer {
    public static final String MODID = "sakura";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static FabricItemSettings defaultItemProperties() {
        return new FabricItemSettings();
    }

    public static void registerBiomeModifications() {
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE,
                        new ResourceLocation(SakuraFabric.MODID, "patch_bambooshoot"))
        );
    }
    @Override
    public void onInitialize() {

        RecipeTypeRegistry.initialize();
        RecipeSerializerRegistry.initialize();

        BlockRegistry.registrySakuraBlocks();


        BlockEntityRegistry.initialize();

        FluidTypeRegistry.initialize();
        FluidRegistry.initialize();
        FluidBlockRegistry.initialize();
        BucketItemRegistry.initialize();

        ItemRegistry.registerSakuraItem();
        BlockItemRegistry.registerSakuraBlockItem();
        ComposterRegistry.registerCompost();

        FoodRegistry.initialize();

        ContainerRegistry.registryModMenus();
        CreativeModeTabRegistry.init();

        BurnTimeEvent.registerBurnTime();
        TreeEvent.register();
        LootModifiterRegistry.register();

        registerBiomeModifications();
    }

    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
