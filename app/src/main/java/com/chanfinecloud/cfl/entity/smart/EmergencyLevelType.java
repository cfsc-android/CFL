package com.chanfinecloud.cfl.entity.smart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Loong on 2020/3/16.
 * Version: 1.0
 * Describe:
 */
public enum EmergencyLevelType {
    非常紧急("1"),
    紧急("2"),
    一般("3"),
    低("4"),
    可以忽略("5");
    private final String type;

    public String getType() {
        return type;
    }

    EmergencyLevelType(String type) {
        this.type = type;
    }

    public static List<String> getEmergencyLevelTypeList() {
        EmergencyLevelType[] types = EmergencyLevelType.values();
        List<String> list=new ArrayList<>();
        for (int i = 0; i < types.length; i++) {
            list.add(types[i].name());
        }
        return list;
    }

    public static String getEmergencyLevelTypeValue(int position){
        EmergencyLevelType[] types = EmergencyLevelType.values();
        return types[position].getType();
    }
}
