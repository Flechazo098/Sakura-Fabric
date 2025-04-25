package com.flechazo.sakura.block;

import com.flechazo.sakura.init.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BambooShoot extends BushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(6D, 0.0D, 6D, 10D, 4.0D, 10D);
    private static final int GROWTH_LIGHT_LEVEL = 6;
    private static final float GROWTH_CHANCE = 0.33F; // 1/3的生长概率

    public BambooShoot() {
        super(Properties.copy(Blocks.BAMBOO_SAPLING));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (!level.isAreaLoaded(pos, 1)) {
            return;
        }

        // 检查生长条件
        if (canGrow(level, pos) && random.nextFloat() < GROWTH_CHANCE) {
            growBamboo(level, pos);
        }
    }

    private boolean canGrow(ServerLevel level, BlockPos pos) {
        return level.getRawBrightness(pos.above(), 0) > GROWTH_LIGHT_LEVEL
                && level.getBrightness(LightLayer.BLOCK, pos) > 0;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState ground = level.getBlockState(pos.below());
        return ground.is(BlockTags.BAMBOO_PLANTABLE_ON)
                && !ground.is(Blocks.BAMBOO)
                && !ground.is(Blocks.BAMBOO_SAPLING)
                && !ground.is(BlockRegistry.BAMBOO_PLANT)
                && !ground.is(this);
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
        growBamboo(level, pos);
    }

    private void growBamboo(ServerLevel level, BlockPos pos) {
        // 检查生长空间
        if (!level.isEmptyBlock(pos.above())) {
            return;
        }

        // 生长竹子
        if (level.isEmptyBlock(pos.above(2))) {
            // 如果有两格空间，生长带大叶子的竹子
            level.setBlockAndUpdate(pos.above(2),
                    BlockRegistry.BAMBOO_PLANT.defaultBlockState()
                            .setValue(BambooStalkBlock.LEAVES, BambooLeaves.LARGE));
        }

        // 设置中间和底部的竹子方块
        level.setBlockAndUpdate(pos.above(),
                BlockRegistry.BAMBOO_PLANT.defaultBlockState()
                        .setValue(BambooStalkBlock.LEAVES, BambooLeaves.SMALL));
        level.setBlockAndUpdate(pos,
                BlockRegistry.BAMBOO_PLANT.defaultBlockState());
    }
}