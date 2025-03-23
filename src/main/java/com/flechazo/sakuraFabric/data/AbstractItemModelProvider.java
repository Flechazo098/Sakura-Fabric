package com.flechazo.sakuraFabric.data;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public abstract class AbstractItemModelProvider extends ItemModelProvider {

    public AbstractItemModelProvider(PackOutput generator, String modid, ExistingFileHelper existingFileHelper) {
        super(generator, modid, existingFileHelper);
    }

    public String blockName(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }

    protected ResourceLocation blockTexture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation itemTexture(String name) {
        return modLoc("item/" + name);
    }

    public ItemModelBuilder itemFence(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), mcLoc("block/fence_inventory")).texture("texture",
                ("block/" + name));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
        return itemBlock(block, blockName(block));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block, String model) {
        return withExistingParent(blockName(block), modLoc("block/" + model));
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block) {
        return withExistingParent(blockName(block), mcLoc("item/generated")).texture("layer0",
                modLoc("block/" + blockName(block)));
    }

    public ItemModelBuilder normalItem(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/generated")).texture("layer0",
                modLoc("item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder bushItem(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/generated")).texture("layer0",
                modLoc("block/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder torchItem(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/generated")).texture("layer0",
                modLoc("block/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder toolItem(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/handheld")).texture("layer0",
                modLoc("item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    public ItemModelBuilder egg(Supplier<? extends Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }
}
