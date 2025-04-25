package com.flechazo.sakura.recipes;

import com.flechazo.sakura.init.RecipeSerializerRegistry;
import com.flechazo.sakura.init.RecipeTypeRegistry;
import com.flechazo.sakura.utils.FluidIngredient;
import com.flechazo.sakura.utils.RecipeMatcher;
import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class CookingPotRecipe extends AbstractRecipe {
    @Expose
    @SerializedName("ingredients")
    public NonNullList<Ingredient> inputItems;
    @Expose
    @SerializedName("fluid")
    public FluidIngredient fluidInput;
    @Expose
    @SerializedName("result")
    public ItemStack output;

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public FluidIngredient getRequiredFluid() {
        return fluidInput;
    }

    public boolean matchesWithFluid(FluidStack fluid, Container inv, Level worldIn) {
        if(this.getRequiredFluid() == FluidIngredient.EMPTY)
            return fluid.isEmpty() && matches(inv, worldIn);
        return this.getRequiredFluid().test(fluid) && matches(inv, worldIn);
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        List<ItemStack> inputs = Lists.newArrayList();
        int i = 0;
        for (int j = 0; j < 9; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        return i == this.getIngredients().size() && RecipeMatcher.findMatches(inputs, this.getIngredients()) != null;
    }

    @Override
    public ItemStack assemble(Container inv, RegistryAccess pRegistryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getIngredients().size();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.COOKING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.COOKING_RECIPE_TYPE;
    }

}
