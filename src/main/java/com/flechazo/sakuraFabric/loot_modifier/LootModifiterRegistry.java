package com.flechazo.sakuraFabric.loot_modifier;

import com.mojang.serialization.Codec;

public class LootModifiterRegistry {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister
            .create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, SakuraMod.MODID);
//    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> SEEDSDROP = GLM.register("grass_drops",
//            ()->SeedsDrop.SeedDropModifier.DIRECT_CODEC);
//    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> FISHING = GLM.register("fishing_modifiter",
//            ()->FishingModifiter.DIRECT_CODEC);
}
