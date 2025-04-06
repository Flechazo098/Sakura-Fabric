package com.flechazo.sakura.client.gui;

import com.flechazo.sakura.container.ContainerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class ScreensRegistry {
    public static void register() {
        MenuScreens.register(ContainerRegistry.STONE_MORTAR, StoneMortarScreen::new);
        MenuScreens.register(ContainerRegistry.COOKING_POT, CookingPotScreen::new);
        MenuScreens.register(ContainerRegistry.FERMENTER, FermenterScreen::new);
        MenuScreens.register(ContainerRegistry.DISTILLER, DistillerScreen::new);
    }
}