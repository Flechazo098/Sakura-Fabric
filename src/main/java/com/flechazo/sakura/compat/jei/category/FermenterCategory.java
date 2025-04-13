package com.flechazo.sakura.compat.jei.category;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.BlockRegistry;
import com.flechazo.sakura.block.entity.FermenterBlockEntity;
import com.flechazo.sakura.compat.jei.JEIPlugin;
import com.flechazo.sakura.recipes.FermenterRecipe;
import com.flechazo.sakura.utils.FluidIngredient;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class FermenterCategory implements IRecipeCategory<FermenterRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(SakuraFabric.MODID, "fermenting");
    protected final IDrawableAnimated arrow;
    protected final IDrawableAnimated bubbles;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public FermenterCategory(IGuiHelper helper) {
        title = Component.translatable("sakura.jei.fermenting");
        ResourceLocation backgroundImage = new ResourceLocation(SakuraFabric.MODID, "textures/gui/barrel.png");
        background = helper.createDrawable(backgroundImage, 32, 10, 110, 66);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.FERMENTER));
        arrow = helper.drawableBuilder(backgroundImage, 176, 0, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
        bubbles = helper.drawableBuilder(backgroundImage, 176, 35, 18, 18).buildAnimated(18, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<FermenterRecipe> getRecipeType() {
        return JEIPlugin.FERMENTER_JEI_TYPE;
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
    public void setRecipe(IRecipeLayoutBuilder builder, FermenterRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        int borderSlotSize = 18;
        for (int row = 0; row < 3; ++row) {
            int inputIndex = row;
            if (inputIndex < recipeIngredients.size()) {
                builder.addSlot(RecipeIngredientRole.INPUT, 23, 7 + row * borderSlotSize)
                        .addIngredients(recipeIngredients.get(inputIndex));
            }
        }

        // 修改输入流体的添加方式，添加自定义工具提示回调
        if(recipe.getRequiredFluid() != FluidIngredient.EMPTY) {
            var fluidStacks = recipe.getRequiredFluid().getMatchingFluidStacks();
            var fluidSlot = builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                    .setFluidRenderer(FermenterBlockEntity.TANK_CAPACITY, false, 16, 64)
                    .addTooltipCallback((recipeSlotView, tooltip) -> {
                        // 替换或添加正确的流体量信息
                        for (int i = 0; i < tooltip.size(); i++) {
                            Component component = tooltip.get(i);
                            String text = component.getString();
                            if (text.contains("mB")) {
                                tooltip.set(i, Component.literal(recipe.getRequiredFluid().getRequiredAmount() + " mB / " + FermenterBlockEntity.TANK_CAPACITY + " mB"));
                                break;
                            }
                        }
                    });

            // 逐个添加流体
            for (var fluidStack : fluidStacks) {
                fluidSlot.addFluidStack(fluidStack.getFluid(), fluidStack.getAmount(), fluidStack.getTag());
            }
        }

        for (int row = 0; row < 3; ++row) {
            int inputIndex = row;
            if (inputIndex < recipe.getResultItemList().size()) {
                builder.addSlot(RecipeIngredientRole.OUTPUT, 71, 7 + row * borderSlotSize)
                        .addItemStack(recipe.getResultItemList().get(inputIndex));
            }
        }

        // 修改输出流体的添加方式，添加自定义工具提示回调
        if(!recipe.getResultFluid().isEmpty()) {
            var resultFluid = recipe.getResultFluid();
            builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 1)
                    .setFluidRenderer(FermenterBlockEntity.TANK_CAPACITY, false, 16, 64)
                    .addTooltipCallback((recipeSlotView, tooltip) -> {
                        // 替换或添加正确的流体量信息
                        for (int i = 0; i < tooltip.size(); i++) {
                            Component component = tooltip.get(i);
                            String text = component.getString();
                            if (text.contains("mB")) {
                                tooltip.set(i, Component.literal(resultFluid.getAmount() + " mB / " + FermenterBlockEntity.TANK_CAPACITY + " mB"));
                                break;
                            }
                        }
                    })
                    .addFluidStack(resultFluid.getFluid(), resultFluid.getAmount(), resultFluid.getTag());
        }
    }
    @Override
    public void draw(FermenterRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        bubbles.draw(guiGraphics, 46, 16);
        arrow.draw(guiGraphics, 44, 34);
    }
}
