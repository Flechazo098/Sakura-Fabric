package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.block.entity.CookingPotBlockEntity;
import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.tags.SakuraBlockTags;
import com.flechazo.sakura.utils.TransferFluidUtil;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CookingPotBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty TRAY_SUPPORT = BooleanProperty.create("tray_support");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE,
            Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

    public CookingPotBlock() {
        super(Properties.of().noOcclusion().strength(0.5F, 5.0F).sound(SoundType.LANTERN));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(TRAY_SUPPORT, false).setValue(OPEN, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return BlockEntityRegistry.COOKING_POT.create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(TRAY_SUPPORT) ? SHAPE_WITH_TRAY : SHAPE;
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
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (!(blockEntity instanceof CookingPotBlockEntity cookingPot)) {
            return InteractionResult.FAIL;
        }

        boolean fluidSuccess = TransferFluidUtil.handleFluidInteraction(
                player, hand, world, pos, cookingPot.getFluidTank().orElse(null)
        );

        if (fluidSuccess) {
            cookingPot.setChanged();
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        if (stack.isEmpty() && player.isShiftKeyDown()) {
            world.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), 3);
            world.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, cookingPot, pos);
        }

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof CookingPotBlockEntity blockEntity) {
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
        builder.add(FACING, TRAY_SUPPORT, OPEN);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        BlockState belowBlock = world.getBlockState(currentPos.below());
        return state.setValue(TRAY_SUPPORT, belowBlock.is(SakuraBlockTags.TRAY_HEAT_SOURCES));
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.COOKING_POT,
                CookingPotBlockEntity::workingTick);
    }
}