//package com.flechazo.sakura.loot_modifier;
//
//import com.flechazo.sakura.SakuraFabric;
//import com.flechazo.sakura.init.FoodRegistry;
//import com.flechazo.sakura.init.ItemRegistry;
//import com.flechazo.sakura.item.enums.SakuraFoodSet;
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
//import io.github.fabricators_of_create.porting_lib.loot.LootModifier;
//import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
//import net.minecraft.core.Registry;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.storage.loot.LootContext;
//import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
//import org.jetbrains.annotations.NotNull;
//
//import it.unimi.dsi.fastutil.objects.ObjectArrayList;
//
//public class SakuraLootModifiers {
//    public static void register() {
//        Registry.register(
//                PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(),
//                new ResourceLocation(SakuraFabric.MODID, "fishing_modifiter"),
//                FishingLootModifier.CODEC
//        );
//
//        Registry.register(
//                PortingLibLoot.GLOBAL_LOOT_MODIFIER_SERIALIZERS.get(),
//                new ResourceLocation(SakuraFabric.MODID, "grass_drops"),
//                GrassDropsModifier.CODEC
//        );
//    }
//
//    public static class FishingLootModifier extends LootModifier {
//        public static final Codec<FishingLootModifier> CODEC = RecordCodecBuilder.create(inst ->
//                inst.group(
//                        RecordCodecBuilder.point(new LootItemCondition[0])
//                        ).apply(inst, FishingLootModifier::new)
//        );
//
//        public FishingLootModifier(LootItemCondition[] conditionsIn) {
//            super(conditionsIn);
//        }
//
//        @NotNull
//        @Override
//        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
//            if (FoodRegistry.FOODSET.containsKey(SakuraFoodSet.SHRIMP) && context.getRandom().nextFloat() < 0.05f) {
//                generatedLoot.add(new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP)));
//            }
//            return generatedLoot;
//        }
//
//        @Override
//        public Codec<? extends IGlobalLootModifier> codec() {
//            return CODEC;
//        }
//    }
//
//    public static class GrassDropsModifier extends LootModifier {
//        public static final Codec<GrassDropsModifier> CODEC = RecordCodecBuilder.create(inst ->
//                inst.group(
//                        RecordCodecBuilder.point(new LootItemCondition[0])
//                ).apply(inst, GrassDropsModifier::new)
//        );
//
//        public GrassDropsModifier(LootItemCondition[] conditionsIn) {
//            super(conditionsIn);
//        }
//
//        @NotNull
//        @Override
//        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
//            float random = context.getRandom().nextFloat();
//
//            if (random < 0.08f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.CABBAGE_SEEDS));
//            } else if (random < 0.16f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.EGGPLANT_SEEDS));
//            } else if (random < 0.24f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.ONION_SEEDS));
//            } else if (random < 0.32f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.RADISH_SEEDS));
//            } else if (random < 0.40f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.TOMATO_SEEDS));
//            } else if (random < 0.48f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.RICE_SEEDS));
//            } else if (random < 0.56f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.RAPESEEDS));
//            } else if (random < 0.61f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.TARO));
//            } else if (random < 0.66f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.BUCKWHEAT));
//            } else if (random < 0.71f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.SOYBEAN));
//            } else if (random < 0.76f) {
//                generatedLoot.add(new ItemStack(ItemRegistry.RED_BEAN));
//            }
//
//            return generatedLoot;
//        }
//
//        @Override
//        public Codec<? extends IGlobalLootModifier> codec() {
//            return CODEC;
//        }
//    }
//}