package com.flechazo.sakuraFabric.tags;

import com.flechazo.sakuraFabric.SakuraFabric;
import com.flechazo.sakuraFabric.utils.TagUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class SakuraFluidTags {
    public static final TagKey<Fluid> WATER_WATER = TagUtils.forgeFluidTag("water/water");
    public static final TagKey<Fluid> FOOD_OIL = TagUtils.forgeFluidTag("food_oil");
    public static final TagKey<Fluid> PLANTOIL = TagUtils.forgeFluidTag("plantoil");
    public static final TagKey<Fluid> SOYSAUCE = TagUtils.forgeFluidTag("soysauce");

    public static final TagKey<Fluid> BREWERS_ALCOHOL = TagUtils.modFluidTag(SakuraFabric.MODID, "brewers_alcohol");
}
