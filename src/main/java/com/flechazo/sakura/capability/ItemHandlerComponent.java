package com.flechazo.sakura.capability;

import dev.onyxstudios.cca.api.v3.component.Component;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlottedStackStorage;
import net.minecraft.core.Direction;

/**
 * 物品处理组件接口
 */
public interface ItemHandlerComponent extends Component {
    /**
     * 获取指定方向的物品存储
     *
     * @param direction 方向，可以为null表示任意方向
     * @return 物品存储接口
     */
    SlottedStackStorage getItemHandler(Direction direction);
}