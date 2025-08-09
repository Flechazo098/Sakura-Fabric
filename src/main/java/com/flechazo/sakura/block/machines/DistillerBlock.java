package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.block.entity.DistillerBlockEntity;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.tags.SakuraBlockTags;
import com.flechazo.sakura.utils.TransferFluidUtil;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DistillerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRAY_SUPPORT = BooleanProperty.create("tray_support");
    protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(Shapes.block(),
            Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

    public DistillerBlock() {
        super(Properties.copy(Blocks.OAK_PLANKS));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRAY_SUPPORT, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.DISTILLER.create(pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState belowBlock = world.getBlockState(pos.below());
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());

        return state.setValue(TRAY_SUPPORT, belowBlock.is(SakuraBlockTags.TRAY_HEAT_SOURCES));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult result) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof DistillerBlockEntity distiller)) {
            return InteractionResult.FAIL;
        }

        boolean success = TransferFluidUtil.handleFluidInteraction(
                player, handIn, level, pos,
                distiller.getInputFluidTank().orElse(null),
                distiller.getOutputFluidTank().orElse(null)
        );

        if (!success && !level.isClientSide()) {
            NetworkHooks.openScreen((ServerPlayer) player, distiller, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof DistillerBlockEntity blockEntity) {
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
        builder.add(FACING, TRAY_SUPPORT);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.DISTILLER, DistillerBlockEntity::workingTick);
    }
}