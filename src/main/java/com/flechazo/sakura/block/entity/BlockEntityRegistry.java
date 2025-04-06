package com.flechazo.sakura.block.entity;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {

    public static BlockEntityType<StoneMortarBlockEntity> STONE_MORTAR;
    public static BlockEntityType<CookingPotBlockEntity> COOKING_POT;
    public static BlockEntityType<FermenterBlockEntity> FERMENTER;
    public static BlockEntityType<DistillerBlockEntity> DISTILLER;
    public static BlockEntityType<ObonBlockEntity> OBON;
    public static BlockEntityType<ChoppingBoardBlockEntity> CHOPPING_BOARD;

    public static void initialize() {
        STONE_MORTAR = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "stone_mortar"),
                FabricBlockEntityTypeBuilder.create(StoneMortarBlockEntity::new, BlockRegistry.STONE_MORTAR).build()
        );

        COOKING_POT = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "cooking_pot"),
                FabricBlockEntityTypeBuilder.create(CookingPotBlockEntity::new, BlockRegistry.COOKING_POT).build()
        );

        FERMENTER = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "fermenter"),
                FabricBlockEntityTypeBuilder.create(FermenterBlockEntity::new, BlockRegistry.FERMENTER).build()
        );

        DISTILLER = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "distiller"),
                FabricBlockEntityTypeBuilder.create(DistillerBlockEntity::new, BlockRegistry.DISTILLER).build()
        );

        OBON = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "obon"),
                FabricBlockEntityTypeBuilder.create(ObonBlockEntity::new, BlockRegistry.OBON).build()
        );

        CHOPPING_BOARD = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(SakuraFabric.MODID, "chopping_board"),
                FabricBlockEntityTypeBuilder.create(ChoppingBoardBlockEntity::new, BlockRegistry.CHOPPING_BOARD).build()
        );
    }
}