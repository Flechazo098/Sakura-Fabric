package com.flechazo.sakura.recipes;

import com.google.gson.annotations.Expose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractRecipe implements Recipe<Container> {
    protected ResourceLocation id;
    @Expose
    public String group;
    @Expose
    public float experience;
    @Expose
    public int recipeTime;

    public AbstractRecipe() {
    }

    public String getGroup() {
        return this.group;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public float getExperience() {
        return this.experience;
    }

    public int getRecipeTime() {
        return this.recipeTime;
    }
}
