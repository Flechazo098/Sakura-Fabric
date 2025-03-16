package com.flechazo.sakuraFabric.compat.jei;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.BlockRegistry;
import com.flechazo.sakuraFabric.client.gui.CookingPotScreen;
import com.flechazo.sakuraFabric.client.gui.DistillerScreen;
import com.flechazo.sakuraFabric.client.gui.FermenterScreen;
import com.flechazo.sakuraFabric.client.gui.StoneMortarScreen;
import com.flechazo.sakuraFabric.compat.jei.category.*;
import com.flechazo.sakuraFabric.container.CookingPotContainer;
import com.flechazo.sakuraFabric.container.DistillerContainer;
import com.flechazo.sakuraFabric.container.FermenterContainer;
import com.flechazo.sakuraFabric.container.StoneMortarContainer;
import com.flechazo.sakuraFabric.recipes.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final ResourceLocation PLUGIN_ID = new ResourceLocation(SakuraFabric.MODID, "jei_plugin");

    private static final Minecraft MC = Minecraft.getInstance();

    private static <C extends Container, T extends Recipe<C>> List<T> findRecipesByType(RecipeType<T> type) {
        return MC.level.getRecipeManager().getAllRecipesFor(type);
    }

    public static final mezz.jei.api.recipe.RecipeType<CookingPotRecipe> COOKING_POT_JEI_TYPE =
            mezz.jei.api.recipe.RecipeType.create(SakuraFabric.MODID, "cooking", CookingPotRecipe.class);

    public static final mezz.jei.api.recipe.RecipeType<StoneMortarRecipe> STONE_MORTAR_JEI_TYPE =
            mezz.jei.api.recipe.RecipeType.create(SakuraFabric.MODID, "stone_mortar", StoneMortarRecipe.class);

    public static final mezz.jei.api.recipe.RecipeType<FermenterRecipe> FERMENTER_JEI_TYPE =
            mezz.jei.api.recipe.RecipeType.create(SakuraFabric.MODID, "fermenting", FermenterRecipe.class);

    public static final mezz.jei.api.recipe.RecipeType<DistillerRecipe> DISTILLER_JEI_TYPE =
            mezz.jei.api.recipe.RecipeType.create(SakuraFabric.MODID, "distillation", DistillerRecipe.class);

    public static final mezz.jei.api.recipe.RecipeType<ChoppingRecipe> CHOPPING_JEI_TYPE =
            mezz.jei.api.recipe.RecipeType.create(SakuraFabric.MODID, "chopping", ChoppingRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CookingPotCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new StoneMortarCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new FermenterCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new DistillerCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ChoppingCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(COOKING_POT_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.COOKING_RECIPE_TYPE.get()));
        registration.addRecipes(STONE_MORTAR_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.STONE_MORTAR_RECIPE_TYPE.get()));
        registration.addRecipes(FERMENTER_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.FERMENTER_RECIPE_TYPE.get()));
        registration.addRecipes(DISTILLER_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.DISTILLER_RECIPE_TYPE.get()));
        registration.addRecipes(CHOPPING_JEI_TYPE, findRecipesByType(RecipeTypeRegistry.CHOPPING_RECIPE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.COOKING_POT.get()), COOKING_POT_JEI_TYPE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.STONE_MORTAR.get()), STONE_MORTAR_JEI_TYPE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.FERMENTER.get()), FERMENTER_JEI_TYPE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.DISTILLER.get()), DISTILLER_JEI_TYPE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistry.CHOPPING_BOARD.get()), CHOPPING_JEI_TYPE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CookingPotScreen.class, 94, 16, 32, 54, COOKING_POT_JEI_TYPE);
        registration.addRecipeClickArea(StoneMortarScreen.class, 79, 32, 18, 24, STONE_MORTAR_JEI_TYPE);
        registration.addRecipeClickArea(FermenterScreen.class, 75, 34, 18, 24, FERMENTER_JEI_TYPE);
        registration.addRecipeClickArea(DistillerScreen.class, 75, 34, 18, 24, DISTILLER_JEI_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CookingPotContainer.class,null, COOKING_POT_JEI_TYPE, 0, 9, 10, 36);
        registration.addRecipeTransferHandler(StoneMortarContainer.class,null, STONE_MORTAR_JEI_TYPE, 0, 4, 6, 36);
        registration.addRecipeTransferHandler(FermenterContainer.class, null,FERMENTER_JEI_TYPE, 0, 3, 6, 36);
        registration.addRecipeTransferHandler(DistillerContainer.class, null,DISTILLER_JEI_TYPE, 0, 3, 6, 36);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

}