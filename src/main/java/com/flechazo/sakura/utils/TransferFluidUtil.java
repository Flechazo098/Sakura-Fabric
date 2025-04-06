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
     * @param player 玩家
     * @param hand 交互的手
     * @param tank 流体槽
     * @param handler 流体容器包装器
     * @return 是否成功传输流体
     */
    public static boolean tryTransferFluid(Player player, InteractionHand hand, FluidTank tank, FluidBucketWrapper handler) {
        ItemStack originalStack = player.getItemInHand(hand);
        boolean success = false;

        // 检查物品栈是否为空
        if (originalStack.isEmpty()) {
            return false;
        }

        // 尝试从物品中提取流体到流体槽
        if (!handler.getFluid().isEmpty()) {
            try (Transaction transaction = Transaction.openOuter()) {
                long inserted = tank.insert(handler.getResource(), handler.getAmount(), transaction);
                if (inserted > 0) {
                    // 如果成功插入流体，则提取物品中的流体
                    handler.extract(handler.getResource(), inserted, transaction);
                    transaction.commit();
                    success = true;
                }
            }
        }
        // 尝试从流体槽中提取流体到物品
        else if (!tank.getFluid().isEmpty()) {
            try (Transaction transaction = Transaction.openOuter()) {
                FluidStack tankFluid = tank.getFluid();
                if (handler.canFillFluidType(tankFluid.getType(), FluidConstants.BUCKET)) {
                    long extracted = handler.insert(tankFluid.getType(), Math.min(tankFluid.getAmount(), FluidConstants.BUCKET), transaction);
                    if (extracted > 0) {
                        // 如果成功插入流体到物品，则从流体槽中提取流体
                        tank.extract(tankFluid.getType(), extracted, transaction);
                        transaction.commit();
                        success = true;
                    }
                }
            } catch (Exception e) {
                // 捕获可能的异常，防止游戏崩溃
                return false;
            }
        }

        // 如果操作成功，检查物品是否已经被更新
        if (success) {
            // 流体交互后，物品已经被自动更新
            ItemStack newStack = player.getItemInHand(hand);
            if (!ItemStack.matches(originalStack, newStack)) {
                return true;
            }
        }

        return false;
    }
}