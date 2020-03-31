package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/3/20.
 * Version: 1.0
 * Describe:
 */
public enum HouseholdType {
    Property("YZ"),
    Family("JS"),
    Rent("ZH");
    private final String type;

    public String getType() {
        return type;
    }

    HouseholdType(String type) {
        this.type = type;
    }
}
