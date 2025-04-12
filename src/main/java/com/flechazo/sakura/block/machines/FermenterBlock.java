package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.block.entity.FermenterBlockEntity;
import com.flechazo.sakura.utils.TransferFluidUtil;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.item.FluidBucketWrapper;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
            // 先尝试与输出流体槽交互
            if (fermenter.getOutputFluidTank().isPresent()) {
                FluidTank outTank = fermenter.getOutputFluidTank().orElse(null);
                if (outTank != null) {
                    // 尝试从流体槽中提取流体到物品
                    boolean success = TransferFluidUtil.tryTransferFluid(player, handIn, outTank, fluidHandler);
                    if (success) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            // 再尝试与输入流体槽交互
            if (fermenter.getInputFluidTank().isPresent()) {
                FluidTank inTank = fermenter.getInputFluidTank().orElse(null);
                if (inTank != null) {
                    boolean success = TransferFluidUtil.tryTransferFluid(player, handIn, inTank, fluidHandler);
                    if (success) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.FERMENTER, FermenterBlockEntity::workingTick);
    }
}