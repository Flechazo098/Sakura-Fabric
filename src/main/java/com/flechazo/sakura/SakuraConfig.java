package com.flechazo.sakura;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = SakuraFabric.MODID)
public class SakuraConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public SeedDropConfig seedDropConfig = new SeedDropConfig();

    public static class SeedDropConfig {
        @ConfigEntry.Gui.Tooltip
//        @Comment("全局种子掉落率 (默认: 0.1) - 降低此值将减少所有种子的掉落概率")
        public double globalDropRate = 0.1;
    }
}
