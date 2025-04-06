package com.flechazo.sakura.data;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.block.BlockRegistry;
import com.flechazo.sakura.data.builder.*;
import com.flechazo.sakura.fluid.BucketItemRegistry;
import com.flechazo.sakura.fluid.FluidRegistry;
import com.flechazo.sakura.item.FoodRegistry;
import com.flechazo.sakura.item.ItemRegistry;
import com.flechazo.sakura.item.enums.SakuraCuisineSet;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import com.flechazo.sakura.item.enums.SakuraNormalItemSet;
import com.flechazo.sakura.tags.SakuraFluidTags;
import com.flechazo.sakura.tags.SakuraItemTags;
import com.flechazo.sakura.utils.FluidIngredient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.fabricators_of_create.porting_lib.data.ConditionalRecipe;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SakuraRecipeProvider extends AbstractRecipeProvider {

    public SakuraRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        registerCraftingRecipe(consumer);
        registerMortarRecipe(consumer);
        registerCookingRecipe(consumer);
        registerFermenterRecipe(consumer);
        registerDistillerRecipe(consumer);
        registerChoppingRecipes(consumer);
    }

    private void registerCraftingRecipe(Consumer<FinishedRecipe> consumer) {

        makeSlab(consumer,
                () -> BlockRegistry.TATAMI_SLAB, 
                () -> BlockRegistry.TATAMI);
        makeSlab(consumer,
                () ->BlockRegistry.TATAMI_SLAB_SUNBURNT,
                () ->BlockRegistry.TATAMI_SUNBURNT);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.STRAW_BLOCK,4).pattern("LLL").pattern("LLL").pattern("LLL")
                .define('L', SakuraItemTags.STRAW).unlockedBy("has_item", has(SakuraItemTags.STRAW)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ItemRegistry.IRON_FISH_KNIFE).pattern("  I").pattern(" I ").pattern("L  ")
                .define('I', Tags.Items.INGOTS_IRON).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.TATAMI, 6).pattern("LLL").pattern("L#L").pattern("LLL")
                .define('#', SakuraItemTags.LUMBER).define('L', SakuraItemTags.STRAW)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.TORCH, 4).pattern("C").pattern("#")
                .define('C', ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL))
                .define('#', Tags.Items.RODS_WOODEN).unlockedBy("has_item", has(Tags.Items.RODS_WOODEN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "torchs_from_charcoal"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.STICK, 4).pattern("#").pattern("#").define('#', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sticks_from_lumbers"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.OBON).pattern("LLL").pattern("L#L")
                .define('#', BlockRegistry.SAKURA_LEAVES).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Items.PAPER, 4).pattern("###").define('#', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "papers_from_lumbers"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.CHOPPING_BOARD).pattern("###").pattern("I I")
                .define('#', SakuraItemTags.LUMBER).define('I', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "chopping_board"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.FERMENTER).pattern("SSS").pattern("PPP").pattern("SSS")
                .define('S', SakuraItemTags.LUMBER).define('P', ItemTags.LOGS)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "fermenter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockItemRegistry.DISTILLER).pattern("ISI").pattern("PPP").pattern("III")
                .define('S', SakuraItemTags.LUMBER).define('P', ItemTags.LOGS).define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "distiller"));

        registerFarmerDelightRecipes(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.COOKING_POT).pattern("#L#").pattern("###")
                .define('#', Tags.Items.INGOTS_IRON).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, BlockRegistry.STONE_MORTAR).pattern("L  ").pattern("###").pattern("###")
                .define('#', Tags.Items.COBBLESTONE).define('L', SakuraItemTags.LUMBER)
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        foodSmeltingRecipes("eggplant_bake", FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT),
                FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT_BAKED), 0.5F, consumer);
        foodSmeltingRecipes("taro_bake", ItemRegistry.TARO,
                FoodRegistry.FOODSET.get(SakuraFoodSet.TARO_BAKED), 0.5F, consumer);
        foodSmeltingRecipes("burger", FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER), 0.5F, consumer);

        foodSmeltingRecipes("chikuwa", FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA_RAW),
                FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA), 0.5F, consumer);

        foodSmeltingRecipes("bun", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BUN), 0.5F, consumer);
        foodSmeltingRecipes("buckwheat_bread", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_BUCKWHEAT),
                FoodRegistry.FOODSET.get(SakuraFoodSet.BUCKWHEAT_BREAD), 0.5F, consumer);
        foodSmeltingRecipes("rice_bread", ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_RICE),
                FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BREAD), 0.5F, consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH), 3)
                .requires(SakuraItemTags.FLOUR_WHEAT).requires(SakuraItemTags.FLOUR_WHEAT)
                .requires(SakuraItemTags.FLOUR_WHEAT).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_WHEAT)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockItemRegistry.NABE_SUKIYAKI)
                .requires(BlockItemRegistry.COOKING_POT).requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN)).requires(SakuraItemTags.RAW_BEEF)
                .requires(Tags.Items.CROPS_CARROT).requires(SakuraItemTags.MUSHROOMS)
                .requires(SakuraItemTags.VEGETABLES).requires(SakuraItemTags.VEGETABLES)
                .unlockedBy("has_pot", has(BlockItemRegistry.COOKING_POT)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockItemRegistry.NABE_ODEN)
                .requires(BlockItemRegistry.COOKING_POT).requires(SakuraItemTags.FISHCAKE)
                .requires(SakuraItemTags.FISHCAKE).requires(SakuraItemTags.FISHCAKE).requires(SakuraItemTags.FISHCAKE)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.DASHI).requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .unlockedBy("has_pot", has(BlockItemRegistry.COOKING_POT)).save(consumer);

        makeItemToBucket(() ->BucketItemRegistry.FOOD_OIL_BUCKET, Ingredient.of(SakuraItemTags.SEEDS_RAPESEED))
                .unlockedBy("has_seeds", has(SakuraItemTags.SEEDS_RAPESEED)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_BUCKWHEAT), 3)
                .requires(SakuraItemTags.FLOUR_BUCKWHEAT).requires(SakuraItemTags.FLOUR_BUCKWHEAT)
                .requires(SakuraItemTags.FLOUR_BUCKWHEAT).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_BUCKWHEAT)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_RICE), 3)
                .requires(SakuraItemTags.FLOUR_RICE).requires(SakuraItemTags.FLOUR_RICE)
                .requires(SakuraItemTags.FLOUR_RICE).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR_RICE)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKO_TAMAGOYAKI).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(BlockRegistry.OBON)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI))
                .unlockedBy("has_obon", has(BlockRegistry.OBON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOUKU_FISH_COOKED).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(BlockRegistry.OBON)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE))
                .unlockedBy("has_obon", has(BlockRegistry.OBON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOUKU_FISH_SALT).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(BlockRegistry.OBON)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE_SALT))
                .unlockedBy("has_obon", has(BlockRegistry.OBON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOUKU_FISH_RAW).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(BlockRegistry.OBON)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI))
                .unlockedBy("has_obon", has(BlockRegistry.OBON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.TEISHOKO_YAKINIKU).requires(SakuraItemTags.SOUPS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(BlockRegistry.OBON)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.YAKINIKU))
                .unlockedBy("has_obon", has(BlockRegistry.OBON)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, FoodRegistry.FOODSET.get(SakuraFoodSet.SASHIMI))
                .requires(SakuraItemTags.SLICES_RAW_FISHES).requires(SakuraItemTags.SLICES_RAW_FISHES)
                .requires(SakuraItemTags.SOYSAUCE).unlockedBy("has_fish", has(SakuraItemTags.SLICES_RAW_FISHES))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA_RAW), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI)).requires(SakuraItemTags.SALT)
                .unlockedBy("has_fish", has(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI))).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.SAKURA_SAPLING).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_PINK).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_RED).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_RED).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_GREEN).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_GREEN).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_YELLOW).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_YELLOW).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, BlockRegistry.MAPLE_SAPLING_ORANGE).requires(ItemTags.SAPLINGS)
                .requires(Tags.Items.DYES_ORANGE).unlockedBy("has_sapling", has(ItemTags.SAPLINGS)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_BAMBOO))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(Items.DRIED_KELP)
                .requires(BlockRegistry.BAMBOOSHOOT)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_SEAWEED))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(Items.DRIED_KELP)
                .requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_MUSHROOM))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(Items.DRIED_KELP)
                .requires(SakuraItemTags.MUSHROOMS)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.ONIGIRI_TEMPURA))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(Items.DRIED_KELP)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA))
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(SakuraItemTags.SLICES_RAW_FISHES)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI_SHRIMP), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(SakuraItemTags.SHRIMP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.SUSHI_TAMAGO), 3)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI)).requires(Items.DRIED_KELP)
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TEMPURA_BATTER), 8)
                .requires(SakuraItemTags.FLOUR).requires(SakuraItemTags.SALT).requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.WATER)
                .unlockedBy("has_flour", has(SakuraItemTags.FLOUR)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.HAMBURGER))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER)).requires(SakuraItemTags.TOMATOSAUCE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN))).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE))
                .requires(SakuraItemTags.MILK).requires(SakuraItemTags.SALT)
                .unlockedBy("has_salt", has(SakuraItemTags.SALT)).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_DISH))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER))
                .requires(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .unlockedBy("has_burger", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER))).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE_BURGER))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE)).requires(SakuraItemTags.CHEESE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN))).save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE_BURGER))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.HAMBURGER)).requires(SakuraItemTags.CHEESE)
                .unlockedBy("has_bun", has(FoodRegistry.FOODSET.get(SakuraFoodSet.BUN)))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "cheese_burger_from_hamburger"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI), 8)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .unlockedBy("has_rice", has(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))).save(consumer);

        foodSmeltingRecipes("mochi_toasted", FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI),
                FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI_TOASTED), 0.5F, consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI_SAKURA))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI))
                .requires(BlockRegistry.SAKURA_LEAVES)
                .unlockedBy("has_mochi", has(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI))).save(consumer);

        makeIngotToBlock(
                () -> BlockItemRegistry.BAMBOO_BLOCK,
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO))
                .unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)))
                .save(consumer);
        makeIngotToBlock(
                () -> BlockItemRegistry.BAMBOO_BLOCK,
                () -> Items.BAMBOO).unlockedBy("has_item", has(Items.BAMBOO))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bamboo_block_from_vanilla_bamboo"));
        makeIngotToBlock(
                () -> BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT,
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT))
                .unlockedBy("has_item",
                        has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT)))
                .save(consumer);
        makeIngotToBlock(
                () -> BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK,
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL))
                .unlockedBy("has_item",
                        has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL)))
                .save(consumer);

        makeBlockToIngot(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO),
                () -> BlockItemRegistry.BAMBOO_BLOCK)
                .save(consumer);
        makeBlockToIngot(
                () -> Items.BAMBOO,
                () -> BlockItemRegistry.BAMBOO_BLOCK).save(consumer,
                new ResourceLocation(SakuraFabric.MODID, "bamboo_block_to_vanilla_bamboo"));
        makeBlockToIngot(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL),
                () -> BlockItemRegistry.BAMBOO_CHARCOAL_BLOCK).save(consumer);
        makeBlockToIngot(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT),
                () -> BlockItemRegistry.BAMBOO_BLOCK_SUNBURNT).save(consumer);

        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_BAMBOO), Ingredient.of(SakuraItemTags.BAMBOO))
                .unlockedBy("has_item", has(SakuraItemTags.BAMBOO)).save(consumer);
        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.MAPLE_LOG))
                .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG)).save(consumer);
        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.SAKURA_LOG))
                .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG)).save(consumer);

        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.MAPLE_WOOD))
                .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "maple_lumber_from_wood"));
        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.SAKURA_WOOD))
                .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sakura_lumber_from_wood"));

        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE),
                Ingredient.of(BlockRegistry.STRIPPED_MAPLE_LOG))
                .unlockedBy("has_item", has(BlockItemRegistry.MAPLE_LOG))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "maple_lumber_from_stripped"));
        makeLumber(
                () -> ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA),
                Ingredient.of(BlockRegistry.STRIPPED_SAKURA_LOG))
                .unlockedBy("has_item", has(BlockItemRegistry.SAKURA_LOG))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sakura_lumber_from_stripped"));

        makeLumberToPlank(
                () -> BlockRegistry.BAMBOO_PLANK, Ingredient.of(SakuraItemTags.LUMBER_BAMBOO))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);
        makeLumberToPlank(
                () -> BlockRegistry.MAPLE_PLANK, Ingredient.of(SakuraItemTags.LUMBER_MAPLE))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);
        makeLumberToPlank(
                () -> BlockRegistry.SAKURA_PLANK, Ingredient.of(SakuraItemTags.LUMBER_SAKURA))
                .unlockedBy("has_item", has(SakuraItemTags.LUMBER)).save(consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.BAMBOO_BLOCK),RecipeCategory.MISC,BlockRegistry.BAMBOO_CHARCOAL_BLOCK, 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bamboo_block_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(BlockRegistry.BAMBOO_BLOCK_SUNBURNT),RecipeCategory.MISC,BlockRegistry.BAMBOO_CHARCOAL_BLOCK, 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(BlockRegistry.BAMBOO_BLOCK_SUNBURNT))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bamboo_block_sunburnt_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)),RecipeCategory.MISC,ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO)))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bamboo_charcoal_from_smelt"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT)),RecipeCategory.MISC,ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL), 0.5F,200)
                .group("sakura").unlockedBy("has_item", has(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT)))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bamboo_charcoal_sunburnt_from_smelt"));

    }

    private void registerMortarRecipe(Consumer<FinishedRecipe> consumer) {
        StoneMortarRecipeBuilder.mortar(Items.BONE_MEAL, 3).addResult(Items.BONE_MEAL, 3).requires(Tags.Items.BONES)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "bonemeal_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.SAND).addResult(Items.FLINT).requires(Tags.Items.GRAVEL).save(consumer,
                new ResourceLocation(SakuraFabric.MODID, "flint_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.GRAVEL)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SALT), 2)
                .requires(Tags.Items.COBBLESTONE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "salt_from_mortar"));

        StoneMortarRecipeBuilder.mortar(Items.COBBLESTONE)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.ALKALINE), 2).requires(Tags.Items.STONE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "alkaline_from_mortar"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHARCOAL_POWDER), 1)
                .requires(Ingredient.of(Items.CHARCOAL,
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL)))
                .requires(Ingredient.of(Items.CHARCOAL,
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL)))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "charcoal_powder"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE), 1)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE), 1)
                .requires(SakuraItemTags.SEEDS_RICE).requires(SakuraItemTags.SEEDS_RICE)
                .requires(SakuraItemTags.SEEDS_RICE).requires(SakuraItemTags.SEEDS_RICE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "brown_rice_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.GREEN_DYE, 1).addResult(Items.GREEN_DYE, 1).requires(ItemTags.LEAVES)
                .requires(ItemTags.LEAVES).requires(ItemTags.LEAVES).requires(ItemTags.LEAVES)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "dye_green_from_leaves"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT), 2)
                .requires(Ingredient.of(SakuraItemTags.RAW_CHICKEN))
                .requires(Ingredient.of(SakuraItemTags.RAW_PORK))
                .requires(Ingredient.of(SakuraItemTags.RAW_BEEF))
                .requires(Ingredient.of(SakuraItemTags.RAW_MUTTON))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "minced_meat"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.BURGER_RAW), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MINCED_MEAT))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS))
                .requires(SakuraItemTags.CROPS_ONION).requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "burger_raw"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI), 1)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI), 1).requires(SakuraItemTags.FISHES)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "surimi_from_mortar"));

        StoneMortarRecipeBuilder.mortar(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS), 2)
                .addResult(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS), 2).requires(SakuraItemTags.BREAD)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "breadcrumbs_from_breads"));

        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RICE), 1)
                .requires(SakuraItemTags.RICE_BROWN).requires(SakuraItemTags.RICE_BROWN)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.SUGAR, 3)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES))
                .requires(Items.SUGAR_CANE).save(consumer,
                        new ResourceLocation(SakuraFabric.MODID, "sugar_from_mortar"));
        StoneMortarRecipeBuilder.mortar(Items.SUGAR, 1)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES))
                .requires(Items.BEETROOT).save(consumer,
                        new ResourceLocation(SakuraFabric.MODID, "beetsugar_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR), 1)
                .requires(SakuraItemTags.GRAIN_WHEAT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "flour_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_BUCKWHEAT), 1)
                .requires(SakuraItemTags.GRAIN_BUCKWHEAT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "flour_buckwheat_from_mortar"));
        StoneMortarRecipeBuilder.mortar(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_RICE), 1)
                .requires(SakuraItemTags.RICE_RICE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "flour_rice_from_mortar"));

    }

    private void registerFarmerDelightRecipes(Consumer<FinishedRecipe> consumer) {
        whenModLoaded(StoneMortarRecipeBuilder.mortar(ModItems.RICE.get())
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW))
                .requires(ModItems.RICE_PANICLE.get()), FarmersDelight.MODID, "farmer_rice_mortar_from_sakura")
                .build(consumer, SakuraFabric.MODID, "farmer_rice_mortar_from_sakura");
        whenModLoaded(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.CANVAS.get()).pattern("##").pattern("##")
                        .define('#', SakuraItemTags.STRAW).unlockedBy("has_straw", has(SakuraItemTags.STRAW)),
                FarmersDelight.MODID, "canvas_from_sakura").build(consumer, SakuraFabric.MODID, "canvas_from_sakura");
        whenModLoaded(ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.TATAMI.get(), 2).pattern("S#").pattern("#S")
                .define('#', SakuraItemTags.STRAW).define('S', ModItems.CANVAS.get())
                .unlockedBy("has_straw", has(SakuraItemTags.STRAW)), FarmersDelight.MODID,"farmer_tatami_from_sakura").build(consumer,
                SakuraFabric.MODID, "farmer_tatami_from_sakura");
        whenModLoaded(
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC,ModItems.ROPE.get(), 3).pattern("s").pattern("s").pattern("s")
                        .define('s', SakuraItemTags.STRAW).unlockedBy("has_straw", has(SakuraItemTags.STRAW)),
                FarmersDelight.MODID,"rope_from_sakura").build(consumer, SakuraFabric.MODID, "rope_from_sakura");
        whenModLoaded(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.ORGANIC_COMPOST.get(), 1).requires(Items.DIRT)
                .requires(Items.ROTTEN_FLESH).requires(Items.ROTTEN_FLESH).requires(SakuraItemTags.STRAW)
                .requires(SakuraItemTags.STRAW).requires(Items.BONE_MEAL).requires(Items.BONE_MEAL)
                .requires(Items.BONE_MEAL).requires(Items.BONE_MEAL)
                .unlockedBy("has_rotten_flesh", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ROTTEN_FLESH))
                .unlockedBy("has_straw", has(SakuraItemTags.STRAW)), FarmersDelight.MODID,"organic_compost_rotten_flesh_from_sakura").build(consumer,
                SakuraFabric.MODID, "organic_compost_rotten_flesh_from_sakura");
        whenModLoaded(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.ORGANIC_COMPOST.get(), 1).requires(Items.DIRT)
                .requires(SakuraItemTags.STRAW).requires(SakuraItemTags.STRAW).requires(Items.BONE_MEAL)
                .requires(Items.BONE_MEAL).requires(ModItems.TREE_BARK.get()).requires(ModItems.TREE_BARK.get())
                .requires(ModItems.TREE_BARK.get()).requires(ModItems.TREE_BARK.get())
                .unlockedBy("has_tree_bark", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TREE_BARK.get()))
                .unlockedBy("has_straw", has(SakuraItemTags.STRAW)), FarmersDelight.MODID,"organic_compost_bark_from_sakura").build(consumer,
                SakuraFabric.MODID, "organic_compost_bark_from_sakura");
    }

    private void registerCookingRecipe(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.BEEF_STICK), 2)
                .requires(SakuraItemTags.RAW_BEEF)
                .requires(SakuraItemTags.BAMBOO)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "beef_stick_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.FOODSET.get(SakuraFoodSet.NATTO), 2, 1.0f, 600)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.STRAW)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "natto_fermenting"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.CHICKEN_STICK), 2)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(SakuraItemTags.BAMBOO)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "chicken_stick_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.EMPTY, FoodRegistry.CUISINES.get(SakuraCuisineSet.PORK_STICK), 2)
                .requires(SakuraItemTags.RAW_PORK)
                .requires(SakuraItemTags.BAMBOO)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "pork_stick_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU), 2)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "tofu_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU_FRIED), 2)
                .requires(SakuraItemTags.TOFU)
                .requires(SakuraItemTags.FLOUR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "tofu_fried_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KAMABOKO), 2)
                .requires(SakuraItemTags.SALT)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "kamaboko_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SATSUMAAGE), 2)
                .requires(SakuraItemTags.SALT)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "satsumaage_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU), 2)
                .requires(SakuraItemTags.RAW_PORK)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS))
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "pork_katsu_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FRIED_CHICKEN), 2)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(Ingredient.of(new ItemStack(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS))))
                .requires(Ingredient.of(SakuraItemTags.FLOUR))
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "fried_chicken_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CROQUETTE), 2)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MASHED_POTATO))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.BREADCRUMBS))
                .requires(SakuraItemTags.MILK)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "croquette_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISHCAKE), 4)
                .requires(SakuraItemTags.SALT)
                .requires(SakuraItemTags.EGGS).requires(SakuraItemTags.CROPS_TARO)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "hanpen_taro_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KAESHI), 4)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "kaeshi_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISHCAKE), 4)
                .requires(SakuraItemTags.SALT)
                .requires(SakuraItemTags.EGGS)
                .requires(Ingredient.of(SakuraItemTags.CROPS_TARO))
                .requires(Ingredient.of(Tags.Items.CROPS_POTATO))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.SURIMI))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "hanpen_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_REDBEAN), 2)
                .requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.MOCHI))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "soup_redbean_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE_ROLL))
                .requires(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .requires(Ingredient.of(SakuraItemTags.RAW_CHICKEN))
                .requires(Ingredient.of(SakuraItemTags.RAW_PORK))
                .requires(Ingredient.of(SakuraItemTags.RAW_BEEF))
                .requires(Ingredient.of(SakuraItemTags.RAW_MUTTON))
                .requires(Ingredient.of(SakuraItemTags.FISHES))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "cabbage_roll_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE), 2)
                .requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "redbean_paste_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE), 2)
                .requires(SakuraItemTags.CROPS_TOMATO)
                .requires(SakuraItemTags.CROPS_TOMATO)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "tomato_sauce_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO), 2)
                .requires(SakuraItemTags.DOUGH_RICE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "dango_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANANKO))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "dananko_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANMITARASHI))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO)).requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "danmitarashi_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DANSANSYOKU))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.DANGO))
                .requires(BlockRegistry.SAKURA_LEAVES)
                .requires(Items.GRASS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "dansansyoku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.DAIFUKU), 2)
                .requires(SakuraItemTags.DOUGH_RICE)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "daifuku_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.KUSA_DAIFUKU), 2)
                .requires(SakuraItemTags.DOUGH_RICE)
                .requires(Items.GRASS)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.REDBEAN_PASTE))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "kusa_daifuku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.BROWN_RICE_COOKED))
                .requires(SakuraItemTags.RICE_BROWN)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "brown_rice_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(SakuraItemTags.RICE_RICE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_REDBEAN))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.CROPS_REDBEAN)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_redbean_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_NATTO))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.NATTO)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_natto_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_NATTO_EGG))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.NATTO)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_natto_egg_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BAMBOO))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(BlockRegistry.BAMBOOSHOOT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_bamboo_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_MUSHROOM))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.MUSHROOMS)

                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_mushrooms_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BEEF))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_BEEF)

                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_beef_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_PORK))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_PORK)

                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_pork_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_FISH))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_FISHES)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_fish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_EGG))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_eggs_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BEEF_EGG))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_BEEF)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_beef_eggs_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_PORK_EGG))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_PORK)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_pork_eggs_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_KATSU))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.KATSU))
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_katsu_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_OYAKO))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_oyako_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_OYAKO_FISH))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(SakuraItemTags.EGGS)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_oyako_fish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.OMURICE))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(Ingredient.of(SakuraItemTags.RAW_CHICKEN))
                .requires(Ingredient.of(SakuraItemTags.RAW_PORK))
                .requires(Ingredient.of(SakuraItemTags.RAW_BEEF))
                .requires(Ingredient.of(SakuraItemTags.RAW_MUTTON))
                .requires(Ingredient.of(SakuraItemTags.FISHES))
                .requires(SakuraItemTags.TOMATOSAUCE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "omurice_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TEMPURA))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.TEMPURA_BATTER))
                .requires(SakuraItemTags.SHRIMP)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "tempura_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FRIES), 2)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "fries_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.MASHED_POTATO), 2)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "mashed_potato_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE_SALT))
                .requires(SakuraItemTags.SALT)
                .requires(SakuraItemTags.RAW_FISHES)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "fish_bake_salt_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FISH_BAKE))
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(SakuraItemTags.SOYSAUCE)

                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "fish_bake_cooking"));
        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.TAMAGOYAKI), 2)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.DASHI)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "tamagoyaki_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.OSUIMONO), 2)
                .requires(Items.DRIED_KELP)
                .requires(SakuraItemTags.SOYSAUCE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "osuimono_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_MISO), 2)
                .requires(SakuraItemTags.MISO)
                .requires(SakuraItemTags.TOFU)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "soup_miso_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIKUJAGA), 2)
                .requires(Ingredient.of(SakuraItemTags.RAW_PORK))
                .requires(Ingredient.of(SakuraItemTags.RAW_BEEF))
                .requires(Tags.Items.CROPS_CARROT)
                .requires(Tags.Items.CROPS_POTATO)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "nikujaga_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_PUMPKIN), 2)
                .requires(SakuraItemTags.CROPS_PUMPKIN)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "nimono_pumpkin_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_RADISH), 2)
                .requires(SakuraItemTags.CROPS_RADISH)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "nimono_radish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.IMOTAKI), 2)
                .requires(SakuraItemTags.CROPS_TARO)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "imotaki_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUZENNI), 2)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(SakuraItemTags.MUSHROOMS)
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "chikuzenni_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NOPPEI_JIRU), 2)
                .requires(SakuraItemTags.RAW_CHICKEN)
                .requires(SakuraItemTags.CROPS_TARO)
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SOYSAUCE)
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "noppei_jiru_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 250),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.NIMONO_FISH), 2)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(SakuraItemTags.MISO)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "nimono_fish_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.FUROFUKI_DAIKON), 2)
                .requires(SakuraItemTags.CROPS_RADISH)
                .requires(SakuraItemTags.MISO)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "furofuki_daikon_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DASHI), 1)
                .requires(SakuraItemTags.RAW_FISHES)
                .requires(Items.DRIED_KELP)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "dashi_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.YAKINIKU))
                .requires(Ingredient.of(SakuraItemTags.RAW_PORK))
                .requires(Ingredient.of(SakuraItemTags.RAW_BEEF))
                .requires(Ingredient.of(SakuraItemTags.RAW_MUTTON))
                .requires(SakuraItemTags.SOYSAUCE)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "yakiniku_cooking"));

        CookingPotRecipeBuilder
                .cooking(FluidIngredient.fromTag(SakuraFluidTags.FOOD_OIL, 125),
                        FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_FRIED))
                .requires(SakuraItemTags.RICE_RICE)
                .requires(SakuraItemTags.EGGS)
                .requires(SakuraItemTags.VEGETABLES)
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rice_fried_cooking"));
    }

    private void registerFermenterRecipe(Consumer<FinishedRecipe> consumer) {
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KOUJI), 2, FluidStack.EMPTY)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED))
                .requires(SakuraItemTags.SALT)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "kouji_fermenting"));
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 1000),
                        new FluidStack(FluidRegistry.DOBUROKU, 500))
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(SakuraItemTags.KOUJI)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "doburoku_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BEER, 100))
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.BROWN_MUSHROOMS)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "basic_beer_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        new FluidStack(FluidRegistry.BEER, 200),0,400)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.GRAIN)
                .requires(SakuraItemTags.YEAST)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "beer_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 200),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.YEAST), 4,FluidStack.EMPTY,0,400)
                .requires(SakuraItemTags.BROWN_MUSHROOMS)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "yeast_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 100),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.YEAST), 4,FluidStack.EMPTY,0,200)
                .requires(SakuraItemTags.YEAST)
                .requires(SakuraItemTags.SUGAR)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "yeast_multiply"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.BREWERS_ALCOHOL, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MIRIN), 8, FluidStack.EMPTY)
                .requires(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_COOKED)).requires(SakuraItemTags.KOUJI)
                .requires(SakuraItemTags.SUGAR)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "mirin_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromFluid(FluidRegistry.DOBUROKU, 500),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU), 2,
                        new FluidStack(FluidRegistry.SAKE, 250), 10F, 500)
                .requires(SakuraItemTags.DUST_CHARCOAL)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sake_charcoal_fermenting"));

        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromFluid(FluidRegistry.DOBUROKU, 500),
                        new FluidStack(FluidRegistry.SAKE, 100), 10F, 1000)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sake_fermenting"));
        FermenterRecipeBuilder
                .fermenting(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 1000),
                        ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO), 4,
                        FluidStack.EMPTY)
                .addResult(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOYSAUCE), 4)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.CROPS_SOYBEAN)
                .requires(SakuraItemTags.KOUJI)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "miso_fermenting"));
    }

    private void registerDistillerRecipe(Consumer<FinishedRecipe> consumer) {
        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromFluid(FluidRegistry.SAKE, 1000),
                        new FluidStack(FluidRegistry.SHOUCHU, 500))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "shouchu_from_sake_distillation"));

        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromFluid(FluidRegistry.BEER, 1000),
                        new FluidStack(FluidRegistry.WHISKEY, 500))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "whiskey_from_beer_distillation"));

        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        new FluidStack(FluidRegistry.RUM, 100))
                .requires(Items.SUGAR_CANE)
                .requires(Items.SUGAR_CANE)
                .requires(SakuraItemTags.YEAST)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rum_cane_distillation"));

        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        new FluidStack(FluidRegistry.RUM, 100))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MOLASSES))
                .requires(SakuraItemTags.YEAST)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "rum_molasses_distillation"));

        DistillerRecipeBuilder
                .distillation(FluidIngredient.fromTag(SakuraFluidTags.WATER_WATER, 500),
                        new FluidStack(FluidRegistry.SHOUCHU, 100))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU))
                .requires(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SAKE_KASU))
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "shouchu_from_sakekasu_distillation"));
    }

    private void registerChoppingRecipes(Consumer<FinishedRecipe> consumer) {
        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH))
                .requires(Ingredient.fromValues(Stream.of(new Ingredient.ItemValue(new ItemStack(Items.COD)),
                        new Ingredient.ItemValue(new ItemStack(Items.SALMON)),
                        new Ingredient.ItemValue(new ItemStack(Items.TROPICAL_FISH)))))
                .requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH))
                .addByproduceWithChance(Items.BONE_MEAL, 0.5F)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "machined_fish_chopping"));

        ChoppingBoardRecipeBuilder.chop(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE))
                .requires(SakuraItemTags.CROPS_CABBAGE).requiresTool(SakuraItemTags.TOOLS_KNIVES_FISH)
                .addByproduce(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE))
                .addByproduceWithChance(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE), 0.5F)
                .save(consumer, new ResourceLocation(SakuraFabric.MODID, "sliced_cabbage_chopping"));
    }

    private void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience,
                                     Consumer<FinishedRecipe> consumer) {
        String namePrefix = new ResourceLocation(SakuraFabric.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,200).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,600).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(consumer, namePrefix + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD,result, experience,100).unlockedBy("has_ingredient",has(ingredient)).group("sakura").save(consumer, namePrefix + "_from_smoking");
    }

    public ShapedRecipeBuilder makeLumberToPlank(Supplier<? extends Block> blockOut, Ingredient ingreIn) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS,blockOut.get()).pattern("##").pattern("##").define('#', ingreIn);
    }

    public ShapelessRecipeBuilder makeLumber(Supplier<? extends Item> ingotOut, Ingredient ingreIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS,ingotOut.get(), 8).requires(ingreIn);
    }

    public ShapelessRecipeBuilder makeItemToBucket(Supplier<? extends Item> ingotOut, Ingredient ingreIn) {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ingotOut.get()).requires(ingreIn).requires(ingreIn).requires(ingreIn)
                .requires(ingreIn).requires(ingreIn).requires(ingreIn).requires(ingreIn).requires(ingreIn)
                .requires(Items.BUCKET);
    }

    public ConditionalRecipe.Builder whenModLoaded(ShapedRecipeBuilder recipe, String modid, String path) {
        return ConditionalRecipe.builder().addCondition(new ModLoadedConditionProvider(modid))
                .addRecipe(consumer -> recipe.save(consumer, new ResourceLocation(SakuraFabric.MODID, path)));
    }

    public ConditionalRecipe.Builder whenModLoaded(ShapelessRecipeBuilder recipe, String modid, String path) {
        return ConditionalRecipe.builder().addCondition(new ModLoadedConditionProvider(modid))
                .addRecipe(consumer -> recipe.save(consumer, new ResourceLocation(SakuraFabric.MODID, path)));
    }

    public ConditionalRecipe.Builder whenModLoaded(StoneMortarRecipeBuilder recipe, String modid, String path) {
        return ConditionalRecipe.builder().addCondition(new ModLoadedConditionProvider(modid))
                .addRecipe(consumer -> recipe.save(consumer, new ResourceLocation(SakuraFabric.MODID, path)));
    }

    // 添加一个实现 ConditionJsonProvider 接口的内部类
    private static class ModLoadedConditionProvider implements net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider {
        private final String modid;

        public ModLoadedConditionProvider(String modid) {
            this.modid = modid;
        }

        @Override
        public ResourceLocation getConditionId() {
            return new ResourceLocation("fabric", "all_mods_loaded");
        }

        @Override
        public void writeParameters(JsonObject json) {
            JsonArray values = new JsonArray();
            values.add(modid);
            json.add("values", values);
        }
    }

    public  ShapedRecipeBuilder makeIngotToBlock(Supplier<? extends Item> result, Supplier<? extends Item> ingredient){
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC,result.get(),1).pattern("###").pattern("###").pattern("###").define('#', ingredient.get())
                .group("sakura").unlockedBy("has_ingredient",has(ingredient.get()));
    }

    public ShapelessRecipeBuilder makeBlockToIngot(Supplier<? extends Item> result, Supplier<? extends Item> ingredient){
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,result.get(),9).requires(ingredient.get())
                .group("sakura").unlockedBy("has_ingredient",has(ingredient.get()));
    }
}
