package com.flechazo.sakuraFabric.block;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.block.crops.RiceCrop;
import com.flechazo.sakuraFabric.block.crops.RiceCropRoot;
import com.flechazo.sakuraFabric.block.foods.NabeBlock;
import com.flechazo.sakuraFabric.block.foods.TeishokuBlock;
import com.flechazo.sakuraFabric.block.foods.TeishokuFinishedBlock;
import com.flechazo.sakuraFabric.block.machines.*;
import com.flechazo.sakuraFabric.client.particle.ParticleRegistry;
import com.flechazo.sakuraFabric.item.ItemRegistry;
import com.flechazo.sakuraFabric.item.info.FoodInfo;
import com.flechazo.sakuraFabric.level.tree.MapleTreeGrower;
import com.flechazo.sakuraFabric.level.tree.SakuraTreeFeatures;
import com.flechazo.sakuraFabric.level.tree.SakuraTreeGrower;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BlockRegistry {

    public static final Block SAKURA_LEAVES = registerBlock("sakuraleaves",
            new SakuraLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.SAKURA_LEAF));

    public static final Block MAPLE_LEAVES_RED = registerBlock("mapleleaves_red",
            new SakuraLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.RED_MAPLE_LEAF));

    public static final Block MAPLE_LEAVES_GREEN = registerBlock("mapleleaves_green",
              new SakuraLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.GREEN_MAPLE_LEAF));
    public static final Block MAPLE_LEAVES_YELLOW = registerBlock("mapleleaves_yellow",
              new SakuraLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.YELLOW_MAPLE_LEAF));
    public static final Block MAPLE_LEAVES_ORANGE = registerBlock("mapleleaves_orange",
              new SakuraLeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks()
                    .sound(SoundType.GRASS).noOcclusion(), ParticleRegistry.ORANGE_MAPLE_LEAF));


    public static final RotatedPillarBlock SAKURA_LOG = registerBlock("sakura_log",
              log(MapColor.WOOD, MapColor.PODZOL));

    public static final RotatedPillarBlock STRIPPED_SAKURA_LOG = registerBlock("stripped_sakura_log",
              log(MapColor.WOOD, MapColor.WOOD));

    public static final RotatedPillarBlock SAKURA_WOOD = registerBlock("sakura_wood",
              log(MapColor.PODZOL, MapColor.PODZOL));

    public static final RotatedPillarBlock STRIPPED_SAKURA_WOOD = registerBlock("stripped_sakura_wood",   log(MapColor.WOOD, MapColor.WOOD));

    public static final SaplingBlock SAKURA_SAPLING = registerBlock("sakura_sapling",
              sapling(new SakuraTreeGrower()));

    public static final RotatedPillarBlock MAPLE_LOG = registerBlock("maple_log",
            new MapleTreeLogBlock());

    public static final RotatedPillarBlock MAPLE_SAP_LOG = registerBlock("maple_sap_log",
            new MapleTreeLogBlock());

    public static final RotatedPillarBlock STRIPPED_MAPLE_LOG = registerBlock("stripped_maple_log",
              log(MapColor.WOOD, MapColor.WOOD));

    public static final RotatedPillarBlock MAPLE_WOOD = registerBlock("maple_wood",
              log(MapColor.PODZOL, MapColor.PODZOL));

    public static final RotatedPillarBlock STRIPPED_MAPLE_WOOD = registerBlock("stripped_maple_wood",
              log(MapColor.WOOD, MapColor.WOOD));

    public static final RotatedPillarBlock BAMBOO_BLOCK = registerBlock("bamboo_block",
            new BambooBlock());
    public static final RotatedPillarBlock BAMBOO_BLOCK_SUNBURNT = registerBlock("bamboo_block_sunburnt",   simplebambooBlock(MapColor.SAND, MapColor.WOOD));
    public static final RotatedPillarBlock BAMBOO_CHARCOAL_BLOCK = registerBlock(
            "bamboo_charcoal_block",   simplebambooBlock(MapColor.COLOR_GRAY, MapColor.COLOR_BLACK));

    public static final Block MAPLE_SAPLING_RED = registerBlock("maple_sapling_red",
              sapling(new MapleTreeGrower(SakuraTreeFeatures.MAPLE_RED_KEY, SakuraTreeFeatures.FANCY_MAPLE_RED_KEY)));
    public static final Block MAPLE_SAPLING_GREEN = registerBlock("maple_sapling_green",
              sapling(new MapleTreeGrower(SakuraTreeFeatures.MAPLE_GREEN_KEY, SakuraTreeFeatures.FANCY_MAPLE_GREEN_KEY)));
    public static final Block MAPLE_SAPLING_YELLOW = registerBlock("maple_sapling_yellow",
              sapling(new MapleTreeGrower(SakuraTreeFeatures.MAPLE_YELLOW_KEY, SakuraTreeFeatures.FANCY_MAPLE_YELLOW_KEY)));
    public static final Block MAPLE_SAPLING_ORANGE = registerBlock("maple_sapling_orange",
              sapling(new MapleTreeGrower(SakuraTreeFeatures.MAPLE_ORANGE_KEY, SakuraTreeFeatures.FANCY_MAPLE_ORANGE_KEY)));

    public static final Block BAMBOO_PLANT = registerBlock("bamboo_plant", new BambooPlant());
    public static final Block BAMBOOSHOOT = registerBlock("bamboo_shoot", new BambooShoot());

    public static final Block SAKURA_PLANK = registerBlock("plank_sakura",
              plank(MapColor.WOOD));
    public static final Block MAPLE_PLANK = registerBlock("plank_maple",
              plank(MapColor.SAND));
    public static final Block BAMBOO_PLANK = registerBlock("plank_bamboo",
              plank(MapColor.SAND));

    public static final Block STRAW_BLOCK = registerBlock("straw_block",
              new Block(Block.Properties.copy(Blocks.HAY_BLOCK)));

    public static final Block TATAMI = registerBlock("tatami",
              new TatamiBlock(Block.Properties.copy(Blocks.HAY_BLOCK)));
    public static final Block TATAMI_SUNBURNT = registerBlock("tatami_sunburnt",
              new BaseHorizonBlock(Block.Properties.copy(Blocks.HAY_BLOCK)));
    public static final FacingSlab TATAMI_SLAB = registerBlock("tatami_slab",
              new TatamiSlabBlock(Block.Properties.copy(Blocks.HAY_BLOCK)));
    public static final FacingSlab TATAMI_SLAB_SUNBURNT = registerBlock("tatami_slab_sunburnt",
              new FacingSlab(Block.Properties.copy(Blocks.HAY_BLOCK)));

    public static final Block RICE_CROP_ROOT = registerBlock("rice_crop_root",
              new RiceCropRoot(Block.Properties.copy(Blocks.WHEAT).strength(0.2F)));
    public static final Block RICE_CROP = registerBlock("rice_crop",
              new RiceCrop(Block.Properties.copy(Blocks.WHEAT).strength(0.2F)));

    public static final Block CABBAGE_CROP = registerBlock("cabbage_crop",
              new BaseCropBlock(Block.Properties.copy(Blocks.CARROTS).strength(0.2F),
                      () -> ItemRegistry.CABBAGE_SEEDS));

    public static final Block RADISH_CROP = registerBlock("radish_crop",
              new Age3CropBlock(Block.Properties.copy(Blocks.CARROTS).strength(0.2F),
                      () -> ItemRegistry.RADISH_SEEDS));

    public static final Block ONION_CROP = registerBlock("onion_crop",
              new Age3CropBlock(Block.Properties.copy(Blocks.CARROTS).strength(0.2F),
                      () -> ItemRegistry.ONION_SEEDS));

    public static final Block REDBEAN_CROP = registerBlock("redbean_crop",
              new Age3CropBlock(Block.Properties.copy(Blocks.WHEAT).strength(0.2F),
                      () -> ItemRegistry.RED_BEAN));

    public static final Block SOYBEAN_CROP = registerBlock("soybean_crop",
              new Age3CropBlock(Block.Properties.copy(Blocks.WHEAT).strength(0.2F),
                      () -> ItemRegistry.SOYBEAN));

    public static final Block RAPESEED_CROP = registerBlock("rapeseed_crop",
              new BaseCropBlock(Block.Properties.copy(Blocks.WHEAT).strength(0.2F),
                      () -> ItemRegistry.RAPESEEDS));

    public static final Block BUCKWHEAT_CROP = registerBlock("buckwheat_crop",
              new BaseCropBlock(Block.Properties.copy(Blocks.WHEAT).strength(0.2F),
                      () -> ItemRegistry.BUCKWHEAT));

    public static final Block TARO_CROP = registerBlock("taro_crop",
              new Age3CropBlock(Block.Properties.copy(Blocks.WHEAT).strength(0.2F),
                      () -> ItemRegistry.TARO));

    public static final Block TOMATO_CROP = registerBlock("tomato_crop",
              new HighCropBlock(Block.Properties.copy(Blocks.CARROTS).strength(0.2F),
                      () -> ItemRegistry.TOMATO_SEEDS));

    public static final Block EGGPLANT_CROP = registerBlock("eggplant_crop",
              new HighCropBlock(Block.Properties.copy(Blocks.CARROTS).strength(0.2F),
                      () -> ItemRegistry.EGGPLANT_SEEDS));

    public static final Block STONE_MORTAR = registerBlock("stone_mortar", new StoneMortarBlock());
    public static final Block COOKING_POT = registerBlock("cooking_pot", new CookingPotBlock());
    public static final Block FERMENTER = registerBlock("fermenter", new FermenterBlock());
    public static final Block DISTILLER = registerBlock("distiller", new DistillerBlock());
    public static final Block OBON = registerBlock("obon", new ObonBlock());
    public static final Block CHOPPING_BOARD = registerBlock("chopping_board", new ChoppingBoardBlock());
    public static final Block TEISHOUKU_FINISHED = registerBlock("teishoku_finished", new TeishokuFinishedBlock());
    public static final Block TEISHOUKU_FISH_SALT = registerBlock("teishoku_fish_salt",
            new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final Block TEISHOUKU_FISH_COOKED = registerBlock("teishoku_fish_cooked",
            new TeishokuBlock(FoodInfo.builder().amountAndCalories(8, 0.8f).build()));
    public static final Block TEISHOUKU_FISH_RAW = registerBlock("teishoku_fish_raw",
            new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final Block TEISHOKO_TAMAGOYAKI = registerBlock("teishoku_tamagoyaki",
            new TeishokuBlock(FoodInfo.builder().amountAndCalories(6, 0.8f).build()));
    public static final Block TEISHOKO_YAKINIKU = registerBlock("teishoku_yakiniku",
            new TeishokuBlock(FoodInfo.builder().amountAndCalories(10, 0.8f).build()));

    public static final Block NABE_SUKIYAKI = registerBlock("nabe_sukiyaki",
            new NabeBlock(FoodInfo.builder().amountAndCalories(12, 1f).build()));
    public static final Block NABE_ODEN = registerBlock("nabe_oden",
            new NabeBlock(FoodInfo.builder().amountAndCalories(12, 1f).build()));

    private static <T extends Block> T registerBlock(String name, T block) {
        return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(SakuraFabric.MODID, name), block);
    }

    private static RotatedPillarBlock log (MapColor top, MapColor bark) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .copy(Blocks.OAK_LOG).mapColor(state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : bark))
                .strength(2.0F).sound(SoundType.WOOD));
    }

    private static SaplingBlock sapling(AbstractTreeGrower tree) {
        return new SaplingBlock(tree, BlockBehaviour.Properties.copy(Blocks.GRASS).noCollission().randomTicks()
                .instabreak().sound(SoundType.GRASS));
    }

    private static RotatedPillarBlock simplebambooBlock(MapColor top, MapColor bark) {
        return new RotatedPillarBlock(BlockBehaviour.Properties
                .copy(Blocks.BAMBOO).mapColor(state -> (state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? top : bark))
                .strength(2.0F).sound(SoundType.BAMBOO));
    }

    private static Block plank(MapColor material_color) {
        return new Block(
                BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(material_color).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    public static Collection<Block> getAllBlocks() {
        // 返回所有注册的方块
        return Arrays.stream(BlockRegistry.class.getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(Block.class))
                .map(field -> {
                    try {
                        return (Block) field.get(null);
                    } catch (Exception e) {
                        SakuraFabric.LOGGER.error("Error getting block", e);
                        return null;
                    }
                })
                .filter(block -> block != null)
                .collect(Collectors.toList());
    }

}