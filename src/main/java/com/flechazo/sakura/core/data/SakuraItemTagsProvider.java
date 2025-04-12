package com.flechazo.sakura.core.data;

import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.item.food.FoodRegistry;
import com.flechazo.sakura.init.ItemRegistry;
import com.flechazo.sakura.item.enums.SakuraFoodSet;
import com.flechazo.sakura.item.enums.SakuraNormalItemSet;
import com.flechazo.sakura.tags.SakuraItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SakuraItemTagsProvider extends FabricTagProvider.ItemTagProvider {

    public SakuraItemTagsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        getOrCreateTagBuilder(ItemTags.LOGS)
                .add(BlockItemRegistry.MAPLE_LOG)
                .add(BlockItemRegistry.SAKURA_LOG)
                .add(BlockItemRegistry.MAPLE_WOOD)
                .add(BlockItemRegistry.SAKURA_WOOD)
                .add(BlockItemRegistry.STRIPPED_MAPLE_LOG)
                .add(BlockItemRegistry.STRIPPED_SAKURA_LOG);

        getOrCreateTagBuilder(ItemTags.COALS).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_CHARCOAL));

        getOrCreateTagBuilder(SakuraItemTags.TOOLS_KNIVES).addTag(SakuraItemTags.TOOLS_KNIVES_FISH).addTag(SakuraItemTags.TOOLS_KNIVES_NOODLE);
        getOrCreateTagBuilder(SakuraItemTags.TOOLS_KNIVES_FISH).add(ItemRegistry.IRON_FISH_KNIFE);
        getOrCreateTagBuilder(SakuraItemTags.TOOLS_KNIVES_NOODLE).add(ItemRegistry.IRON_NOODLE_KNIFE);

        getOrCreateTagBuilder(SakuraItemTags.SEEDS_RICE).add(ItemRegistry.RICE_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_CABBAGE).add(ItemRegistry.CABBAGE_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_EGGPLANT).add(ItemRegistry.EGGPLANT_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_BUCKWHEAT).add(ItemRegistry.BUCKWHEAT);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_ONION).add(ItemRegistry.ONION_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_RADISH).add(ItemRegistry.RADISH_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_RAPESEED).add(ItemRegistry.RAPESEEDS);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_REDBEAN).add(ItemRegistry.RED_BEAN);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_SOYBEAN).add(ItemRegistry.SOYBEAN);
        getOrCreateTagBuilder(SakuraItemTags.SEEDS_TOMATO).add(ItemRegistry.TOMATO_SEEDS);

        getOrCreateTagBuilder(SakuraItemTags.YEAST).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.YEAST));

        getOrCreateTagBuilder(SakuraItemTags.BAMBOO).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO));
        getOrCreateTagBuilder(SakuraItemTags.BAMBOO).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BAMBOO_SUNBURNT));
        getOrCreateTagBuilder(SakuraItemTags.BAMBOO).add(Items.BAMBOO);

        getOrCreateTagBuilder(SakuraItemTags.SLICES).addTag(SakuraItemTags.SLICES_CABBAGE).addTag(SakuraItemTags.SLICES_RAW_FISHES);
        getOrCreateTagBuilder(SakuraItemTags.SLICES_RAW_FISHES).add(FoodRegistry.FOODSET.get(SakuraFoodSet.MACHINED_FISH)).addTag(SakuraItemTags.SLICES_RAW_FISHES_COD).addTag(SakuraItemTags.SLICES_RAW_FISHES_SALMON);
        getOrCreateTagBuilder(SakuraItemTags.SLICES_RAW_FISHES_COD).addOptional(new ResourceLocation("farmersdelight:cod_slice"));
        getOrCreateTagBuilder(SakuraItemTags.SLICES_RAW_FISHES_SALMON).addOptional(new ResourceLocation("farmersdelight:salmon_slice"));
        getOrCreateTagBuilder(SakuraItemTags.SLICES_CABBAGE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE));

        getOrCreateTagBuilder(SakuraItemTags.DUST_CHARCOAL).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.CHARCOAL_POWDER));

        getOrCreateTagBuilder(SakuraItemTags.OFFHAND_EQUIPMENT).add(Items.SHIELD);

        getOrCreateTagBuilder(SakuraItemTags.NATTO).add(FoodRegistry.FOODSET.get(SakuraFoodSet.NATTO));
        getOrCreateTagBuilder(SakuraItemTags.SHRIMP).add(FoodRegistry.FOODSET.get(SakuraFoodSet.SHRIMP));

        getOrCreateTagBuilder(SakuraItemTags.FISHCAKE)
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.FISHCAKE))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.CHIKUWA))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.KAMABOKO))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.SATSUMAAGE));

        getOrCreateTagBuilder(SakuraItemTags.KOUJI).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.KOUJI));
        getOrCreateTagBuilder(SakuraItemTags.TOMATOSAUCE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO_SAUCE));

        getOrCreateTagBuilder(SakuraItemTags.TOFU).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU));
        getOrCreateTagBuilder(SakuraItemTags.TOFU_FRIED).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOFU_FRIED));

        getOrCreateTagBuilder(SakuraItemTags.SOYSAUCE).addTag(SakuraItemTags.SOYSAUCE_SOYSAUCE);
        getOrCreateTagBuilder(SakuraItemTags.SOYSAUCE_SOYSAUCE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SOYSAUCE));

        getOrCreateTagBuilder(SakuraItemTags.MISO).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.MISO));
        getOrCreateTagBuilder(SakuraItemTags.DASHI).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DASHI));

        getOrCreateTagBuilder(SakuraItemTags.SOUPS)
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_MISO))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.SOUP_REDBEAN))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.OSUIMONO));

        getOrCreateTagBuilder(SakuraItemTags.CROPS_SOYBEAN).add(ItemRegistry.SOYBEAN);
        getOrCreateTagBuilder(SakuraItemTags.CROPS_REDBEAN).add(ItemRegistry.RED_BEAN);
        getOrCreateTagBuilder(SakuraItemTags.CROPS_BUCKWHEAT).add(ItemRegistry.BUCKWHEAT);

        getOrCreateTagBuilder(SakuraItemTags.CROPS_RICE).add(ItemRegistry.RICE_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.CROPS_TARO).add(ItemRegistry.TARO);
        getOrCreateTagBuilder(SakuraItemTags.CROPS_CABBAGE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE));
        getOrCreateTagBuilder(SakuraItemTags.CROPS_EGGPLANT).add(FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT));
        getOrCreateTagBuilder(SakuraItemTags.CROPS_ONION).add(FoodRegistry.FOODSET.get(SakuraFoodSet.ONION));
        getOrCreateTagBuilder(SakuraItemTags.CROPS_RADISH).add(FoodRegistry.FOODSET.get(SakuraFoodSet.RADISH));
        getOrCreateTagBuilder(SakuraItemTags.CROPS_TOMATO).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO));

        getOrCreateTagBuilder(SakuraItemTags.CROPS_RICE).add(ItemRegistry.RICE_SEEDS);
        getOrCreateTagBuilder(SakuraItemTags.CROPS_TARO).add(ItemRegistry.TARO);

        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_CABBAGE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE));
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_EGGPLANT).add(FoodRegistry.FOODSET.get(SakuraFoodSet.EGGPLANT));
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_ONION).add(FoodRegistry.FOODSET.get(SakuraFoodSet.ONION));
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_RADISH).add(FoodRegistry.FOODSET.get(SakuraFoodSet.RADISH));
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_TOMATO).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO));

        getOrCreateTagBuilder(SakuraItemTags.RICE_BROWN).addTag(SakuraItemTags.GRAIN_RICE);
        getOrCreateTagBuilder(SakuraItemTags.RICE_RICE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.RICE));
        getOrCreateTagBuilder(SakuraItemTags.STRAW).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.STRAW)).addOptional(new ResourceLocation("farmersdelight:straw"));
        getOrCreateTagBuilder(SakuraItemTags.RICE).addTag(SakuraItemTags.RICE_BROWN).addTag(SakuraItemTags.RICE_RICE);

        getOrCreateTagBuilder(SakuraItemTags.LUMBER).addTag(SakuraItemTags.LUMBER_BAMBOO).addTag(SakuraItemTags.LUMBER_MAPLE)
                .addTag(SakuraItemTags.LUMBER_SAKURA);

        getOrCreateTagBuilder(SakuraItemTags.LUMBER_TFC).addTag(SakuraItemTags.LUMBER);

        getOrCreateTagBuilder(SakuraItemTags.LUMBER_BAMBOO).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_BAMBOO));

        getOrCreateTagBuilder(SakuraItemTags.LUMBER_MAPLE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_MAPLE));
        getOrCreateTagBuilder(SakuraItemTags.LUMBER_SAKURA).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.LUMBER_SAKURA));
        getOrCreateTagBuilder(SakuraItemTags.GRAIN_RICE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.BROWN_RICE));
        getOrCreateTagBuilder(SakuraItemTags.GRAIN_BUCKWHEAT).add(ItemRegistry.BUCKWHEAT);

        getOrCreateTagBuilder(SakuraItemTags.SALT).addTag(SakuraItemTags.DUST_SALT);
        getOrCreateTagBuilder(SakuraItemTags.DUST_SALT).addTag(SakuraItemTags.SALT_SALT);
        getOrCreateTagBuilder(SakuraItemTags.SALT_SALT).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.SALT));

        getOrCreateTagBuilder(SakuraItemTags.SUGAR).addTag(SakuraItemTags.SUGAR_SUGAR);
        getOrCreateTagBuilder(SakuraItemTags.SUGAR_SUGAR).add(Items.SUGAR);

        getOrCreateTagBuilder(SakuraItemTags.CHEESE).addTag(SakuraItemTags.CHEESE_CHEESE);
        getOrCreateTagBuilder(SakuraItemTags.CHEESE_CHEESE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.CHEESE));

        getOrCreateTagBuilder(SakuraItemTags.WATER).addTag(SakuraItemTags.WATER_WATER);
        getOrCreateTagBuilder(SakuraItemTags.WATER_WATER).add(Items.WATER_BUCKET);

        getOrCreateTagBuilder(SakuraItemTags.FLOUR).addTags(SakuraItemTags.FLOUR_WHEAT, SakuraItemTags.FLOUR_BUCKWHEAT,
                SakuraItemTags.FLOUR_RICE);
        getOrCreateTagBuilder(SakuraItemTags.FLOUR_WHEAT).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR));
        getOrCreateTagBuilder(SakuraItemTags.FLOUR_BUCKWHEAT).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_BUCKWHEAT));
        getOrCreateTagBuilder(SakuraItemTags.FLOUR_RICE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.FLOUR_RICE));

        getOrCreateTagBuilder(SakuraItemTags.DOUGH).addTags(SakuraItemTags.DOUGH_WHEAT, SakuraItemTags.DOUGH_BUCKWHEAT,
                SakuraItemTags.DOUGH_RICE);
        getOrCreateTagBuilder(SakuraItemTags.DOUGH_WHEAT).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH));
        getOrCreateTagBuilder(SakuraItemTags.DOUGH_BUCKWHEAT).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_BUCKWHEAT));
        getOrCreateTagBuilder(SakuraItemTags.DOUGH_RICE).add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.DOUGH_RICE));

        registerForgeTags();
    }

    @SuppressWarnings("unchecked")
    private void registerForgeTags() {
        getOrCreateTagBuilder(SakuraItemTags.SEEDS).addTag(SakuraItemTags.SEEDS_CABBAGE).addTag(SakuraItemTags.SEEDS_ONION)
                .addTag(SakuraItemTags.SEEDS_EGGPLANT).addTag(SakuraItemTags.SEEDS_RADISH)
                .addTag(SakuraItemTags.SEEDS_RICE).addTag(SakuraItemTags.SEEDS_TOMATO)
                .addTag(SakuraItemTags.SEEDS_BUCKWHEAT).addTag(SakuraItemTags.SEEDS_RAPESEED)
                .addTag(SakuraItemTags.SEEDS_REDBEAN);
        getOrCreateTagBuilder(SakuraItemTags.CROPS).addTag(SakuraItemTags.CROPS_CABBAGE).addTag(SakuraItemTags.CROPS_ONION)
                .addTag(SakuraItemTags.CROPS_BUCKWHEAT).addTag(SakuraItemTags.CROPS_EGGPLANT)
                .addTag(SakuraItemTags.CROPS_RADISH).addTag(SakuraItemTags.CROPS_RICE)
                .addTag(SakuraItemTags.CROPS_TOMATO).addTag(SakuraItemTags.CROPS_TARO)
                .addTag(SakuraItemTags.SEEDS_RAPESEED).addTag(SakuraItemTags.CROPS_REDBEAN)
                .addTag(SakuraItemTags.CROPS_PUMPKIN)
        ;

        getOrCreateTagBuilder(SakuraItemTags.CROPS_PUMPKIN).add(Items.PUMPKIN);
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_PUMPKIN).add(Items.PUMPKIN);

        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES).addTag(SakuraItemTags.VEGETABLES_CABBAGE)
                .addTag(SakuraItemTags.VEGETABLES_BEETROOT).addTag(SakuraItemTags.VEGETABLES_CARROT)
                .addTag(SakuraItemTags.VEGETABLES_EGGPLANT).addTag(SakuraItemTags.VEGETABLES_ONION)
                .addTag(SakuraItemTags.VEGETABLES_POTATO).addTag(SakuraItemTags.VEGETABLES_RADISH)
                .addTag(SakuraItemTags.VEGETABLES_TOMATO).addTag(SakuraItemTags.VEGETABLES_PUMPKIN)
                .add(ItemRegistry.MATERIALS.get(SakuraNormalItemSet.IMOGARA));
        getOrCreateTagBuilder(SakuraItemTags.LEAFYVEGETABLES).addTag(SakuraItemTags.LEAFYVEGETABLES_CABBAGE);
        getOrCreateTagBuilder(SakuraItemTags.LEAFYVEGETABLES_CABBAGE).addTag(SakuraItemTags.VEGETABLES_CABBAGE);

        getOrCreateTagBuilder(SakuraItemTags.MUSHROOMS).addTags(SakuraItemTags.BROWN_MUSHROOMS, SakuraItemTags.RED_MUSHROOMS);
        getOrCreateTagBuilder(SakuraItemTags.BROWN_MUSHROOMS).add(Items.BROWN_MUSHROOM);
        getOrCreateTagBuilder(SakuraItemTags.RED_MUSHROOMS).add(Items.RED_MUSHROOM);
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_BEETROOT).add(Items.BEETROOT);
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_CARROT).add(Items.CARROT);
        getOrCreateTagBuilder(SakuraItemTags.VEGETABLES_POTATO).add(Items.POTATO);
        getOrCreateTagBuilder(SakuraItemTags.COOKIES).add(Items.COOKIE);
        getOrCreateTagBuilder(SakuraItemTags.BREAD).addTags(SakuraItemTags.BREAD_WHEAT, SakuraItemTags.BREAD_BUCKWHEAT,
                SakuraItemTags.BREAD_RICE);
        getOrCreateTagBuilder(SakuraItemTags.BREAD_WHEAT).add(Items.BREAD, FoodRegistry.FOODSET.get(SakuraFoodSet.BUN));
        getOrCreateTagBuilder(SakuraItemTags.BREAD_BUCKWHEAT).add(FoodRegistry.FOODSET.get(SakuraFoodSet.BUCKWHEAT_BREAD));
        getOrCreateTagBuilder(SakuraItemTags.BREAD_RICE).add(FoodRegistry.FOODSET.get(SakuraFoodSet.RICE_BREAD));
        getOrCreateTagBuilder(SakuraItemTags.COOKED_BEEF).add(Items.COOKED_BEEF);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_CHICKEN).add(Items.COOKED_CHICKEN);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_PORK).add(Items.COOKED_PORKCHOP);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_MUTTON).add(Items.COOKED_MUTTON);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_FISHES).addTags(SakuraItemTags.COOKED_FISHES_COD,
                SakuraItemTags.COOKED_FISHES_SALMON);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_FISHES_COD).add(Items.COOKED_COD);
        getOrCreateTagBuilder(SakuraItemTags.COOKED_FISHES_SALMON).add(Items.COOKED_SALMON);
        getOrCreateTagBuilder(SakuraItemTags.EGGS).add(Items.EGG);
        getOrCreateTagBuilder(SakuraItemTags.GRAIN).addTags(SakuraItemTags.GRAIN_WHEAT, SakuraItemTags.GRAIN_RICE,
                SakuraItemTags.GRAIN_BUCKWHEAT);
        getOrCreateTagBuilder(SakuraItemTags.GRAIN_WHEAT).add(Items.WHEAT);
        getOrCreateTagBuilder(SakuraItemTags.MILK).addTags(SakuraItemTags.MILK_BUCKET);
        getOrCreateTagBuilder(SakuraItemTags.MILK_BUCKET).add(Items.MILK_BUCKET);
        getOrCreateTagBuilder(SakuraItemTags.RAW_BEEF).add(Items.BEEF);
        getOrCreateTagBuilder(SakuraItemTags.RAW_CHICKEN).add(Items.CHICKEN);
        getOrCreateTagBuilder(SakuraItemTags.RAW_PORK).add(Items.PORKCHOP);
        getOrCreateTagBuilder(SakuraItemTags.RAW_MUTTON).add(Items.MUTTON);
        getOrCreateTagBuilder(SakuraItemTags.FISHES).addTag(SakuraItemTags.RAW_FISHES);
        getOrCreateTagBuilder(SakuraItemTags.RAW_FISHES).addTags(SakuraItemTags.SLICES_RAW_FISHES, SakuraItemTags.RAW_FISHES_COD, SakuraItemTags.RAW_FISHES_SALMON,
                SakuraItemTags.RAW_FISHES_TROPICAL);
        getOrCreateTagBuilder(SakuraItemTags.RAW_FISHES_COD).add(Items.COD);
        getOrCreateTagBuilder(SakuraItemTags.RAW_FISHES_SALMON).add(Items.SALMON);
        getOrCreateTagBuilder(SakuraItemTags.RAW_FISHES_TROPICAL).add(Items.TROPICAL_FISH);
        getOrCreateTagBuilder(SakuraItemTags.SALAD_INGREDIENTS).addTags(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE,
                SakuraItemTags.SALAD_INGREDIENTS_TOMATO);
        getOrCreateTagBuilder(SakuraItemTags.SALAD_INGREDIENTS_CABBAGE)
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.CABBAGE))
                .add(FoodRegistry.FOODSET.get(SakuraFoodSet.SLICED_CABBAGE));
        getOrCreateTagBuilder(SakuraItemTags.SALAD_INGREDIENTS_TOMATO).add(FoodRegistry.FOODSET.get(SakuraFoodSet.TOMATO));
        getOrCreateTagBuilder(SakuraItemTags.TOOLS).addTags(SakuraItemTags.TOOLS_AXES, SakuraItemTags.TOOLS_PICKAXES,
                SakuraItemTags.TOOLS_SHOVELS);
        getOrCreateTagBuilder(SakuraItemTags.TOOLS_AXES).add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE,
                Items.GOLDEN_AXE, Items.NETHERITE_AXE);
        getOrCreateTagBuilder(SakuraItemTags.TOOLS_PICKAXES).add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE,
                Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.NETHERITE_PICKAXE);
        getOrCreateTagBuilder(SakuraItemTags.TOOLS_SHOVELS).add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL,
                Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.NETHERITE_SHOVEL);
    }

    @Override
    public String getName() {
        return "Sakura Items' Tags";
    }
}
