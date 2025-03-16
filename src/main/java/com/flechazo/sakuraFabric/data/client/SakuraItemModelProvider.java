package com.flechazo.sakuraFabric.data.client;

import com.flechazo.sakuraFabric.block.BlockItemRegistry;
import com.flechazo.sakuraFabric.block.machines.StoneMortarBlock;
import com.flechazo.sakuraFabric.data.AbstractItemModelProvider;
import com.flechazo.sakuraFabric.fluid.BucketItemRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.BushBlock;

public class SakuraItemModelProvider extends AbstractItemModelProvider {

    public SakuraItemModelProvider(PackOutput packOutput, String modid, ExistingFileHelper existingFileHelper) {
        super(packOutput, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BlockItemRegistry.ITEMS.getEntries().forEach(item -> {
            if (item.get() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) item.get();
                if (blockItem.getBlock() instanceof StoneMortarBlock)
                    return;
                if (blockItem.getBlock() instanceof BushBlock)
                    bushItem(item);
                else
                    itemBlock(blockItem::getBlock);
            } else {
                normalItem(item);
            }
        });

        BucketItemRegistry.ITEMS.getEntries().forEach((item)->{
            normalItem(item);

        });

    }

}
