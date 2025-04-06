package com.flechazo.sakura.fluid;

import com.flechazo.sakura.SakuraFabric;
import com.flechazo.sakura.utils.IClientFluidTypeExtensions;
import io.github.fabricators_of_create.porting_lib.fluids.FluidStack;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.extensions.ConvertToSourceFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
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
    // 获取流体的方法 好多呀QAQ
    public static FlowingFluid getFoodOil() {
        return FOOD_OIL;
    }

    public static FlowingFluid getFoodOilFlowing() {
        return FOOD_OIL_FLOWING;
    }

    public static FlowingFluid getDoburoku() {
        return DOBUROKU;
    }

    public static FlowingFluid getDoburokuFlowing() {
        return DOBUROKU_FLOWING;
    }

    public static FlowingFluid getSake() {
        return SAKE;
    }

    public static FlowingFluid getSakeFlowing() {
        return SAKE_FLOWING;
    }

    public static FlowingFluid getShouchu() {
        return SHOUCHU;
    }

    public static FlowingFluid getShouchuFlowing() {
        return SHOUCHU_FLOWING;
    }

    public static FlowingFluid getBeer() {
        return BEER;
    }

    public static FlowingFluid getBeerFlowing() {
        return BEER_FLOWING;
    }

    public static FlowingFluid getWhiskey() {
        return WHISKEY;
    }

    public static FlowingFluid getWhiskeyFlowing() {
        return WHISKEY_FLOWING;
    }

    public static FlowingFluid getRum() {
        return RUM;
    }

    public static FlowingFluid getRumFlowing() {
        return RUM_FLOWING;
    }

    public static FlowingFluid getRedWine() {
        return RED_WINE;
    }

    public static FlowingFluid getRedWineFlowing() {
        return RED_WINE_FLOWING;
    }

    public static FlowingFluid getWhiteWine() {
        return WHITE_WINE;
    }

    public static FlowingFluid getWhiteWineFlowing() {
        return WHITE_WINE_FLOWING;
    }

    public static FlowingFluid getChampagne() {
        return CHAMPAGNE;
    }

    public static FlowingFluid getChampagneFlowing() {
        return CHAMPAGNE_FLOWING;
    }

    public static FlowingFluid getBrandy() {
        return BRANDY;
    }

    public static FlowingFluid getBrandyFlowing() {
        return BRANDY_FLOWING;
    }

        // 创建源流体
        private static FlowingFluid createSourceFluid(FluidType fluidType, Supplier<? extends LiquidBlock> block) {
            return new CustomSourceFluid(fluidType, block);
        }

        // 创建流动流体
        private static FlowingFluid createFlowingFluid(FluidType fluidType, Supplier<? extends Fluid> still, Supplier<? extends LiquidBlock> block) {
            return new CustomFlowingFluid(fluidType, still, block);
        }

    private static <T extends Fluid> T registerFluid(String name, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(SakuraFabric.MODID, name), fluid);
    }

    // 自定义源流体类
    public static class CustomSourceFluid extends net.minecraft.world.level.material.FlowingFluid implements ConvertToSourceFluid, IClientFluidTypeExtensions.FluidExtensionProvider {
        private final FluidType fluidType;
        private final Supplier<? extends LiquidBlock> block;
        private FlowingFluid flowing; // Add this field

        public CustomSourceFluid(FluidType fluidType, Supplier<? extends LiquidBlock> block) {
            this.fluidType = fluidType;
            this.block = block;
        }

        // Add this method to set the flowing fluid
        public void setFlowing(FlowingFluid flowing) {
            this.flowing = flowing;
        }

        @Override
        public net.minecraft.world.level.material.FlowingFluid getFlowing() {
            return flowing; // Return the flowing fluid
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
            return true; // 允许转换为源流体
        }

        @Override
        protected void beforeDestroyingBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
            // 实现必要的方法
        }

        @Override
        protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) {
            return 4;
        }

        @Override
        protected int getDropOff(net.minecraft.world.level.LevelReader level) {
            return 1;
        }

        @Override
        public net.minecraft.world.item.Item getBucket() {
            return net.minecraft.world.item.Items.BUCKET; // 这里应该返回对应的桶物品
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            // 仅允许向下替换非同类流体
            return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
        }

        @Override
        public int getTickDelay(net.minecraft.world.level.LevelReader level) {
            return 5;
        }

        @Override
        protected void spreadTo(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
            // 移除对 LiquidBlock 的检查
            if (blockState.canBeReplaced(this)) {
                levelAccessor.setBlock(blockPos, createLegacyBlock(fluidState), 3);
            }
        }

        @Override
        protected float getExplosionResistance() {
            return 100.0F;
        }

        @Override
        protected net.minecraft.world.level.block.state.BlockState createLegacyBlock(net.minecraft.world.level.material.FluidState state) {
            return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
        }

        @Override
        public boolean isSource(net.minecraft.world.level.material.FluidState state) {
            return true;
        }

        @Override
        public int getAmount(net.minecraft.world.level.material.FluidState state) {
            return 8;
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this;
        }

        @Override
        public IClientFluidTypeExtensions getExtensions() {
            return new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture () {
                    return IClientFluidTypeExtensions.super.getStillTexture();
                }

                @Override
                public ResourceLocation getFlowingTexture () {
                    return IClientFluidTypeExtensions.super.getFlowingTexture();
                }

                @Override
                public int getTintColor() {
                    return FluidTypeRegistry.getFluidColor(fluidType);
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return FluidTypeRegistry.getFluidColor(fluidType);
                }
            };
        }
    }

    // 自定义流动流体类
    public static class CustomFlowingFluid extends net.minecraft.world.level.material.FlowingFluid implements ConvertToSourceFluid, IClientFluidTypeExtensions.FluidExtensionProvider {
        private final FluidType fluidType;
        private final Supplier<? extends Fluid> still;
        private final Supplier<? extends LiquidBlock> block;

        public CustomFlowingFluid(FluidType fluidType, Supplier<? extends Fluid> still, Supplier<? extends LiquidBlock> block) {
            this.fluidType = fluidType;
            this.still = still;
            this.block = block;
        }

        @Override
        protected void createFluidStateDefinition(net.minecraft.world.level.block.state.StateDefinition.Builder<net.minecraft.world.level.material.Fluid, net.minecraft.world.level.material.FluidState> builder) {
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
            return true; // 允许转换为源流体
        }

        @Override
        protected void beforeDestroyingBlock(net.minecraft.world.level.LevelAccessor level, net.minecraft.core.BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
            // 实现必要的方法
        }

        @Override
        protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) {
            return 4;
        }

        @Override
        protected int getDropOff(net.minecraft.world.level.LevelReader level) {
            return 1;
        }

        @Override
        public net.minecraft.world.item.Item getBucket() {
            return net.minecraft.world.item.Items.BUCKET; // 这里应该返回对应的桶物品
        }

        @Override
        protected boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction direction) {
            // 仅允许向下替换非同类流体
            return direction == Direction.DOWN && !fluid.is(FluidTags.WATER);
        }

        @Override
        public int getTickDelay(net.minecraft.world.level.LevelReader level) {
            return 5;
        }

        @Override
        protected float getExplosionResistance() {
            return 100.0F;
        }

        @Override
        protected net.minecraft.world.level.block.state.BlockState createLegacyBlock(net.minecraft.world.level.material.FluidState state) {
            return block.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
        }

        @Override
        protected void spreadTo(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState) {
            // 移除对 LiquidBlock 的检查
            if (blockState.canBeReplaced(this)) {
                levelAccessor.setBlock(blockPos, createLegacyBlock(fluidState), 3);
            }
        }

        @Override
        public boolean isSource(net.minecraft.world.level.material.FluidState state) {
            return false;
        }

        @Override
        public int getAmount(net.minecraft.world.level.material.FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSame(Fluid fluid) {
            return fluid == this || fluid == still.get();
        }

        @Override
        public net.minecraft.world.level.material.FlowingFluid getFlowing () {
            return this;
        }

        @Override
        public IClientFluidTypeExtensions getExtensions() {
            return new IClientFluidTypeExtensions() {
                @Override
                public ResourceLocation getStillTexture () {
                    return IClientFluidTypeExtensions.super.getStillTexture();
                }

                @Override
                public ResourceLocation getFlowingTexture () {
                    return IClientFluidTypeExtensions.super.getFlowingTexture();
                }

                @Override
                public int getTintColor() {
                    return FluidTypeRegistry.getFluidColor(fluidType);
                }

                @Override
                public int getTintColor(FluidStack stack) {
                    return FluidTypeRegistry.getFluidColor(fluidType);
                }
            };
        }
    }
}