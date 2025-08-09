package com.flechazo.sakura.capability;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.entity.*;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.resources.ResourceLocation;

public class SakuraComponents implements BlockComponentInitializer {
    public static final ComponentKey<ItemHandlerComponent> ITEM_HANDLER =
            ComponentRegistry.getOrCreate(new ResourceLocation(SakuraFabric.MODID, "item_handler"), ItemHandlerComponent.class);

    public static final ComponentKey<FluidHandlerComponent> FLUID_HANDLER =
            ComponentRegistry.getOrCreate(new ResourceLocation(SakuraFabric.MODID, "fluid_handler"), FluidHandlerComponent.class);

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
        registry.registerFor(ChoppingBoardBlockEntity.class, ITEM_HANDLER, ChoppingBoardBlockEntity::createItemHandlerComponent);
        registry.registerFor(DistillerBlockEntity.class, ITEM_HANDLER, DistillerBlockEntity::createItemHandlerComponent);
        registry.registerFor(FermenterBlockEntity.class, ITEM_HANDLER, FermenterBlockEntity::createItemHandlerComponent);
        registry.registerFor(ObonBlockEntity.class, ITEM_HANDLER, ObonBlockEntity::createItemHandlerComponent);
        registry.registerFor(StoneMortarBlockEntity.class, ITEM_HANDLER, StoneMortarBlockEntity::createItemHandlerComponent);

        registry.registerFor(DistillerBlockEntity.class, FLUID_HANDLER, DistillerBlockEntity::createFluidHandlerComponent);
        registry.registerFor(FermenterBlockEntity.class, FLUID_HANDLER, FermenterBlockEntity::createFluidHandlerComponent);
    }
}