package com.flechazo.sakura.utils;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

@SuppressWarnings("UnstableApiUsage")
public class TransferFluidUtil {

    /**
     * 用空桶从流体槽中提取流体
     *
     * @param player 玩家
     * @param hand   手部
     * @param level  世界
     * @param pos    方块位置
     * @param tank   流体槽
     * @return 是否成功提取
     */
    public static boolean extractFluidWithBucket(Player player, InteractionHand hand, Level level, BlockPos pos, FluidTank tank) {
        if (tank.getFluid().isEmpty() || tank.getFluid().getAmount() < FluidConstants.BUCKET) {
            return false;
        }

        try (Transaction transaction = TransferUtil.getTransaction()) {
            FluidVariant fluidType = tank.getFluid().getType();
            // 将流体从储罐传输到桶
            long extracted = tank.extract(fluidType, FluidConstants.BUCKET, transaction);
            if (extracted == FluidConstants.BUCKET) {
                ItemStack filledBucket = TransferUtil.getFilledBucket(fluidType);
                transaction.commit();

                // 更新玩家手中的物品
                ItemStack currentStack = player.getItemInHand(hand);
                if (!player.getAbilities().instabuild) {
                    currentStack.shrink(1);
                    if (currentStack.isEmpty()) {
                        player.setItemInHand(hand, filledBucket);
                    } else if (!player.getInventory().add(filledBucket)) {
                        player.drop(filledBucket, false);
                    }
                }

                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    /**
     * 从流体容器向流体槽注入流体
     *
     * @param player      玩家
     * @param hand        手部
     * @param level       世界
     * @param pos         方块位置
     * @param tank        流体槽
     * @param itemStorage 物品流体存储
     * @return 是否成功注入
     */
    public static boolean insertFluidFromItem(Player player, InteractionHand hand, Level level, BlockPos pos,
                                              FluidTank tank, Storage<FluidVariant> itemStorage) {
        FluidVariant fluidInItem = null;
        long amountInItem = 0;

        // 获取物品中的流体
        try (Transaction tx = TransferUtil.getTransaction()) {
            for (var view : itemStorage.nonEmptyViews()) {
                fluidInItem = view.getResource();
                amountInItem = view.getAmount();
                break;
            }
        }

        if (fluidInItem == null || amountInItem < FluidConstants.BUCKET) {
            return false;
        }

        // 检查流体槽是否为空或者流体类型相同
        if (!tank.getFluid().isEmpty() && !tank.getFluid().getType().equals(fluidInItem)) {
            return false;
        }

        // 检查流体槽是否有足够空间
        long spaceAvailable = tank.getCapacity() - tank.getAmount();
        if (spaceAvailable < FluidConstants.BUCKET) {
            return false;
        }

        try (Transaction transaction = TransferUtil.getTransaction()) {
            // 将流体从物品传输到储罐
            long inserted = tank.insert(fluidInItem, FluidConstants.BUCKET, transaction);
            if (inserted == FluidConstants.BUCKET) {
                // 从物品中提取流体
                long extracted = itemStorage.extract(fluidInItem, FluidConstants.BUCKET, transaction);
                if (extracted == FluidConstants.BUCKET) {
                    transaction.commit();

                    // 更新玩家手中的物品
                    ItemStack currentStack = player.getItemInHand(hand);
                    if (!player.getAbilities().instabuild) {
                        ItemStack emptyBucket = new ItemStack(Items.BUCKET);
                        currentStack.shrink(1);
                        if (currentStack.isEmpty()) {
                            player.setItemInHand(hand, emptyBucket);
                        } else if (!player.getInventory().add(emptyBucket)) {
                            player.drop(emptyBucket, false);
                        }
                    }

                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 处理单个流体槽的流体交互
     *
     * @param player 玩家
     * @param hand   手部
     * @param level  世界
     * @param pos    方块位置
     * @param tank   流体槽
     * @return 是否成功处理流体交互
     */
    public static boolean handleFluidInteraction(Player player, InteractionHand hand, Level level, BlockPos pos, FluidTank tank) {
        ItemStack stack = player.getItemInHand(hand);
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        Storage<FluidVariant> itemStorage = itemContext.find(FluidStorage.ITEM);

        if (itemStorage == null) {
            return false;
        }

        // 空桶：提取流体
        if (stack.getItem() == Items.BUCKET) {
            return extractFluidWithBucket(player, hand, level, pos, tank);
        }
        // 非空桶：注入流体
        else if (itemStorage.supportsExtraction()) {
            return insertFluidFromItem(player, hand, level, pos, tank, itemStorage);
        }

        return false;
    }

    /**
     * 处理双流体槽的流体交互（输入槽和输出槽）
     * 空桶优先从输出槽提取，再尝试输入槽
     * 非空桶只能向输入槽注入
     *
     * @param player     玩家
     * @param hand       手部
     * @param level      世界
     * @param pos        方块位置
     * @param inputTank  输入流体槽
     * @param outputTank 输出流体槽
     * @return 是否成功处理流体交互
     */
    public static boolean handleFluidInteraction(Player player, InteractionHand hand, Level level, BlockPos pos,
                                                 FluidTank inputTank, FluidTank outputTank) {
        ItemStack stack = player.getItemInHand(hand);
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        Storage<FluidVariant> itemStorage = itemContext.find(FluidStorage.ITEM);

        if (itemStorage == null) {
            return false;
        }

        // 空桶：优先从输出槽提取，再尝试输入槽
        if (stack.getItem() == Items.BUCKET) {
            // 优先尝试输出槽
            if (outputTank != null && !outputTank.getFluid().isEmpty() &&
                    outputTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                if (extractFluidWithBucket(player, hand, level, pos, outputTank)) {
                    return true;
                }
            }

            // 如果输出槽没有液体，尝试输入槽
            if (inputTank != null && !inputTank.getFluid().isEmpty() &&
                    inputTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                return extractFluidWithBucket(player, hand, level, pos, inputTank);
            }
        }
        // 非空桶：只能向输入槽注入
        else if (itemStorage.supportsExtraction() && inputTank != null) {
            return insertFluidFromItem(player, hand, level, pos, inputTank, itemStorage);
        }

        return false;
    }

    /**
     * 检查物品是否为流体容器
     *
     * @param stack 物品堆
     * @return 是否为流体容器
     */
    public static boolean isFluidContainer(ItemStack stack) {
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        Storage<FluidVariant> itemStorage = itemContext.find(FluidStorage.ITEM);
        return itemStorage != null;
    }

    /**
     * 获取物品中的流体信息
     *
     * @param stack 物品堆
     * @return 流体信息，如果没有流体则返回null
     */
    public static FluidInfo getFluidFromItem(ItemStack stack) {
        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        Storage<FluidVariant> itemStorage = itemContext.find(FluidStorage.ITEM);

        if (itemStorage == null) {
            return null;
        }

        try (Transaction tx = TransferUtil.getTransaction()) {
            for (var view : itemStorage.nonEmptyViews()) {
                return new FluidInfo(view.getResource(), view.getAmount());
            }
        }

        return null;
    }

    /**
     * 流体信息记录类
     */
    public record FluidInfo(FluidVariant variant, long amount) {
        public boolean isEmpty() {
            return variant.isBlank() || amount <= 0;
        }

        public boolean hasEnoughForBucket() {
            return amount >= FluidConstants.BUCKET;
        }
    }
}