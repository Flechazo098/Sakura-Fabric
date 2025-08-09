package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.recipes.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerRegistry {

    public static AbstractRecipeSerializer<StoneMortarRecipe> STONE_MORTAR_RECIPE_SERIALIZER;
    public static AbstractRecipeSerializer<CookingPotRecipe> COOKING_RECIPE_SERIALIZER;
    public static AbstractRecipeSerializer<FermenterRecipe> FERMENTER_RECIPE_SERIALIZER;
    public static AbstractRecipeSerializer<DistillerRecipe> DISTILLER_RECIPE_SERIALIZER;
    public static AbstractRecipeSerializer<ChoppingRecipe> CHOPPING_RECIPE_SERIALIZER;

    public static void initialize() {

        STONE_MORTAR_RECIPE_SERIALIZER = registerRecipeSerializer(
                "stone_mortar", new AbstractRecipeSerializer<>(StoneMortarRecipe.class));
        COOKING_RECIPE_SERIALIZER = registerRecipeSerializer(
                "cooking", new AbstractRecipeSerializer<>(CookingPotRecipe.class));
        FERMENTER_RECIPE_SERIALIZER = registerRecipeSerializer(
                "fermenting", new AbstractRecipeSerializer<>(FermenterRecipe.class));
        DISTILLER_RECIPE_SERIALIZER = registerRecipeSerializer(
                "distillation", new AbstractRecipeSerializer<>(DistillerRecipe.class));
        CHOPPING_RECIPE_SERIALIZER = registerRecipeSerializer(
                "chopping", new AbstractRecipeSerializer<>(ChoppingRecipe.class));
    }

    private static <T extends RecipeSerializer<?>> T registerRecipeSerializer(String path, T serializer) {
        ResourceLocation id = new ResourceLocation(SakuraFabric.MODID, path);
        return Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, id, serializer);
    }
}