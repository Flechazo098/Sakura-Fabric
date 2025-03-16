package com.flechazo.sakuraFabric.utils;

import com.flechazo.sakuraFabric.client.model.pojo.CubesItem;
import com.flechazo.sakuraFabric.recipes.ChanceResult;
import com.flechazo.sakuraFabric.utils.json.AbstractSerializer;
import com.flechazo.sakuraFabric.utils.json.NonNullListDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.nio.file.Path;

public class DataGenUtil {
    public static final Gson DATA_GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(CubesItem.class, new CubesItem.Deserializer()).create();
    public static final Gson NETWORK_GSON = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization()
            .registerTypeAdapter(ItemStack.class, AbstractSerializer.ItemStackSerializer.getInstance())
            .registerTypeAdapter(Ingredient.class, AbstractSerializer.IngredientSerializer.getInstance())
            .registerTypeAdapter(FluidStack.class, AbstractSerializer.FluidStackSerializer.getInstance())
            .registerTypeAdapter(ChanceResult.class, AbstractSerializer.ChanceResultSerializer.getInstance())
            .registerTypeAdapter(FluidIngredient.class, AbstractSerializer.FluidIngredientSerializer.getInstance())
            .registerTypeAdapter(NonNullList.class, NonNullListDeserializer.getInstance())
            .excludeFieldsWithoutExposeAnnotation().create();

    public static Path createPath(Path path, String namespace, String type, String name) {
        String builder = "data/" + namespace + '/' + type + '/' +
                type + ".json";
        return path.resolve(builder);
    }

    // Thanks Commoble
    public static final Codec<Ingredient> INGREDIENT_CODEC = Codec.PASSTHROUGH.comapFlatMap(dynamic -> {
        try {
            Ingredient ingredient = Ingredient.fromJson(dynamic.convert(JsonOps.INSTANCE).getValue());
            return DataResult.success(ingredient);
        } catch (Exception e) {
            return DataResult.error(e::getMessage);
        }
    }, ingredient -> new Dynamic<JsonElement>(JsonOps.INSTANCE, ingredient.toJson()));
}
