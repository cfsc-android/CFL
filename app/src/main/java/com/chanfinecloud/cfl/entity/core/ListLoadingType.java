package com.chanfinecloud.cfl.entity.core;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 分页加载列表数据类型枚举
 */
public enum ListLoadingType {
    Refresh(0x001),//刷新
    LoadMore(0x002);//加载更多

    private final int type;
    ListLoadingType(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }
}
