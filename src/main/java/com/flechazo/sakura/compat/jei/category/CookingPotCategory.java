package com.flechazo.sakura.compat.jei.category;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.block.entity.CookingPotBlockEntity;
import com.flechazo.sakura.compat.jei.JEIPlugin;
import com.flechazo.sakura.recipes.CookingPotRecipe;
import com.flechazo.sakura.utils.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class CookingPotCategory implements IRecipeCategory<CookingPotRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(SakuraFabric.MODID, "cooking");
    protected final IDrawable heatIndicator;
    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CookingPotCategory(IGuiHelper helper) {
        title = Component.translatable("sakura.jei.cooking");
        ResourceLocation backgroundImage = new ResourceLocation(SakuraFabric.MODID, "textures/gui/pot.png");
        background = helper.createDrawable(backgroundImage, 16, 16, 144, 54);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.COOKING_POT));
        heatIndicator = helper.createDrawable(backgroundImage, 176, 0, 18, 18);
        arrow = helper.drawableBuilder(backgroundImage, 176, 18, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<CookingPotRecipe> getRecipeType() {
        return JEIPlugin.COOKING_POT_JEI_TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CookingPotRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        int borderSlotSize = 18;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 3; ++column) {
                int inputIndex = row * 3 + column;
                if (inputIndex < recipeIngredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, 23 + column * borderSlotSize, 1 + row * borderSlotSize)
                            .addIngredients(recipeIngredients.get(inputIndex));
                }
            }
        }

        // 修改流体成分的添加方式
        if(recipe.getRequiredFluid() != FluidIngredient.EMPTY) {
            List<FluidStack> fluidStacks = recipe.getRequiredFluid().getMatchingFluidStacks();
            IRecipeSlotBuilder fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                    .setFluidRenderer(CookingPotBlockEntity.TANK_CAPACITY, true, 16, 52);

            // 使用正确的方法添加流体成分
            for (FluidStack fluidStack : fluidStacks) {
                fluidSlot.addFluidStack(fluidStack.getFluid(), fluidStack.getAmount(), fluidStack.getTag());
            }
        }

        Minecraft minecraft = Minecraft.getInstance();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 22).addItemStack(recipe.getResultItem(minecraft.level.registryAccess()));
    }
    @Override
    public void draw(CookingPotRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        arrow.draw(guiGraphics, 82, 18);
        heatIndicator.draw(guiGraphics, 85, 36);
    }
}
