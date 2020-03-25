package com.chanfinecloud.cfl.http;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 请求Body类型枚举
 */
public enum ParamType {
    FormData(0x01),//form-data
    Urlencoded(0x02),//x-www-form-urlencoded
    Json(0x03);//JSON
    private final int type;

    ParamType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}