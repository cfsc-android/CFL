package com.chanfinecloud.cfl.entity.enumtype;

/**
 * Created by zengx on 2019/5/25.
 * Describe:
 */
public enum CarType {
    SMALL("小型车","1"),
    BIG("大型车","2"),
    MOTOR("摩托车","3"),
    OTHER("其他车","0");


    private final String type;
    private final String value;

    CarType(String type,String value){
        this.type=type;
        this.value=value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
