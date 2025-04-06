package com.flechazo.sakura.recipes;

import com.flechazo.sakura.SakuraFabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeRegistry {

    public static RecipeType<CookingPotRecipe> COOKING_RECIPE_TYPE;
    public static RecipeType<StoneMortarRecipe> STONE_MORTAR_RECIPE_TYPE;
    public static RecipeType<FermenterRecipe> FERMENTER_RECIPE_TYPE;
    public static RecipeType<DistillerRecipe> DISTILLER_RECIPE_TYPE;
    public static RecipeType<ChoppingRecipe> CHOPPING_RECIPE_TYPE;

    public static void initialize() {
        COOKING_RECIPE_TYPE = registerRecipeType("cooking");
        STONE_MORTAR_RECIPE_TYPE = registerRecipeType("stone_mortar");
        FERMENTER_RECIPE_TYPE = registerRecipeType("fermenting");
        DISTILLER_RECIPE_TYPE = registerRecipeType("distillation");
        CHOPPING_RECIPE_TYPE = registerRecipeType("chopping");
    }

    private static <T extends Recipe<?>> RecipeType<T> registerRecipeType(String name) {
        RecipeType<T> type = new RecipeType<>() {
            public String toString () {
                return new ResourceLocation(SakuraFabric.MODID, name).toString();
            }
        };
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(SakuraFabric.MODID, name), type);
    }
}