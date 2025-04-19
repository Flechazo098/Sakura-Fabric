package com.flechazo.sakura.client.render.fluid;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * 客户端专用的流体扩展初始化类
 */
@Environment(EnvType.CLIENT)
public class ClientFluidExtensions {

    /**
     * 初始化客户端流体扩展
     */
    @Environment(EnvType.CLIENT)
    public static void initialize() {
        // 初始化客户端流体属性
        ClientFluidRegistry.initialize();
    }
}