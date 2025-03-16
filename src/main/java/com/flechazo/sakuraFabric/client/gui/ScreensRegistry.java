package com.flechazo.sakuraFabric.client.gui;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.container.ContainerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = SakuraFabric.MODID, value = Dist.CLIENT)
public class ScreensRegistry {
    @SubscribeEvent
    public static void screenRegistry(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegistry.STONE_MORTAR.get(), StoneMortarScreen::new);
            MenuScreens.register(ContainerRegistry.COOKING_POT.get(), CookingPotScreen::new);
            MenuScreens.register(ContainerRegistry.FERMENTER.get(), FermenterScreen::new);
            MenuScreens.register(ContainerRegistry.DISTILLER.get(), DistillerScreen::new);
        });
    }
}
