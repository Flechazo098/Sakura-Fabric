package com.flechazo.sakura.block;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.item.StoneMortarItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BlockItemRegistry {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item SAKURA_LEAVES = registerItem("sakuraleaves",
             new BlockItem(BlockRegistry.SAKURA_LEAVES, SakuraFabric.defaultItemProperties()));

    public static final Item MAPLE_LEAVES_RED = registerItem("mapleleaves_red",
             new BlockItem(BlockRegistry.MAPLE_LEAVES_RED, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_LEAVES_YELLOW = registerItem("mapleleaves_yellow",
             new BlockItem(BlockRegistry.MAPLE_LEAVES_YELLOW, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_LEAVES_ORANGE = registerItem("mapleleaves_orange",
             new BlockItem(BlockRegistry.MAPLE_LEAVES_ORANGE, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_LEAVES_GREEN = registerItem("mapleleaves_green",
             new BlockItem(BlockRegistry.MAPLE_LEAVES_GREEN, SakuraFabric.defaultItemProperties()));

    public static final Item SAKURA_LOG = registerItem("sakura_log",
             new BlockItem(BlockRegistry.SAKURA_LOG, SakuraFabric.defaultItemProperties()));

    public static final Item STRIPPED_SAKURA_LOG = registerItem("stripped_sakura_log",
             new BlockItem(BlockRegistry.STRIPPED_SAKURA_LOG, SakuraFabric.defaultItemProperties()));

    public static final Item SAKURA_WOOD = registerItem("sakura_wood",
             new BlockItem(BlockRegistry.SAKURA_WOOD, SakuraFabric.defaultItemProperties()));
    public static final Item STRIPPED_SAKURA_WOOD = registerItem("stripped_sakura_wood",
             new BlockItem(BlockRegistry.STRIPPED_SAKURA_WOOD, SakuraFabric.defaultItemProperties()));

    public static final Item MAPLE_LOG = registerItem("maple_log",
             new BlockItem(BlockRegistry.MAPLE_LOG, SakuraFabric.defaultItemProperties()));
    public static final Item STRIPPED_MAPLE_LOG = registerItem("stripped_maple_log",
             new BlockItem(BlockRegistry.STRIPPED_MAPLE_LOG, SakuraFabric.defaultItemProperties()));

    public static final Item MAPLE_WOOD = registerItem("maple_wood",
             new BlockItem(BlockRegistry.MAPLE_WOOD, SakuraFabric.defaultItemProperties()));
    public static final Item STRIPPED_MAPLE_WOOD = registerItem("stripped_maple_wood",
             new BlockItem(BlockRegistry.STRIPPED_MAPLE_WOOD, SakuraFabric.defaultItemProperties()));

    public static final Item BAMBOO_BLOCK = registerItem("bamboo_block",
             new BlockItem(BlockRegistry.BAMBOO_BLOCK, SakuraFabric.defaultItemProperties()));
    public static final Item BAMBOO_BLOCK_SUNBURNT = registerItem("bamboo_block_sunburnt",
             new BlockItem(BlockRegistry.BAMBOO_BLOCK_SUNBURNT, SakuraFabric.defaultItemProperties()));
    public static final Item BAMBOO_CHARCOAL_BLOCK = registerItem("bamboo_charcoal_block",
             new BlockItem(BlockRegistry.BAMBOO_CHARCOAL_BLOCK, SakuraFabric.defaultItemProperties()));

    public static final Item SAKURA_PLANK = registerItem("plank_sakura",
             new BlockItem(BlockRegistry.SAKURA_PLANK, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_PLANK = registerItem("plank_maple",
             new BlockItem(BlockRegistry.MAPLE_PLANK, SakuraFabric.defaultItemProperties()));
    public static final Item BAMBOO_PLANK = registerItem("plank_bamboo",
             new BlockItem(BlockRegistry.BAMBOO_PLANK, SakuraFabric.defaultItemProperties()));

    public static final Item TATAMI = registerItem("tatami",
             new BlockItem(BlockRegistry.TATAMI, SakuraFabric.defaultItemProperties()));
    public static final Item TATAMI_SLAB = registerItem("tatami_slab",
             new BlockItem(BlockRegistry.TATAMI_SLAB, SakuraFabric.defaultItemProperties()));

    public static final Item TATAMI_SUNBURNT = registerItem("tatami_sunburnt",
             new BlockItem(BlockRegistry.TATAMI_SUNBURNT, SakuraFabric.defaultItemProperties()));
    public static final Item TATAMI_SLAB_SUNBURNT = registerItem("tatami_slab_sunburnt",
             new BlockItem(BlockRegistry.TATAMI_SLAB_SUNBURNT, SakuraFabric.defaultItemProperties()));

    public static final Item STRAW_BLOCK = registerItem("straw_block",
             new BlockItem(BlockRegistry.STRAW_BLOCK, SakuraFabric.defaultItemProperties()));

    public static final Item BAMBOOSHOOT = registerItem("bamboo_shoot",
             new BlockItem(BlockRegistry.BAMBOOSHOOT, SakuraFabric.defaultItemProperties()));

    public static final Item SAKURA_SAPLING = registerItem("sakura_sapling",
             new BlockItem(BlockRegistry.SAKURA_SAPLING, SakuraFabric.defaultItemProperties()));

    public static final Item MAPLE_SAPLING_RED = registerItem("maple_sapling_red",
             new BlockItem(BlockRegistry.MAPLE_SAPLING_RED, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_SAPLING_YELLOW = registerItem("maple_sapling_yellow",
             new BlockItem(BlockRegistry.MAPLE_SAPLING_YELLOW, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_SAPLING_ORANGE = registerItem("maple_sapling_orange",
             new BlockItem(BlockRegistry.MAPLE_SAPLING_ORANGE, SakuraFabric.defaultItemProperties()));
    public static final Item MAPLE_SAPLING_GREEN = registerItem("maple_sapling_green",
             new BlockItem(BlockRegistry.MAPLE_SAPLING_GREEN, SakuraFabric.defaultItemProperties()));

    public static final Item STONE_MORTAR = registerItem("stone_mortar", new  StoneMortarItem());

    public static final Item COOKING_POT = registerItem("cooking_pot",
             new BlockItem(BlockRegistry.COOKING_POT, SakuraFabric.defaultItemProperties()));

    public static final Item FERMENTER = registerItem("fermenter",
             new BlockItem(BlockRegistry.FERMENTER, SakuraFabric.defaultItemProperties()));

    public static final Item DISTILLER = registerItem("distiller",
             new BlockItem(BlockRegistry.DISTILLER, SakuraFabric.defaultItemProperties()));

    public static final Item OBON = registerItem("obon",
             new BlockItem(BlockRegistry.OBON, SakuraFabric.defaultItemProperties()));

    public static final Item CHOPPING_BOARD = registerItem("chopping_board",
             new BlockItem(BlockRegistry.CHOPPING_BOARD, SakuraFabric.defaultItemProperties()));

    public static final Item TEISHOUKU_FISH_RAW = registerItem("teishoku_fish_raw",
             new BlockItem(BlockRegistry.TEISHOUKU_FISH_RAW, SakuraFabric.defaultItemProperties()));

    public static final Item TEISHOUKU_FISH_COOKED = registerItem("teishoku_fish_cooked",
             new BlockItem(BlockRegistry.TEISHOUKU_FISH_COOKED, SakuraFabric.defaultItemProperties()));

    public static final Item TEISHOUKU_FISH_SALT = registerItem("teishoku_fish_salt",
             new BlockItem(BlockRegistry.TEISHOUKU_FISH_SALT, SakuraFabric.defaultItemProperties()));

    public static final Item TEISHOKO_TAMAGOYAKI = registerItem("teishoku_tamagoyaki",
             new BlockItem(BlockRegistry.TEISHOKO_TAMAGOYAKI, SakuraFabric.defaultItemProperties()));

    public static final Item TEISHOKO_YAKINIKU = registerItem("teishoku_yakiniku",
             new BlockItem(BlockRegistry.TEISHOKO_YAKINIKU, SakuraFabric.defaultItemProperties()));

    public static final Item NABE_SUKIYAKI = registerItem("nabe_sukiyaki",
             new BlockItem(BlockRegistry.NABE_SUKIYAKI, SakuraFabric.defaultItemProperties()));

    public static final Item NABE_ODEN = registerItem("nabe_oden",
             new BlockItem(BlockRegistry.NABE_ODEN, SakuraFabric.defaultItemProperties()));

    private static Item registerItem(String name, Item item) {
        Item registeredItem = Registry.register(BuiltInRegistries.ITEM,
                new ResourceLocation(SakuraFabric.MODID, name), item);
        ITEMS.add(registeredItem);
        return registeredItem;
    }

    public static List<Item> getEntries() {
        return ITEMS;
    }

    public static void registerSakuraBlockItem() {
        SakuraFabric.LOGGER.info("Registering Mod Blockitems for " + SakuraFabric.MODID);
    }
}
