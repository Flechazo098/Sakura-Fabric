package com.flechazo.sakuraFabric.data.compat;

import com.flechazo.sakuraFabric.item.IFoodLike;
import com.flechazo.sakuraFabric.item.info.FoodInfo;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.commons.compress.utils.Lists;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TFCFoodDefinitionProvider implements DataProvider {

    protected final PackOutput output;
    protected final String modId;
    protected final ExistingFileHelper existingFileHelper;
    private final ExistingFileHelper.IResourceType resourceType;
    protected final Map<ResourceLocation, FoodInfo> datas = Maps.newLinkedHashMap();

    public TFCFoodDefinitionProvider(PackOutput output, ExistingFileHelper existingFileHelper, String modId) {
        this.output = output;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", "tfc/food_items");
    }

    public void addData(Item item) {
        if(item instanceof IFoodLike food)
            this.addData(item, food.getFoodInfo());
    }

    public void addData(Item item, FoodInfo data) {
        this.datas.computeIfAbsent(ForgeRegistries.ITEMS.getKey(item), loc->{
            existingFileHelper.trackGenerated(loc, resourceType);
            return data;
        });
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache){
        this.datas.clear();
        this.addDatas();
        final Path outputFolder = output.getOutputFolder();
        List<CompletableFuture<?>> futureList = Lists.newArrayList();

        this.datas.forEach( (loc, data) -> {
            String pathString = String.join("/", PackType.SERVER_DATA.getDirectory(), this.modId, "tfc", "food_items", loc.getPath()+".json");
            Path path = outputFolder.resolve(pathString);

            JsonObject jsonObj = new JsonObject();
            jsonObj.add("ingredient", Ingredient.of(ForgeRegistries.ITEMS.getValue(loc)).toJson());
            jsonObj.addProperty("hunger", data.getAmount());
            jsonObj.addProperty("saturation", data.getCalories());
            jsonObj.addProperty("decayModifier", data.getDecayModifier());
            jsonObj.addProperty("water", data.getWater());
            jsonObj.addProperty("grain", data.getNutrients()[0]);
            jsonObj.addProperty("fruit", data.getNutrients()[1]);
            jsonObj.addProperty("vegetables", data.getNutrients()[2]);
            jsonObj.addProperty("protein", data.getNutrients()[3]);
            jsonObj.addProperty("dairy", data.getNutrients()[4]);

            futureList.add(DataProvider.saveStable(cache, jsonObj, path));
        });
        return CompletableFuture.allOf(futureList.stream().toArray(CompletableFuture<?>[]::new));

    }

    public void addDatas() {

    }

    @Override
    public String getName() {
        return "MMLib FoodInfo to TFC FoodDefinition Provider";
    }

}