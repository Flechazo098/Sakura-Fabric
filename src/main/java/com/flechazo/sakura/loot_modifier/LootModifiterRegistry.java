package com.flechazo.sakura.loot_modifier;

/**
 * 战利品表修改器注册类
 */
public class LootModifiterRegistry {
    /**
     * 注册所有战利品表修改器
     */
    public static void register() {
        // 注册草方块掉落种子的战利品表修改器
        SeedsDrop.register();

        // 注册钓鱼战利品表修改器
        FishingModifiter.register();
    }
}