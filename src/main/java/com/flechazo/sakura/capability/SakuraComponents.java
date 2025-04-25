package com.flechazo.sakura.capability;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.entity.*;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public static ItemHandlerComponent getItemHandlerComponent (ItemStackHandler inventory, ObonBlockEntity blockEntity) {
        return new ItemHandlerComponent() {
            @Override
            public void readFromNbt(CompoundTag compoundTag) {
                // 从NBT中读取物品栏数据
                if (compoundTag.contains("Inventory")) {
                    inventory.deserializeNBT(compoundTag.getCompound("Inventory"));
                }
            }

            @Override
            public void writeToNbt(CompoundTag compoundTag) {
                // 将物品栏数据写入NBT
                compoundTag.put("Inventory", inventory.serializeNBT());
            }

            @Override
            public SlottedStackStorage getItemHandler(Direction direction) {
                return inventory;
            }
        };
    }
}