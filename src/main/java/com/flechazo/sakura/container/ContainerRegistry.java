package com.flechazo.sakura.container;

import com.flechazo.sakura.SakuraFabric;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class ContainerRegistry {
    public static MenuType<StoneMortarContainer> STONE_MORTAR;
    public static MenuType<CookingPotContainer> COOKING_POT;
    public static MenuType<FermenterContainer> FERMENTER;
    public static MenuType<DistillerContainer> DISTILLER;

    public static void registryModMenus() {
        STONE_MORTAR = Registry.register(
                BuiltInRegistries.MENU,
                new ResourceLocation(SakuraFabric.MODID, "stone_mortar"),
                new ExtendedScreenHandlerType<>(StoneMortarContainer::new)
        );

        COOKING_POT = Registry.register(
                BuiltInRegistries.MENU,
                new ResourceLocation(SakuraFabric.MODID, "cooking_pot"),
                new ExtendedScreenHandlerType<>(CookingPotContainer::new)
        );

        FERMENTER = Registry.register(
                BuiltInRegistries.MENU,
                new ResourceLocation(SakuraFabric.MODID, "fermenter"),
                new ExtendedScreenHandlerType<>(FermenterContainer::new)
        );

        DISTILLER = Registry.register(
                BuiltInRegistries.MENU,
                new ResourceLocation(SakuraFabric.MODID, "distiller"),
                new ExtendedScreenHandlerType<>(DistillerContainer::new)
        );
    }
}