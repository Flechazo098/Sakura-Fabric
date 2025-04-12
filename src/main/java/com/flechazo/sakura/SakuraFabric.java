package com.flechazo.sakura;

import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.init.ContainerRegistry;
import com.flechazo.sakura.core.events.BurnTimeEvent;
import com.flechazo.sakura.core.events.TreeEvent;
import com.flechazo.sakura.fluid.BucketItemRegistry;
import com.flechazo.sakura.fluid.FluidBlockRegistry;
import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.fluid.FluidTypeRegistry;
import com.flechazo.sakura.init.CreativeModeTabRegistry;
import com.flechazo.sakura.item.food.FoodRegistry;
import com.flechazo.sakura.init.ItemRegistry;
import com.flechazo.sakura.loot_modifier.LootModifiterRegistry;
import com.flechazo.sakura.recipes.RecipeSerializerRegistry;
import com.flechazo.sakura.recipes.RecipeTypeRegistry;
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
        // 1. 首先注册配方类型和序列化器，因为这些是基础系统
        RecipeTypeRegistry.initialize();
        RecipeSerializerRegistry.initialize();

        // 2. 注册方块，因为很多其他内容都依赖于方块
        BlockRegistry.registrySakuraBlocks();

        // 3. 注册方块实体，依赖于方块注册
        BlockEntityRegistry.initialize();

        // 4. 注册流体相关内容，按照依赖顺序
        FluidTypeRegistry.initialize();
        FluidRegistry.initialize();
        FluidBlockRegistry.initialize();
        BucketItemRegistry.initialize();

        // 5. 注册物品相关内容
        ItemRegistry.registerSakuraItem();
        BlockItemRegistry.registerSakuraBlockItem();  // 方块物品依赖于方块和物品系统

        // 6. 注册食物系统
        FoodRegistry.initialize();

        // 7. 注册容器和UI相关
        ContainerRegistry.registryModMenus();
        CreativeModeTabRegistry.init();

        // 8. 注册事件和修改器
        BurnTimeEvent.registerBurnTime();
        TreeEvent.register();
        LootModifiterRegistry.register();

        // 9. 最后注册生物群系修改，因为它可能依赖于之前注册的所有内容
        registerBiomeModifications();
    }

    public static boolean isClientSide() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
