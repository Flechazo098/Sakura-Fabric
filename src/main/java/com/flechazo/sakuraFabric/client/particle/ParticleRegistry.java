package com.flechazo.sakuraFabric.client.particle;

import com.flechazo.sakuraFabric.SakuraFabric;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister
            .create(ForgeRegistries.PARTICLE_TYPES, SakuraFabric.MODID);
    public static final RegistryObject<SimpleParticleType> SAKURA_LEAF = PARTICLE_TYPES.register("sakura",
            () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> RED_MAPLE_LEAF = PARTICLE_TYPES.register("red_maple",
            () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> YELLOW_MAPLE_LEAF = PARTICLE_TYPES.register("yellow_maple",
            () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GREEN_MAPLE_LEAF = PARTICLE_TYPES.register("green_maple",
            () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ORANGE_MAPLE_LEAF = PARTICLE_TYPES.register("orange_maple",
            () -> new SimpleParticleType(false));
}
