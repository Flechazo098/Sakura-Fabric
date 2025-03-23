package com.flechazo.sakuraFabric.container;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;


public class ContainerRegistry{
    public static MenuType<StoneMortarContainer> STONE_MORTAR;
    public static MenuType<CookingPotContainer> COOKING_POT;
    public static MenuType<FermenterContainer> FERMENTER;
    public static MenuType<DistillerContainer> DISTILLER;

    public static void registryModMenus() {
        STONE_MORTAR = Registry.register(BuiltInRegistries.MENU, "stone_mortar", IForgeMenuType.create(StoneMortarContainer::new));
        COOKING_POT = Registry.register(BuiltInRegistries.MENU, "cooking_pot", IForgeMenuType.create(CookingPotContainer::new));
        FERMENTER = Registry.register(BuiltInRegistries.MENU, "fermenter", IForgeMenuType.create(FermenterContainer::new));
        DISTILLER = Registry.register(BuiltInRegistries.MENU, "distiller", IForgeMenuType.create(DistillerContainer::new));
    }
}