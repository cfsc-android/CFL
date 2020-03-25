package com.chanfinecloud.cfl.entity.core;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 转场动画枚举
 */
public enum Transition {
    TopIn(0x01),
    TopOut(-0x01),
    LeftIn(0x02),
    LeftOut(-0x02),
    BottomIn(0x03),
    BottomOut(-0x03),
    RightIn(0x04),
    RightOut(-0x04);
    private final int type;

    Transition(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}