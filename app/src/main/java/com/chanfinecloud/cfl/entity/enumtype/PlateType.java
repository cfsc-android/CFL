package com.chanfinecloud.cfl.entity.enumtype;

/**
 * Created by zengx on 2019/5/25.
 * Describe:
 */

public enum PlateType {
    STANDARD("标准民用车","BZMYC"),
    CIVIL_02("02式民用车","02MYC"),
    POLICE_ARMED("武警车","WJC"),
    POLICE("警车","JC"),
    CIVIL_DOUBLE("民用车双行尾牌","MYCSXWP"),
    EMBASSY("使馆车","SGC"),
    AGRICULTURAL("农用车","NYC"),
    MOTORCYCLE("摩托车","MTC"),
    ENERGY("新能源车","XNYC"),
    ARMY("军车","JUC");

    private final String type;
    private final String value;
    PlateType(String type,String value) {
        this.type = type;
        this.value=value;
    }

    public String getType() {
        return type;
    }
    public String getValue() {
        return value;
    }
}
