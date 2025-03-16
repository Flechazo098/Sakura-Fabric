package com.flechazo.sakuraFabric.block.entity;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, SakuraFabric.MODID);

    public static final RegistryObject<BlockEntityType<StoneMortarBlockEntity>> STONE_MORTAR = BLOCK_ENTITIES
            .register("stone_mortar", () -> BlockEntityType.Builder
                    .of(StoneMortarBlockEntity::new, BlockRegistry.STONE_MORTAR.get()).build(null));

    public static final RegistryObject<BlockEntityType<CookingPotBlockEntity>> COOKING_POT = BLOCK_ENTITIES.register(
            "cooking_pot",
            () -> BlockEntityType.Builder.of(CookingPotBlockEntity::new, BlockRegistry.COOKING_POT.get()).build(null));

    public static final RegistryObject<BlockEntityType<FermenterBlockEntity>> FERMENTER = BLOCK_ENTITIES.register(
            "fermenter",
            () -> BlockEntityType.Builder.of(FermenterBlockEntity::new, BlockRegistry.FERMENTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DistillerBlockEntity>> DISTILLER = BLOCK_ENTITIES.register(
            "distiller",
            () -> BlockEntityType.Builder.of(DistillerBlockEntity::new, BlockRegistry.DISTILLER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ObonBlockEntity>> OBON = BLOCK_ENTITIES.register(
            "obon",
            () -> BlockEntityType.Builder.of(ObonBlockEntity::new, BlockRegistry.OBON.get()).build(null));

    public static final RegistryObject<BlockEntityType<ChoppingBoardBlockEntity>> CHOPPING_BOARD = BLOCK_ENTITIES.register(
            "chopping_board",
            () -> BlockEntityType.Builder.of(ChoppingBoardBlockEntity::new, BlockRegistry.CHOPPING_BOARD.get()).build(null));
}
