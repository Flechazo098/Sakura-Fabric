package com.flechazo.sakura.core.data.client;

import com.flechazo.sakura.block.BlockItemRegistry;
import com.flechazo.sakura.block.machines.StoneMortarBlock;
import com.flechazo.sakura.core.data.AbstractItemModelProvider;
import com.flechazo.sakura.fluid.BucketItemRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BushBlock;

public class SakuraItemModelProvider extends AbstractItemModelProvider {

    public SakuraItemModelProvider(PackOutput packOutput, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item item : BlockItemRegistry.getEntries()) {
            if (item instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof StoneMortarBlock)
                    continue;
                if (blockItem.getBlock() instanceof BushBlock)
                    bushItem(() -> item);
                else
                    itemBlock(blockItem::getBlock);
            } else {
                normalItem(() -> item);
            }
        }

        for (Item item : BucketItemRegistry.getEntries()) {
            normalItem(() -> item);
        }
    }
}