package com.flechazo.sakura.core.data;

import com.flechazo.sakura.init.FluidRegistry;
import com.flechazo.sakura.tags.SakuraFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

public class SakuraFluidTagsProvider extends FluidTagsProvider {

    public SakuraFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput,completableFuture);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(FluidTags.WATER).add(FluidRegistry.FOOD_OIL,FluidRegistry.FOOD_OIL_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.DOBUROKU,FluidRegistry.DOBUROKU_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.SAKE,FluidRegistry.SAKE_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.SHOUCHU,FluidRegistry.SHOUCHU_FLOWING);

        tag(FluidTags.WATER).add(FluidRegistry.RED_WINE,FluidRegistry.RED_WINE_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.WHITE_WINE,FluidRegistry.WHITE_WINE_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.WHISKEY,FluidRegistry.WHISKEY_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.BEER,FluidRegistry.BEER_FLOWING);

        tag(FluidTags.WATER).add(FluidRegistry.RUM,FluidRegistry.RUM);
        tag(FluidTags.WATER).add(FluidRegistry.CHAMPAGNE,FluidRegistry.CHAMPAGNE_FLOWING);
        tag(FluidTags.WATER).add(FluidRegistry.BRANDY,FluidRegistry.BRANDY);

        tag(SakuraFluidTags.WATER_WATER).add(Fluids.WATER, Fluids.FLOWING_WATER).addOptional(new ResourceLocation("tfc:river_water"));
        tag(SakuraFluidTags.BREWERS_ALCOHOL)
                .add(FluidRegistry.RUM,FluidRegistry.RUM_FLOWING)
                .add(FluidRegistry.WHISKEY,FluidRegistry.WHISKEY_FLOWING)
                .add(FluidRegistry.SHOUCHU,FluidRegistry.SHOUCHU_FLOWING);
        tag(SakuraFluidTags.FOOD_OIL).addTag(SakuraFluidTags.PLANTOIL);
        tag(SakuraFluidTags.PLANTOIL).add(FluidRegistry.FOOD_OIL,FluidRegistry.FOOD_OIL_FLOWING).addOptional(new ResourceLocation("tfc:flowing_olive_oil")).addOptional(new ResourceLocation("tfc:olive_oil"));
    }
}
