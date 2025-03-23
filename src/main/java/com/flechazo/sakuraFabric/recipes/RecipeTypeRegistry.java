package com.flechazo.sakuraFabric.recipes;

import com.flechazo.sakuraFabric.SakuraFabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeRegistry {

    public static final RecipeType<CookingPotRecipe> COOKING_RECIPE_TYPE = registerRecipeType("cooking");
    public static final RecipeType<StoneMortarRecipe> STONE_MORTAR_RECIPE_TYPE = registerRecipeType("stone_mortar");
    public static final RecipeType<FermenterRecipe> FERMENTER_RECIPE_TYPE = registerRecipeType("fermenting");
    public static final RecipeType<DistillerRecipe> DISTILLER_RECIPE_TYPE = registerRecipeType("distillation");
    public static final RecipeType<ChoppingRecipe> CHOPPING_RECIPE_TYPE = registerRecipeType("chopping");

    public static final AbstractRecipeSerializer<StoneMortarRecipe> STONE_MORTAR_RECIPE_SERIALIZER = registerRecipeSerializer(
            "stone_mortar", new AbstractRecipeSerializer<>(StoneMortarRecipe.class));
    public static final AbstractRecipeSerializer<CookingPotRecipe> COOKING_RECIPE_SERIALIZER = registerRecipeSerializer(
            "cooking", new AbstractRecipeSerializer<>(CookingPotRecipe.class));
    public static final AbstractRecipeSerializer<FermenterRecipe> FERMENTER_RECIPE_SERIALIZER = registerRecipeSerializer(
            "fermenting", new AbstractRecipeSerializer<>(FermenterRecipe.class));
    public static final AbstractRecipeSerializer<DistillerRecipe> DISTILLER_RECIPE_SERIALIZER = registerRecipeSerializer(
            "distillation", new AbstractRecipeSerializer<>(DistillerRecipe.class));
    public static final AbstractRecipeSerializer<ChoppingRecipe> CHOPPING_RECIPE_SERIALIZER = registerRecipeSerializer(
            "chopping", new AbstractRecipeSerializer<>(ChoppingRecipe.class));

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
        RecipeType<T> type = new RecipeType<T>() {
            public String toString() {
                return new ResourceLocation(SakuraFabric.MODID, name).toString();
            }
        };
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(SakuraFabric.MODID, name), type);
    }

    private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(String name, S serializer) {
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(SakuraFabric.MODID, name), serializer);
    }
}