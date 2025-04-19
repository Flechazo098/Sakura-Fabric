package com.flechazo.sakura.block.machines;

import com.flechazo.sakura.init.BlockEntityRegistry;
import com.flechazo.sakura.block.entity.DistillerBlockEntity;
import com.flechazo.sakura.tags.SakuraBlockTags;
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
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
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
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue(TRAY_SUPPORT) ? SHAPE_WITH_TRAY : Shapes.block();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult result) {
        ItemStack stack = player.getItemInHand(handIn);
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (!(blockentity instanceof DistillerBlockEntity distiller)) {
            return InteractionResult.FAIL;
        }

        // 检查是否为桶或流体容器
        boolean isBucket = stack.getItem() == Items.BUCKET;
        boolean isFluidContainer = stack.getItem() instanceof BucketItem;

        if (isBucket || isFluidContainer) {
            // 创建一个 ContainerItemContext 来处理物品和流体的交互
            ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
            FluidBucketWrapper fluidHandler = new FluidBucketWrapper(itemContext);
            boolean success = false;

            // 如果是空桶，尝试从输出槽提取流体
            if (isBucket) {
                // 尝试从输出流体槽中提取流体到物品
                if (distiller.getOutputFluidTank().isPresent()) {
                    FluidTank outTank = distiller.getOutputFluidTank().orElse(null);
                    if (outTank != null && !outTank.getFluid().isEmpty() && outTank.getFluid().getAmount() >= FluidConstants.BUCKET) {
                        success = TransferFluidUtil.tryTransferFluid(player, handIn, outTank, fluidHandler);
                        if (success) {
                            // 播放流体提取声音
                            level.playSound(null, pos, SoundEvents.BUCKET_FILL,
                                    SoundSource.BLOCKS, 1.0F, 1.0F);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

                // 如果输出槽为空或不足一桶，尝试从输入槽提取
                if (!success && distiller.getInputFluidTank().isPresent()) {
                    FluidTank inTank = distiller.getInputFluidTank().orElse(null);
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
            }
            // 如果是装有液体的桶，与输入槽交互
            else if (isFluidContainer && !fluidHandler.getFluid().isEmpty()) {
                if (distiller.getInputFluidTank().isPresent()) {
                    FluidTank inTank = distiller.getInputFluidTank().orElse(null);
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

        // 如果不是流体交互，则打开GUI
        if (!level.isClientSide()) {
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.DISTILLER, DistillerBlockEntity::workingTick);
    }
}