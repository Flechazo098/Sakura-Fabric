package com.flechazo.sakuraFabric.block.entity;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {

    public static final BlockEntityType<StoneMortarBlockEntity> STONE_MORTAR = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "stone_mortar"),
            FabricBlockEntityTypeBuilder.create(StoneMortarBlockEntity::new, BlockRegistry.STONE_MORTAR).build()
    );

    public static final BlockEntityType<CookingPotBlockEntity> COOKING_POT = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "cooking_pot"),
            FabricBlockEntityTypeBuilder.create(CookingPotBlockEntity::new, BlockRegistry.COOKING_POT).build()
    );

    public static final BlockEntityType<FermenterBlockEntity> FERMENTER = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "fermenter"),
            FabricBlockEntityTypeBuilder.create(FermenterBlockEntity::new, BlockRegistry.FERMENTER).build()
    );

    public static final BlockEntityType<DistillerBlockEntity> DISTILLER = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "distiller"),
            FabricBlockEntityTypeBuilder.create(DistillerBlockEntity::new, BlockRegistry.DISTILLER).build()
    );

    public static final BlockEntityType<ObonBlockEntity> OBON = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "obon"),
            FabricBlockEntityTypeBuilder.create(ObonBlockEntity::new, BlockRegistry.OBON).build()
    );

    public static final BlockEntityType<ChoppingBoardBlockEntity> CHOPPING_BOARD = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation(SakuraFabric.MODID, "chopping_board"),
            FabricBlockEntityTypeBuilder.create(ChoppingBoardBlockEntity::new, BlockRegistry.CHOPPING_BOARD).build()
    );
}