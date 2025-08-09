package com.flechazo.sakura.client.render.fluid;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


@Environment(EnvType.CLIENT)
public class ClientFluidExtensions {

    public static void initialize() {
        FluidRenderingRegistry.initialize();
    }
}