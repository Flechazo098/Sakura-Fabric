package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.block.entity.FermenterBlockEntity;
import com.flechazo.sakura.init.BlockEntityRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class FermenterBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FermenterBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.FERMENTER.create(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        return state;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult result) {
        ItemStack stack = player.getItemInHand(handIn);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof FermenterBlockEntity fermenter)) {
            return InteractionResult.FAIL;
        }

        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
        Storage<FluidVariant> itemStorage = itemContext.find(FluidStorage.ITEM);

        boolean success = false;

        if (itemStorage != null) {
            // 空桶：先尝试从输出槽提取，再尝试从输入槽提取
            if (stack.getItem() == Items.BUCKET) {
                FluidTank outputTank = fermenter.getOutputFluidTank().orElse(null);
                FluidTank inputTank = fermenter.getInputFluidTank().orElse(null);

                // 优先尝试输出槽
                if (!outputTank.getFluid().isEmpty() && outputTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                    success = extractFluidWithBucket(player, handIn, level, pos, outputTank);
                }

                // 如果输出槽没有液体，尝试输入槽
                if (!success && !inputTank.getFluid().isEmpty() && inputTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                    success = extractFluidWithBucket(player, handIn, level, pos, inputTank);
                }
            }
            // 非空桶：尝试向输入槽插入液体
            else if (itemStorage.supportsExtraction()) {
                FluidTank inputTank = fermenter.getInputFluidTank().orElse(null);
                success = insertFluidFromItem(player, handIn, level, pos, inputTank, itemStorage);
            }
        }

        if (!success && !level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, fermenter, pos);
        }

        return InteractionResult.SUCCESS;
    }

    private boolean extractFluidWithBucket(Player player, InteractionHand hand, Level level, BlockPos pos, FluidTank tank) {
        if (tank.getFluid().isEmpty() || tank.getFluid().getAmount() < FluidConstants.BUCKET) return false;

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

    private boolean insertFluidFromItem(Player player, InteractionHand hand, Level level, BlockPos pos,
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

        if (fluidInItem == null || amountInItem < FluidConstants.BUCKET) return false;

        // 检查流体槽是否为空或者流体类型相同
        if (!tank.getFluid().isEmpty() && !tank.getFluid().getType().equals(fluidInItem)) return false;

        // 检查流体槽是否有足够空间
        long spaceAvailable = tank.getCapacity() - tank.getAmount();
        if (spaceAvailable < FluidConstants.BUCKET) return false;

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


    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof FermenterBlockEntity blockEntity) {
                Containers.dropContents(worldIn, pos, blockEntity.getDroppableInventory());
                blockEntity.grantStoredRecipeExperience(worldIn, Vec3.atCenterOf(pos));
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.FERMENTER, FermenterBlockEntity::workingTick);
    }
}