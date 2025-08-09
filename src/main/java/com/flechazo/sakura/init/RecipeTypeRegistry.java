package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.recipes.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
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
        ResourceLocation id = new ResourceLocation(SakuraFabric.MODID, name);
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, id, new SakuraRecipeType<>(id));
    }

    private record SakuraRecipeType<T extends Recipe<?>>(ResourceLocation id) implements RecipeType<T> {

        @Override
        public String toString() {
            return id.toString();
        }
    }
}