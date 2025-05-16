package com.flechazo.sakura.init;

import com.flechazo.sakura.loot_modifier.FishingModifiter;
import com.flechazo.sakura.loot_modifier.SeedsDrop;

public class LootModifiterRegistry {
    public static void register() {

        SeedsDrop.register();

        FishingModifiter.register();

//        SakuraLootModifiers.register();
    }
}