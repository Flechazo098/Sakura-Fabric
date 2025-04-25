package com.flechazo.sakura.init;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.init.fluid.FluidBlockRegistry;
import com.flechazo.sakura.init.fluid.FluidTypeRegistry;
import com.flechazo.sakura.utils.FluidExtensionProvider;
import com.flechazo.sakura.utils.IClientFluidTypeExtensions;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.extensions.ConvertToSourceFluid;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FluidRegistry {

    // 存储所有流体方块的集合
    public static final List<LiquidBlock> FLUIDS = new ArrayList<>();


    public static FlowingFluid FOOD_OIL;
    public static FlowingFluid FOOD_OIL_FLOWING;
    public static FlowingFluid DOBUROKU;
    public static FlowingFluid DOBUROKU_FLOWING;
    public static FlowingFluid SAKE;
    public static FlowingFluid SAKE_FLOWING;
    public static FlowingFluid SHOUCHU;
    public static FlowingFluid SHOUCHU_FLOWING;
    public static FlowingFluid BEER;
    public static FlowingFluid BEER_FLOWING;
    public static FlowingFluid WHISKEY;
    public static FlowingFluid WHISKEY_FLOWING;
    public static FlowingFluid RUM;
    public static FlowingFluid RUM_FLOWING;
    public static FlowingFluid RED_WINE;
    public static FlowingFluid RED_WINE_FLOWING;
    public static FlowingFluid WHITE_WINE;
    public static FlowingFluid WHITE_WINE_FLOWING;
    public static FlowingFluid CHAMPAGNE;
    public static FlowingFluid CHAMPAGNE_FLOWING;
    public static FlowingFluid BRANDY;
    public static FlowingFluid BRANDY_FLOWING;

    public static void initialize() {
        // FOOD_OIL
        CustomSourceFluid foodOilSource = new CustomSourceFluid(FluidTypeRegistry.FOOD_OIL,
                () -> FluidBlockRegistry.FOOD_OIL_BLOCK);
        FOOD_OIL = registerFluid("food_oil", foodOilSource);

        CustomFlowingFluid foodOilFlowing = new CustomFlowingFluid(FluidTypeRegistry.FOOD_OIL,
                () -> FOOD_OIL, () -> FluidBlockRegistry.FOOD_OIL_BLOCK);
        FOOD_OIL_FLOWING = registerFluid("food_oil_flowing", foodOilFlowing);

        foodOilSource.setFlowing(FOOD_OIL_FLOWING);

        // DOBUROKU
        CustomSourceFluid doburokuSource = new CustomSourceFluid(FluidTypeRegistry.DOBUROKU,
                () -> FluidBlockRegistry.DOBUROKU_BLOCK);
        DOBUROKU = registerFluid("doburoku", doburokuSource);

        CustomFlowingFluid doburokuFlowing = new CustomFlowingFluid(FluidTypeRegistry.DOBUROKU,
                () -> DOBUROKU, () -> FluidBlockRegistry.DOBUROKU_BLOCK);
        DOBUROKU_FLOWING = registerFluid("doburoku_flowing", doburokuFlowing);

        doburokuSource.setFlowing(DOBUROKU_FLOWING);

        // SAKE
        CustomSourceFluid sakeSource = new CustomSourceFluid(FluidTypeRegistry.SAKE,
                () -> FluidBlockRegistry.SAKE_BLOCK);
        SAKE = registerFluid("sake", sakeSource);

        CustomFlowingFluid sakeFlowing = new CustomFlowingFluid(FluidTypeRegistry.SAKE,
                () -> SAKE, () -> FluidBlockRegistry.SAKE_BLOCK);
        SAKE_FLOWING = registerFluid("sake_flowing", sakeFlowing);

        sakeSource.setFlowing(SAKE_FLOWING);

        // SHOUCHU
        CustomSourceFluid shouchuSource = new CustomSourceFluid(FluidTypeRegistry.SHOUCHU,
                () -> FluidBlockRegistry.SHOUCHU_BLOCK);
        SHOUCHU = registerFluid("shouchu", shouchuSource);

        CustomFlowingFluid shouchuFlowing = new CustomFlowingFluid(FluidTypeRegistry.SHOUCHU,
                () -> SHOUCHU, () -> FluidBlockRegistry.SHOUCHU_BLOCK);
        SHOUCHU_FLOWING = registerFluid("shouchu_flowing", shouchuFlowing);

        shouchuSource.setFlowing(SHOUCHU_FLOWING);

        // BEER
        CustomSourceFluid beerSource = new CustomSourceFluid(FluidTypeRegistry.BEER,
                () -> FluidBlockRegistry.BEER_BLOCK);
        BEER = registerFluid("beer", beerSource);

        CustomFlowingFluid beerFlowing = new CustomFlowingFluid(FluidTypeRegistry.BEER,
                () -> BEER, () -> FluidBlockRegistry.BEER_BLOCK);
        BEER_FLOWING = registerFluid("beer_flowing", beerFlowing);

        beerSource.setFlowing(BEER_FLOWING);

        // WHISKEY
        CustomSourceFluid whiskeySource = new CustomSourceFluid(FluidTypeRegistry.WHISKEY,
                () -> FluidBlockRegistry.WHISKEY_BLOCK);
        WHISKEY = registerFluid("whiskey", whiskeySource);

        CustomFlowingFluid whiskeyFlowing = new CustomFlowingFluid(FluidTypeRegistry.WHISKEY,
                () -> WHISKEY, () -> FluidBlockRegistry.WHISKEY_BLOCK);
        WHISKEY_FLOWING = registerFluid("whiskey_flowing", whiskeyFlowing);

        whiskeySource.setFlowing(WHISKEY_FLOWING);

        // RUM
        CustomSourceFluid rumSource = new CustomSourceFluid(FluidTypeRegistry.RUM,
                () -> FluidBlockRegistry.RUM_BLOCK);
        RUM = registerFluid("rum", rumSource);

        CustomFlowingFluid rumFlowing = new CustomFlowingFluid(FluidTypeRegistry.RUM,
                () -> RUM, () -> FluidBlockRegistry.RUM_BLOCK);
        RUM_FLOWING = registerFluid("rum_flowing", rumFlowing);

        rumSource.setFlowing(RUM_FLOWING);

        // RED_WINE
        CustomSourceFluid redWineSource = new CustomSourceFluid(FluidTypeRegistry.RED_WINE,
                () -> FluidBlockRegistry.RED_WINE_BLOCK);
        RED_WINE = registerFluid("red_wine", redWineSource);

        CustomFlowingFluid redWineFlowing = new CustomFlowingFluid(FluidTypeRegistry.RED_WINE,
                () -> RED_WINE, () -> FluidBlockRegistry.RED_WINE_BLOCK);
        RED_WINE_FLOWING = registerFluid("red_wine_flowing", redWineFlowing);

        redWineSource.setFlowing(RED_WINE_FLOWING);

        // WHITE_WINE
        CustomSourceFluid whiteWineSource = new CustomSourceFluid(FluidTypeRegistry.WHITE_WINE,
                () -> FluidBlockRegistry.WHITE_WINE_BLOCK);
        WHITE_WINE = registerFluid("white_wine", whiteWineSource);

        CustomFlowingFluid whiteWineFlowing = new CustomFlowingFluid(FluidTypeRegistry.WHITE_WINE,
                () -> WHITE_WINE, () -> FluidBlockRegistry.WHITE_WINE_BLOCK);
        WHITE_WINE_FLOWING = registerFluid("white_wine_flowing", whiteWineFlowing);

        whiteWineSource.setFlowing(WHITE_WINE_FLOWING);

        // CHAMPAGNE
        CustomSourceFluid champagneSource = new CustomSourceFluid(FluidTypeRegistry.CHAMPAGNE,
                () -> FluidBlockRegistry.CHAMPAGNE_BLOCK);
        CHAMPAGNE = registerFluid("champagne", champagneSource);

        CustomFlowingFluid champagneFlowing = new CustomFlowingFluid(FluidTypeRegistry.CHAMPAGNE,
                () -> CHAMPAGNE, () -> FluidBlockRegistry.CHAMPAGNE_BLOCK);
        CHAMPAGNE_FLOWING = registerFluid("champagne_flowing", champagneFlowing);

        champagneSource.setFlowing(CHAMPAGNE_FLOWING);

        // BRANDY
        CustomSourceFluid brandySource = new CustomSourceFluid(FluidTypeRegistry.BRANDY,
                () -> FluidBlockRegistry.BRANDY_BLOCK);
        BRANDY = registerFluid("brandy", brandySource);

        CustomFlowingFluid brandyFlowing = new CustomFlowingFluid(FluidTypeRegistry.BRANDY,
                () -> BRANDY, () -> FluidBlockRegistry.BRANDY_BLOCK);
        BRANDY_FLOWING = registerFluid("brandy_flowing", brandyFlowing);

        brandySource.setFlowing(BRANDY_FLOWING);
    }

    private static <T extends Fluid> T registerFluid(String name, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(SakuraFabric.MODID, name), fluid);
    }

    public static class CustomSourceFluid extends FlowingFluid implements ConvertToSourceFluid, FluidExtensionProvider {
        private final FluidType fluidType;
        private final Supplier<? extends LiquidBlock> block;
        private FlowingFluid flowing;
        private Item bucketItem;
        private IClientFluidTypeExtensions clientExtensions;

        public CustomSourceFluid(FluidType fluidType, Supplier<? extends LiquidBlock> block) {
            this.fluidType = fluidType;
            this.block = block;
        }

        public void setBucketItem(Item bucketItem) {
            this.bucketItem = bucketItem;
        }

        public void setFlowing(FlowingFluid flowing) {
            this.flowing = flowing;
        }

        @Override
        public FlowingFluid getFlowing() {
            return flowing;
        }

        @Override
        public Fluid getSource() {
            return this;
        }

        @Override
        public FluidType getFluidType() {
            return fluidType;
        }

        @Override
        protected boolean canConvertToSource(Level level) {
            return true;
        }

        @Override
        protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
            BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
            Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
        }

        @Override
        protected int getSlopeFindDistance(LevelReader level) {
            return 4;
        }

        @Override
        protected int getDropOff(LevelReader level) {
            return 1;
        }

        @Override
        public Item getBucket() {
            return this.bucketItem != null ? this.bucketItem : Items.BUCKET;
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
        }

        @Override
        public int getTickDelay(LevelReader level) {
            return 5;
        }

        @Override
        protected float getExplosionResistance() {
            return 100.0F;
        }

        @Override
        protected BlockState createLegacyBlock(FluidState state) {
            return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this;
        }

        @Environment(EnvType.CLIENT)
        public void setClientExtensions(IClientFluidTypeExtensions extensions) {
            this.clientExtensions = extensions;
        }

        @Environment(EnvType.CLIENT)
        public IClientFluidTypeExtensions getExtensions() {
            return this.clientExtensions;
        }
    }

    public static class CustomFlowingFluid extends FlowingFluid implements ConvertToSourceFluid, FluidExtensionProvider {
        private final FluidType fluidType;
        private final Supplier<? extends Fluid> still;
        private final Supplier<? extends LiquidBlock> block;
        private Item bucketItem;
        private IClientFluidTypeExtensions clientExtensions;

        public CustomFlowingFluid(FluidType fluidType, Supplier<? extends Fluid> still, Supplier<? extends LiquidBlock> block) {
            this.fluidType = fluidType;
            this.still = still;
            this.block = block;
        }

        public void setBucketItem(Item bucketItem) {
            this.bucketItem = bucketItem;
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public Fluid getSource() {
            return still.get();
        }

        @Override
        public FluidType getFluidType() {
            return fluidType;
        }

        @Override
        protected boolean canConvertToSource(Level level) {
            return true;
        }

        @Override
        protected void beforeDestroyingBlock(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState) {
            BlockEntity blockEntity = blockState.hasBlockEntity() ? levelAccessor.getBlockEntity(blockPos) : null;
            Block.dropResources(blockState, levelAccessor, blockPos, blockEntity);
        }

        @Override
        protected int getSlopeFindDistance(LevelReader level) {
            return 4;
        }

        @Override
        protected int getDropOff(LevelReader level) {
            return 1;
        }

        @Override
        public Item getBucket() {
            return still.get().getBucket();
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
        }

        @Override
        public int getTickDelay(LevelReader level) {
            return 5;
        }

        @Override
        protected float getExplosionResistance() {
            return 100.0F;
        }

        @Override
        protected BlockState createLegacyBlock(FluidState state) {
            return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this || fluid == still.get();
        }

        @Override
        public FlowingFluid getFlowing() {
            return this;
        }

        @Override
        protected void spread(Level world, BlockPos blockPos, FluidState fluidState) {
            if (!fluidState.isEmpty()) {
                int bottomFluidLevel = fluidState.getValue(LEVEL);
                if(bottomFluidLevel == 0) {
                    BlockState blockState = world.getBlockState(blockPos);
                    BlockPos belowBlockPos = blockPos.below();
                    BlockState belowBlockState = world.getBlockState(belowBlockPos);
                    FluidState belowFluidState = this.getNewLiquid(world, belowBlockPos, belowBlockState);
                    if (!belowBlockState.getFluidState().is(FluidTags.WATER) && this.canSpreadTo(world, blockPos, blockState, Direction.DOWN, belowBlockPos, belowBlockState, world.getFluidState(belowBlockPos), belowFluidState.getType())) {
                        this.spreadDown(world, belowBlockPos, belowBlockState, Direction.DOWN, belowFluidState);
                        if (this.sourceNeighborCount(world, blockPos) >= 3) {
                            this.spreadToSides(world, blockPos, fluidState, blockState);
                        }
                    }
                    else if (fluidState.isSource() || !belowBlockState.getFluidState().getType().isSame(this)) {
                        this.spreadToSides(world, blockPos, fluidState, blockState);
                    }
                }
            }
        }

        protected void spreadDown(LevelAccessor world, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
            if (!blockState.isAir()) {
                this.beforeDestroyingBlock(world, blockPos, blockState);
            }
            world.setBlock(blockPos, fluidState.createLegacyBlock(), 3);
        }

        @Environment(EnvType.CLIENT)
        public void setClientExtensions(IClientFluidTypeExtensions extensions) {
            this.clientExtensions = extensions;
        }

        @Environment(EnvType.CLIENT)
        public IClientFluidTypeExtensions getExtensions() {
            return this.clientExtensions;
        }
    }
}