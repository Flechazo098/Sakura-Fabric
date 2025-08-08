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
import net.minecraft.sounds.SoundEvent;
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
import java.util.Optional;

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
        FluidBucketWrapper fluidHandler = new FluidBucketWrapper(itemContext);


        boolean success;

        if (fluidHandler.getFluid().isEmpty()) {
            // 空桶：先尝试从输出槽提取，再尝试从输入槽提取
            success = fermenter.getOutputFluidTank().map(tank ->
                            tryExtractFluid(player, handIn, level, pos, tank, SoundEvents.BUCKET_FILL))
                    .orElse(false)
                    || fermenter.getInputFluidTank().map(tank ->
                            tryExtractFluid(player, handIn, level, pos, tank, SoundEvents.BUCKET_FILL))
                    .orElse(false);
        } else {
            // 非空桶：尝试向输入槽插入液体
            success = fermenter.getInputFluidTank().map(tank ->
                            tryInsertFluid(player, handIn, level, pos, fluidHandler, tank, SoundEvents.BUCKET_EMPTY))
                    .orElse(false);
        }

        if (success) {
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, fermenter, pos);
        }

        return InteractionResult.SUCCESS;
    }

    private boolean tryExtractFluid(Player player, InteractionHand hand, Level level, BlockPos pos,
                                    FluidTank tank, SoundEvent sound) {
        if (tank.getFluid().isEmpty()) return false;

        boolean success = TransferFluidUtil.tryTransferFluid(
                player, hand, tank, new FluidBucketWrapper(ContainerItemContext.withConstant(player.getItemInHand(hand)))
        );
        if (success) {
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return success;
    }

    private boolean tryInsertFluid(Player player, InteractionHand hand, Level level, BlockPos pos,
                                   FluidBucketWrapper handler, FluidTank tank, SoundEvent sound) {
        FluidStack toInsert = new FluidStack(handler.getResource(), handler.getAmount());

        if (!tank.getFluid().isEmpty() && !tank.getFluid().isFluidEqual(toInsert)) return false;

        boolean success = TransferFluidUtil.tryTransferFluid(player, hand, tank, handler);
        if (success) {
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
        return success;
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