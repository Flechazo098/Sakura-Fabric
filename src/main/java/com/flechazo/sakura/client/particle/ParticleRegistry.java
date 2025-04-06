package com.flechazo.sakura.client.particle;

import com.flechazo.sakura.SakuraFabric;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ParticleRegistry {
    // 直接定义粒子类型
    public static final SimpleParticleType SAKURA_LEAF = registerParticle("sakura");
    public static final SimpleParticleType RED_MAPLE_LEAF = registerParticle("red_maple");
    public static final SimpleParticleType YELLOW_MAPLE_LEAF = registerParticle("yellow_maple");
    public static final SimpleParticleType GREEN_MAPLE_LEAF = registerParticle("green_maple");
    public static final SimpleParticleType ORANGE_MAPLE_LEAF = registerParticle("orange_maple");

    // 注册粒子类型的辅助方法
    private static SimpleParticleType registerParticle(String name) {
        return Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                new ResourceLocation(SakuraFabric.MODID, name),
                FabricParticleTypes.simple(false)
        );
    }

    public static void register() {
        // 粒子类型已经在静态字段初始化时注册，这里不需要额外操作
    }
}