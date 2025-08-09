package com.flechazo.sakura.block;

import com.flechazo.sakura.init.BlockRegistry;
import io.github.fabricators_of_create.porting_lib.tool.ToolActions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BambooPlant extends Block implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(6D, 0.0D, 6D, 10D, 16.0D, 10D);
    public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
    public static final int MAX_HEIGHT = 16;
    public static final int STAGE_GROWING = 0;
    public static final int STAGE_DONE_GROWING = 1;
    public static final int AGE_THIN_BAMBOO = 0;
    public static final int AGE_THICK_BAMBOO = 1;

    public BambooPlant() {
        super(Properties.copy(Blocks.BAMBOO));
        this.registerDefaultState(this.stateDefinition.any().setValue(LEAVES, BambooLeaves.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEAVES);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0 && level.getRawBrightness(pos.above(), 0) >= 6) {
            growingTick(state, level, pos, random);
            spreadingTick(level, pos, random);
        }
    }

    public void spreadingTick(ServerLevel level, BlockPos pos, RandomSource random) {
        int heightAbove = this.getHeightAboveUpToMax(level, pos) + 1;
        if (heightAbove >= MAX_HEIGHT && (level.isRaining() || random.nextFloat() < 0.15)) {
            growBambooShoot(level, pos, random);
        }
    }

    public void growingTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int heightBelow = this.getHeightBelowUpToMax(level, pos) + 1;
        if (heightBelow < MAX_HEIGHT && level.isEmptyBlock(pos.above())) {
            this.growBamboo(state, level, pos, random, heightBelow);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState ground = worldIn.getBlockState(pos.below());
        return (ground.is(BlockTags.BAMBOO_PLANTABLE_ON) || ground.is(this)) && !(ground.is(Blocks.BAMBOO))
                && !(ground.is(Blocks.BAMBOO_SAPLING));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int heightAbove = this.getHeightAboveUpToMax(level, pos);
        int heightBelow = this.getHeightBelowUpToMax(level, pos);
        int totalHeight = heightAbove + heightBelow + 1;
        int growAttempts = 1 + random.nextInt(2);

        for (int i = 0; i < growAttempts; ++i) {
            BlockPos growPos = pos.above(heightAbove);
            if (totalHeight >= MAX_HEIGHT || !level.isEmptyBlock(growPos.above())) {
                growBambooShoot(level, pos, random);
                return;
            }
            growBamboo(level.getBlockState(growPos), level, growPos, random, totalHeight);
            heightAbove++;
            totalHeight++;
        }
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return player.getMainHandItem().canPerformAction(ToolActions.AXE_DIG) ? 1.0F
                : super.getDestroyProgress(state, player, level, pos);
    }

    public void growBamboo(BlockState state, Level level, BlockPos pos, RandomSource random, int height) {
        BlockState blockStateBelow = level.getBlockState(pos.below());
        BlockPos posBelowTwo = pos.below(2);
        BlockState blockStateBelowTwo = level.getBlockState(posBelowTwo);
        BambooLeaves bambooLeaves = BambooLeaves.NONE;

        if (height >= 1) {
            if (blockStateBelow.is(this) && blockStateBelow.getValue(LEAVES) != BambooLeaves.NONE) {
                bambooLeaves = BambooLeaves.LARGE;
                if (blockStateBelowTwo.is(this)) {
                    level.setBlock(pos.below(), blockStateBelow.setValue(LEAVES, BambooLeaves.SMALL), 3);
                    level.setBlock(posBelowTwo, blockStateBelowTwo.setValue(LEAVES, BambooLeaves.NONE), 3);
                }
            } else {
                bambooLeaves = BambooLeaves.SMALL;
            }
        }

        level.setBlock(pos.above(), this.defaultBlockState().setValue(LEAVES, bambooLeaves), 3);
    }

    public void growBambooShoot(ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos shootPos = pos.offset(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2),
                random.nextInt(3) - 1);
        if (BlockRegistry.BAMBOOSHOOT.defaultBlockState().canSurvive(level, shootPos)
                && level.isEmptyBlock(shootPos.above()) && level.isEmptyBlock(shootPos)) {
            level.setBlockAndUpdate(shootPos, BlockRegistry.BAMBOOSHOOT.defaultBlockState());
        }
    }

    protected int getHeightAboveUpToMax(BlockGetter level, BlockPos pos) {
        int height = 0;
        while (height < MAX_HEIGHT && level.getBlockState(pos.above(height + 1)).is(this)) {
            height++;
        }
        return height;
    }

    protected int getHeightBelowUpToMax(BlockGetter level, BlockPos pos) {
        int height = 0;
        while (height < MAX_HEIGHT && level.getBlockState(pos.below(height + 1)).is(this)) {
            height++;
        }
        return height;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1F;
    }
}