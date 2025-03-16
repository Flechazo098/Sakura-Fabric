package com.flechazo.sakuraFabric.item;

import com.flechazo.sakuraFabric.item.info.FoodInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class ItemFoodBase extends Item implements IFoodLike {
    private final FoodInfo info;

    public ItemFoodBase(Item.Properties prop, FoodInfo info) {
        super(prop);
        this.info = info;
    }

    public boolean isEdible() {
        return this.info != null;
    }

    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltips, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltips, flag);
        if (this.shouldAddEffectTooltips()) {
            if (!this.getFoodInfo().getEffects().isEmpty()) {
                this.addEffectTooltips(tooltips);
            }

        }
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entity);
        if (stack.getCount() > 0) {
            if (entity instanceof Player entityplayer) {
                if (entityplayer.getAbilities().instabuild) {
                    return itemstack;
                }

                if (!entityplayer.addItem(this.getCraftingRemainingItem(stack))) {
                    entityplayer.drop(this.getCraftingRemainingItem(stack), true);
                }
            }

            return itemstack;
        } else {
            return entity instanceof Player && ((Player)entity).getAbilities().instabuild ? itemstack : this.getCraftingRemainingItem(stack);
        }
    }

    public SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }

    public SoundEvent getEatingSound() {
        return super.getEatingSound();
    }

    public FoodProperties getFoodProperties() {
        FoodProperties.Builder food = (new FoodProperties.Builder()).nutrition(this.getFoodInfo().getAmount()).saturationMod(this.getFoodInfo().getCalories());
        if (this.getFoodInfo().isAlwaysEat()) {
            food.alwaysEat();
        }

        if (this.getFoodInfo().getEatTime() <= 16) {
            food.fast();
        }

        this.getFoodInfo().getEffects().forEach((k) -> food.effect((Supplier)k.getFirst(), (Float)k.getSecond()));
        return food.build();
    }

    public int getUseDuration(ItemStack stack) {
        return this.getFoodInfo() != null ? this.getFoodInfo().getEatTime() : super.getUseDuration(stack);
    }

    public FoodInfo getFoodInfo() {
        return this.info;
    }

    public boolean shouldAddEffectTooltips() {
        return this.info != null;
    }
}
