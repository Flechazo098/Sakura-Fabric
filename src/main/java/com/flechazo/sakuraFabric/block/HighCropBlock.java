package com.flechazo.sakuraFabric.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class HighCropBlock extends BaseCropBlock {
    public static final BooleanProperty UPPER = BooleanProperty.create("upper");
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)4.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)8.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)12.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F)};

    public HighCropBlock(BlockBehaviour.Properties proper, Supplier<? extends ItemLike> seed) {
        super(proper, seed);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(UPPER, false));
    }

    public BooleanProperty getUpperProperty() {
        return UPPER;
    }

    public int getGrowUpperAge() {
        return 3;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE, UPPER});
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[(Integer)state.getValue(this.getAgeProperty())];
    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos downpos = pos.below();
        if (!worldIn.getBlockState(downpos).is(this)) {
            return super.canSurvive(state, worldIn, pos);
        } else {
            return !(Boolean)worldIn.getBlockState(downpos).getValue(this.getUpperProperty()) && (worldIn.getRawBrightness(pos, 0) >= 8 || worldIn.canSeeSky(pos)) && this.getAge(worldIn.getBlockState(downpos)) >= this.getGrowUpperAge();
        }
    }

    public BlockState getStateForAge(int age) {
        return super.getStateForAge(age);
    }

    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            float f = getGrowthSpeed(this, worldIn, pos);
            int age = this.getAge(state);
            if (worldIn.getRawBrightness(pos, 0) >= 9 && age < this.getMaxAge() && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
                worldIn.setBlock(pos, (BlockState)this.getStateForAge(age + 1).setValue(this.getUpperProperty(), (Boolean)state.getValue(this.getUpperProperty())), 2);
                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }

            if (!(Boolean)state.getValue(this.getUpperProperty())) {
                if (age >= this.getGrowUpperAge() && ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0) && this.defaultBlockState().canSurvive(worldIn, pos.above()) && worldIn.isEmptyBlock(pos.above())) {
                    worldIn.setBlockAndUpdate(pos.above(), (BlockState)this.defaultBlockState().setValue(this.getUpperProperty(), true));
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }

            }
        }
    }

    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upperState = worldIn.getBlockState(pos.above());
        if (upperState.is(this)) {
            return !this.isMaxAge(upperState);
        } else if ((Boolean)state.getValue(this.getUpperProperty())) {
            return !this.isMaxAge(state);
        } else {
            return true;
        }
    }

    public boolean isBonemealSuccess(Level worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(worldIn), 15);
        if (ageGrowth <= this.getMaxAge()) {
            worldIn.setBlockAndUpdate(pos, (BlockState)state.setValue(AGE, ageGrowth));
        } else {
            worldIn.setBlockAndUpdate(pos, (BlockState)state.setValue(AGE, this.getMaxAge()));
            if ((Boolean)state.getValue(this.getUpperProperty())) {
                return;
            }

            BlockState top = worldIn.getBlockState(pos.above());
            if (top.is(this)) {
                BonemealableBlock growable = (BonemealableBlock)worldIn.getBlockState(pos.above()).getBlock();
                if (growable.isValidBonemealTarget(worldIn, pos.above(), top, false)) {
                    growable.performBonemeal(worldIn, worldIn.random, pos.above(), top);
                }
            } else {
                int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
                if (this.defaultBlockState().canSurvive(worldIn, pos.above()) && worldIn.isEmptyBlock(pos.above())) {
                    worldIn.setBlock(pos.above(), (BlockState)((BlockState)this.defaultBlockState().setValue(this.getUpperProperty(), true)).setValue(this.getAgeProperty(), remainingGrowth), 3);
                }
            }
        }

    }
}