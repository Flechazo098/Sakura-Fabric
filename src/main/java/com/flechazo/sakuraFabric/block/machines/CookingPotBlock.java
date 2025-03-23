package com.flechazo.sakuraFabric.block.machines;

import com.flechazo.sakuraFabric.block.entity.BlockEntityRegistry;
import com.flechazo.sakuraFabric.block.entity.CookingPotBlockEntity;
import com.flechazo.sakuraFabric.block.entity.DistillerBlockEntity;
import com.flechazo.sakuraFabric.tags.SakuraBlockTags;
import com.flechazo.sakuraFabric.utils.FluidAction;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.item.FluidBucketWrapper;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import io.github.fabricators_of_create.porting_lib.util.FluidUtil;
import io.github.fabricators_of_create.porting_lib.util.NetworkHooks;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
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
import net.minecraft.world.level.material.Fluids;
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn,
                                 BlockHitResult result) {
        ItemStack stack = player.getItemInHand(handIn);
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (!(blockentity instanceof DistillerBlockEntity distiller)) {
            return InteractionResult.FAIL;
        }

        // 简化的流体处理逻辑
        boolean handled = handleFluidInteraction(player, handIn, stack, distiller);
        if (handled) {
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {
            // 使用 Fabric API 打开界面
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.openMenu((ExtendedScreenHandlerFactory)distiller);
            }
        }
        return InteractionResult.SUCCESS;
    }

    // 修改流体交互处理方法
    private boolean handleFluidInteraction(Player player, InteractionHand hand, ItemStack stack, CookingPotBlockEntity cookingPot) {
        // 检查是否是桶或瓶子等流体容器
        if (stack.getItem() == Items.BUCKET ||
                stack.getItem() == Items.WATER_BUCKET ||
                stack.getItem() == Items.LAVA_BUCKET ||
                stack.getItem() == Items.GLASS_BOTTLE) {

            // 尝试与流体槽交互
            if (cookingPot.getFluidTank().isPresent()) {
                return tryTransferFluid(player, hand, stack, cookingPot.getFluidTank().orElse(null));
            }
        }
        return false;
    }

    // 实现流体转移方法
    private boolean tryTransferFluid(Player player, InteractionHand hand, ItemStack stack, FluidTank fluidTank) {
        if (fluidTank == null) return false;

        // 使用 PortingLib 的 FluidUtil 处理流体交互
        boolean success = false;

        // 从物品中提取流体到储罐
        if (stack.getItem() == Items.WATER_BUCKET) {
            // 水桶处理
            if (fluidTank.isEmpty() || (fluidTank.getFluid().getFluid() == Fluids.WATER && fluidTank.getSpace() >= 1000)) {
                fluidTank.fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                success = true;
            }
        } else if (stack.getItem() == Items.BUCKET && !fluidTank.isEmpty() && fluidTank.getFluidAmount() >= 1000) {
            // 空桶装满
            FluidStack fluidStack = fluidTank.getFluid();
            if (fluidStack.getFluid() == Fluids.WATER) {
                fluidTank.drain(1000, FluidAction.EXECUTE);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    ItemStack filledBucket = new ItemStack(Items.WATER_BUCKET);
                    if (stack.isEmpty()) {
                        player.setItemInHand(hand, filledBucket);
                    } else if (!player.getInventory().add(filledBucket)) {
                        player.drop(filledBucket, false);
                    }
                }
                success = true;
            }
        }

        return success;
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
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world,
                                  BlockPos currentPos, BlockPos facingPos) {
        BlockState belowBlock = world.getBlockState(currentPos.below());
        return state.setValue(TRAY_SUPPORT, belowBlock.is(SakuraBlockTags.TRAY_HEAT_SOURCES));
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> blockEntity) {
        return createTickerHelper(blockEntity, BlockEntityRegistry.COOKING_POT,
                CookingPotBlockEntity::workingTick);
    }
}