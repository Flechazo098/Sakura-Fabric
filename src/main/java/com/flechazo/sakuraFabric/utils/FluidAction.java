package com.flechazo.sakuraFabric.utils;

/**
 * 流体操作枚举，用于指定流体操作是模拟还是执行
 */
public enum FluidAction {
    /**
     * 执行操作并修改储罐内容
     */
    EXECUTE,

    /**
     * 模拟操作，不修改储罐内容
     */
    SIMULATE;

    /**
     * 检查是否应该执行操作
     * @return 如果应该执行操作则返回true
     */
    public boolean execute() {
        return this == EXECUTE;
    }

    /**
     * 检查是否应该模拟操作
     * @return 如果应该模拟操作则返回true
     */
    public boolean simulate() {
        return this == SIMULATE;
    }
}