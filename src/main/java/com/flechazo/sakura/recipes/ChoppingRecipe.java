package com.flechazo.sakura.recipes;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChoppingRecipe extends AbstractRecipe {

    @Expose
    @SerializedName("ingredient")
    public Ingredient input;

    @Expose
    @SerializedName("tool")
    public Ingredient tool;

    @Expose
    @SerializedName("result")
    public ItemStack output;

    @Expose
    @SerializedName("byproducts")
    public NonNullList<ChanceResult> extraOutput;

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input);
    }

    public Ingredient getTool() {
        return tool;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(Container inv, Level worldIn) {
        if (inv.isEmpty())
            return false;
        return input.test(inv.getItem(0));
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.output;
    }

    public NonNullList<ChanceResult> getByproducts() {
        return this.extraOutput;
    }

    public List<ItemStack> rollByproducts(RandomSource rand, int fortuneLevel) {
        List<ItemStack> results = Lists.newArrayList();
        NonNullList<ChanceResult> rollableResults = getByproducts();
        for (ChanceResult output : rollableResults) {
            ItemStack stack = output.rollOutput(rand, fortuneLevel);
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return this.output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getIngredients().size();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.CHOPPING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.CHOPPING_RECIPE_TYPE;
    }

}