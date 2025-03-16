package com.flechazo.sakuraFabric;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;

public class SakuraFabric implements ModInitializer {
    public static final String MODID = "sakura";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Item.Properties defaultItemProperties() {
        return new Item.Properties();
    }

    @Override
    public void onInitialize () {
    }
}
