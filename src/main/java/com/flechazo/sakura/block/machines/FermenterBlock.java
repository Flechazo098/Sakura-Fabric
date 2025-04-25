package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.block.entity.FermenterBlockEntity;
import com.flechazo.sakura.utils.TransferFluidUtil;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.item.FluidBucketWrapper;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
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
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (!(blockentity instanceof FermenterBlockEntity fermenter)) {
            return InteractionResult.FAIL;
        }

        ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);

        // 尝试获取流体处理器
        FluidBucketWrapper fluidHandler = new FluidBucketWrapper(itemContext);

        // 检查是否为流体容器
        if (fluidHandler != null) {
            boolean success = false;

            // 先检查是否是空桶，如果是空桶则优先从输出槽提取
            if (fluidHandler.getFluid().isEmpty()) {
                // 尝试从输出流体槽中提取流体到物品
                if (fermenter.getOutputFluidTank().isPresent()) {
                    FluidTank outTank = fermenter.getOutputFluidTank().orElse(null);
                    // 只需要检查流体是否存在，不需要检查是否达到一桶的量，因为TransferFluidUtil会处理
                    if (outTank != null && !outTank.getFluid().isEmpty()) {
                        success = TransferFluidUtil.tryTransferFluid(player, handIn, outTank, fluidHandler);
                        if (success) {
                            // 播放流体提取声音
                            level.playSound(null, pos, SoundEvents.BUCKET_FILL,
                                    SoundSource.BLOCKS, 1.0F, 1.0F);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

                // 如果输出槽为空或流体不足，尝试从输入槽提取
                if (!success && fermenter.getInputFluidTank().isPresent()) {
                    FluidTank inTank = fermenter.getInputFluidTank().orElse(null);
                    if (inTank != null && !inTank.getFluid().isEmpty() && inTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                        success = TransferFluidUtil.tryTransferFluid(player, handIn, inTank, fluidHandler);
                        if (success) {
                            // 播放流体提取声音
                            level.playSound(null, pos, SoundEvents.BUCKET_FILL,
                                    SoundSource.BLOCKS, 1.0F, 1.0F);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            } else {
                // 如果是装有液体的桶，与输入槽交互
                if (fermenter.getInputFluidTank().isPresent()) {
                    FluidTank inTank = fermenter.getInputFluidTank().orElse(null);
                    if (inTank != null) {
                        // 检查流体槽是否可以接受这种流体
                        FluidStack fluidInItem = new FluidStack(fluidHandler.getResource(), fluidHandler.getAmount());
                        if (inTank.getFluid().isEmpty() || inTank.getFluid().isFluidEqual(fluidInItem)) {
                            success = TransferFluidUtil.tryTransferFluid(player, handIn, inTank, fluidHandler);
                            if (success) {
                                // 播放流体注入声音
                                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY,
                                        SoundSource.BLOCKS, 1.0F, 1.0F);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
        }

        // 如果没有进行流体交互，则打开GUI
        if (!level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, fermenter, pos);
        }
        return InteractionResult.SUCCESS;
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