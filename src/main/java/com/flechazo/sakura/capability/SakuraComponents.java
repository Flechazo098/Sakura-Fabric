package com.flechazo.sakura.capability;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.entity.*;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import net.minecraft.resources.ResourceLocation;

/**
 * 注册樱的组件
 */
public class SakuraComponents implements BlockComponentInitializer {
    // 物品处理组件
    public static final ComponentKey<ItemHandlerComponent> ITEM_HANDLER =
            ComponentRegistry.getOrCreate(new ResourceLocation(SakuraFabric.MODID, "item_handler"), ItemHandlerComponent.class);

    // 流体处理组件
    public static final ComponentKey<FluidHandlerComponent> FLUID_HANDLER =
            ComponentRegistry.getOrCreate(new ResourceLocation(SakuraFabric.MODID, "fluid_handler"), FluidHandlerComponent.class);

    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry registry) {
        // 在这里注册方块实体的组件工厂
        registry.registerFor(ChoppingBoardBlockEntity.class, ITEM_HANDLER, ChoppingBoardBlockEntity::createItemHandlerComponent);
        registry.registerFor(DistillerBlockEntity.class, ITEM_HANDLER, DistillerBlockEntity::createItemHandlerComponent);
        registry.registerFor(FermenterBlockEntity.class, ITEM_HANDLER, FermenterBlockEntity::createItemHandlerComponent);
        registry.registerFor(ObonBlockEntity.class, ITEM_HANDLER, ObonBlockEntity::createItemHandlerComponent);
        registry.registerFor(StoneMortarBlockEntity.class, ITEM_HANDLER, StoneMortarBlockEntity::createItemHandlerComponent);

        // 注册流体组件
        registry.registerFor(DistillerBlockEntity.class, FLUID_HANDLER, DistillerBlockEntity::createFluidHandlerComponent);
        registry.registerFor(FermenterBlockEntity.class, FLUID_HANDLER, FermenterBlockEntity::createFluidHandlerComponent);
    }
}