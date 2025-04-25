package com.flechazo.sakura.client.render.fluid;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * 客户端专用的流体扩展初始化类
 */
@Environment(EnvType.CLIENT)
public class ClientFluidExtensions {

    @Environment(EnvType.CLIENT)
    public static void initialize() {
        ClientFluidRegistry.initialize();
    }
}