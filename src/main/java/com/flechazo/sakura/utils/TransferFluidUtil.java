package com.flechazo.sakura.utils;

import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.item.FluidBucketWrapper;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TransferFluidUtil {
    /**
     * 尝试在流体槽和物品之间传输流体
     *
     * @param player  玩家
     * @param hand    交互的手
     * @param tank    流体槽
     * @param handler 流体容器包装器
     * @return 是否成功传输流体
     */
    public static boolean tryTransferFluid (Player player, InteractionHand hand, FluidTank tank, FluidBucketWrapper handler) {
        return tryTransferFluid(player, hand, tank, handler, 8);
    }

    /**
     * 尝试在流体槽和物品之间传输流体，可指定分段数
     *
     * @param player   玩家
     * @param hand     交互的手
     * @param tank     流体槽
     * @param handler  流体容器包装器
     * @param segments 将容器容量分成几份
     * @return 是否成功传输流体
     */
    public static boolean tryTransferFluid (Player player, InteractionHand hand, FluidTank tank, FluidBucketWrapper handler, int segments) {
        ItemStack originalStack = player.getItemInHand(hand);
        boolean success = false;

        // 检查物品栈是否为空
        if (originalStack.isEmpty()) {
            return false;
        }

        // 尝试从物品中提取流体到流体槽
        if (! handler.getFluid().isEmpty()) {
            try (Transaction transaction = Transaction.openOuter()) {
                FluidStack fluidInItem = new FluidStack(handler.getResource(), handler.getAmount());

                // 检查流体槽是否可以接受这种流体
                if (tank.getFluid().isEmpty() || tank.getFluid().isFluidEqual(fluidInItem)) {
                    // 计算每次传输量 - 将槽容量分成指定份数
                    long transferAmount = tank.getCapacity() / segments;

                    // 确保不超过物品中的流体量
                    transferAmount = Math.min(transferAmount, handler.getAmount());

                    // 确保不超过槽的剩余容量
                    transferAmount = Math.min(transferAmount, tank.getCapacity() - tank.getAmount());

                    if (transferAmount > 0) {
                        // 使用insert方法将流体插入到流体槽中
                        long inserted = tank.insert(fluidInItem.getType(), transferAmount, transaction);
                        if (inserted > 0) {
                            // 如果成功插入流体，则提取物品中的流体
                            handler.extract(handler.getResource(), inserted, transaction);
                            transaction.commit();

                            // 更新玩家手中的物品 - 使用空桶替换
                            if (! player.getAbilities().instabuild) {
                                player.setItemInHand(hand, new ItemStack(net.minecraft.world.item.Items.BUCKET));
                            }

                            success = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        // 尝试从流体槽中提取流体到物品
        else if (! tank.getFluid().isEmpty()) {
            try (Transaction transaction = Transaction.openOuter()) {
                FluidStack tankFluid = tank.getFluid();

                // 检查物品是否可以接受这种流体类型
                if (handler.canFillFluidType(tankFluid.getType(), FluidConstants.BUCKET)) {
                    // 检查流体槽中是否有足够的流体填满一个桶
                    if (tankFluid.getAmount() >= FluidConstants.BUCKET) {
                        // 尝试将流体插入到物品中
                        long extracted = handler.insert(tankFluid.getType(), FluidConstants.BUCKET, transaction);
                        if (extracted > 0) {
                            // 如果成功插入流体到物品，则从流体槽中提取流体
                            tank.extract(tankFluid.getType(), extracted, transaction);
                            transaction.commit();

                            // 更新玩家手中的物品 - 使用装满的桶替换
                            if (! player.getAbilities().instabuild) {
                                // 使用 FluidBucketWrapper 的 getFilledBucket 方法获取装满的桶
                                ItemStack filledBucket = handler.getFilledBucket(tankFluid);
                                player.setItemInHand(hand, filledBucket);
                            }

                            success = true;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return success;
    }
}