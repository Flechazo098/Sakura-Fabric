package com.flechazo.sakuraFabric.utils;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class FluidIngredient implements Predicate<FluidStack> {
    public static final FluidIngredient EMPTY;
    public List<FluidStack> matchingFluidStacks;
    protected int amountRequired;

    public FluidIngredient() {
    }

    public static FluidIngredient fromTag(TagKey<Fluid> tag, int amount) {
        FluidTagIngredient ingredient = new FluidTagIngredient();
        ingredient.tag = tag;
        ingredient.amountRequired = amount;
        return ingredient;
    }

    public static FluidIngredient fromFluid(Fluid fluid, int amount) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = fluid;
        ingredient.amountRequired = amount;
        ingredient.fixFlowing();
        return ingredient;
    }

    public static FluidIngredient fromFluidStack(FluidStack fluidStack) {
        FluidStackIngredient ingredient = new FluidStackIngredient();
        ingredient.fluid = fluidStack.getFluid();
        ingredient.amountRequired = (int) fluidStack.getAmount();
        ingredient.fixFlowing();
        if (fluidStack.hasTag()) {
            ingredient.tagToMatch = fluidStack.getTag();
        }

        return ingredient;
    }

    protected abstract boolean testInternal(FluidStack var1);

    protected abstract void readInternal(JsonObject var1);

    protected abstract void writeInternal(JsonObject var1);

    protected abstract List<FluidStack> determineMatchingFluidStacks();

    public int getRequiredAmount() {
        return this.amountRequired;
    }

    public List<FluidStack> getMatchingFluidStacks() {
        return this.matchingFluidStacks != null ? this.matchingFluidStacks : (this.matchingFluidStacks = this.determineMatchingFluidStacks());
    }

    public boolean test(FluidStack t) {
        if (t == null) {
            throw new IllegalArgumentException("FluidStack cannot be null");
        } else {
            return this.testInternal(t);
        }
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        this.writeInternal(json);
        if (this.amountRequired != 0) {
            json.addProperty("amount", this.amountRequired);
        }

        return json;
    }

    public static boolean isFluidIngredient(@Nullable JsonElement je) {
        if (je != null && !je.isJsonNull()) {
            if (!je.isJsonObject()) {
                return false;
            } else {
                JsonObject json = je.getAsJsonObject();
                if (json.has("null_fluid")) {
                    return true;
                } else if (json.has("fluidTag")) {
                    return true;
                } else {
                    return json.has("fluid");
                }
            }
        } else {
            return false;
        }
    }

    public static FluidIngredient deserialize(@Nullable JsonElement je) {
        if (!isFluidIngredient(je)) {
            throw new JsonSyntaxException("Invalid fluid ingredient: " + Objects.toString(je));
        } else {
            JsonObject json = je.getAsJsonObject();
            if (json.has("null_fluid")) {
                if (!json.get("null_fluid").getAsBoolean()) {
                    throw new JsonSyntaxException("'null_fluid' can NOT be false, delete it: " + Objects.toString(je));
                } else {
                    return EMPTY;
                }
            } else {
                FluidIngredient ingredient = (FluidIngredient)(json.has("fluidTag") ? new FluidTagIngredient() : new FluidStackIngredient());
                ingredient.readInternal(json);
                if (!json.has("amount")) {
                    throw new JsonSyntaxException("Fluid ingredient has to define an amount");
                } else {
                    ingredient.amountRequired = GsonHelper.getAsInt(json, "amount");
                    return ingredient;
                }
            }
        }
    }

    static {
        EMPTY = FluidIngredient.NullFluidIngredient.EMPTY;
    }

    private static class NullFluidIngredient extends FluidIngredient {
        private static final NullFluidIngredient EMPTY = new NullFluidIngredient();

        private NullFluidIngredient() {
        }

        protected boolean testInternal(FluidStack t) {
            return true;
        }

        protected void readInternal(JsonObject json) {
        }

        protected void writeInternal(JsonObject json) {
            json.addProperty("null_fluid", true);
        }

        protected List<FluidStack> determineMatchingFluidStacks() {
            return ImmutableList.of(new FluidStack(Fluids.EMPTY, 0));
        }
    }

    public static class FluidStackIngredient extends FluidIngredient {
        protected Fluid fluid;
        protected CompoundTag tagToMatch = new CompoundTag();

        public FluidStackIngredient() {
        }

        void fixFlowing() {
            if (this.fluid instanceof FlowingFluid) {
                this.fluid = ((FlowingFluid)this.fluid).getSource();
            }

        }

        protected boolean testInternal(FluidStack t) {
            if (!t.getFluid().isSame(this.fluid)) {
                return false;
            } else if (this.tagToMatch.isEmpty()) {
                return true;
            } else {
                CompoundTag tag = t.getOrCreateTag();
                return tag.copy().merge(this.tagToMatch).equals(tag);
            }
        }

        protected void readInternal(JsonObject json) {
            FluidStack stack = FluidHelper.deserializeFluidStack(json);
            this.fluid = stack.getFluid();
            this.tagToMatch = stack.getOrCreateTag();
        }

        protected void writeInternal(JsonObject json) {
            json.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.fluid).toString());
            json.add("nbt", JsonParser.parseString(this.tagToMatch.toString()));
        }

        protected List<FluidStack> determineMatchingFluidStacks() {
            return ImmutableList.of(this.tagToMatch.isEmpty() ? new FluidStack(this.fluid, this.amountRequired) : new FluidStack(this.fluid, this.amountRequired, this.tagToMatch));
        }
    }

    public static class FluidTagIngredient extends FluidIngredient {
        protected TagKey<Fluid> tag;

        public FluidTagIngredient() {
        }

        protected boolean testInternal(FluidStack t) {
            if (this.tag == null) {
                for(FluidStack accepted : this.getMatchingFluidStacks()) {
                    if (accepted.getFluid().isSame(t.getFluid())) {
                        return true;
                    }
                }
            }

            return ((IReverseTag)ForgeRegistries.FLUIDS.tags().getReverseTag(t.getFluid()).get()).containsTag(this.tag);
        }

        protected void readInternal(JsonObject json) {
            ResourceLocation name = new ResourceLocation(GsonHelper.getAsString(json, "fluidTag"));
            this.tag = FluidTags.create(name);
        }

        protected void writeInternal(JsonObject json) {
            json.addProperty("fluidTag", this.tag.location().toString());
        }

        protected List<FluidStack> determineMatchingFluidStacks() {
            return (List)ForgeRegistries.FLUIDS.tags().getTag(this.tag).stream().map((f) -> f instanceof FlowingFluid ? ((FlowingFluid)f).getSource() : f).distinct().map((f) -> new FluidStack(f, this.amountRequired)).collect(Collectors.toList());
        }
    }
}