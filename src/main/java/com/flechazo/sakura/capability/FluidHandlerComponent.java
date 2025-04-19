package com.flechazo.sakura.capability;

import dev.onyxstudios.cca.api.v3.component.Component;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import net.minecraft.core.Direction;

/**
 * 流体处理组件接口，用于替代Forge的FluidHandler能力
 */
public interface FluidHandlerComponent extends Component {
    /**
     * 获取指定方向的流体存储
     * @param direction 方向，可以为null表示任意方向
     * @return 流体存储接口
     */
    FluidTank getFluidHandler(Direction direction);

    /**
     * 获取输入流体存储
     * @return 输入流体存储
     */
    FluidTank getInputFluidHandler();

    /**
     * 获取输出流体存储
     * @return 输出流体存储
     */
    FluidTank getOutputFluidHandler();
}