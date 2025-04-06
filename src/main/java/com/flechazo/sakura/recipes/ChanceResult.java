package com.flechazo.sakura.recipes;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public record ChanceResult(ItemStack stack, float chance) {
    public static final ChanceResult EMPTY;

    public ChanceResult(ItemStack stack, float chance) {
        this.stack = stack;
        this.chance = chance;
    }

    public ItemStack rollOutput(RandomSource rand, int fortuneLevel) {
        int outputAmount = this.stack.getCount();
        double fortuneBonus = (double)fortuneLevel;

        for(int roll = 0; roll < this.stack.getCount(); ++roll) {
            if ((double)rand.nextFloat() > (double)this.chance + fortuneBonus) {
                --outputAmount;
            }
        }

        if (outputAmount == 0) {
            return ItemStack.EMPTY;
        } else {
            ItemStack out = this.stack.copy();
            out.setCount(outputAmount);
            return out;
        }
    }

    public ItemStack stack() {
        return this.stack;
    }

    public float chance() {
        return this.chance;
    }

    static {
        EMPTY = new ChanceResult(ItemStack.EMPTY, 1.0F);
    }
}
