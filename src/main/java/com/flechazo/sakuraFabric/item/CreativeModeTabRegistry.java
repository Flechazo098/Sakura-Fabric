package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockItemRegistry;
import com.flechazo.sakuraFabric.item.enums.SakuraFoodSet;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SakuraFabric.MODID);
    public static final RegistryObject<CreativeModeTab> GROUP = TABS.register(
            "sakura",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI).get()))
                    .title(Component.translatable("itemGroup.sakura"))
                    .displayItems(
                            (parameters, output) -> {
                                BlockItemRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                ItemRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                FoodRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                                BucketItemRegistry.ITEMS.getEntries().forEach(
                                        (entry) -> output.accept(new ItemStack(entry.get()))
                                );
                            }
                    )
                    .build()
    );
}
