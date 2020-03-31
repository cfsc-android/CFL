package com.chanfinecloud.cfl.util;

import com.chanfinecloud.cfl.entity.enumtype.CarColor;
import com.chanfinecloud.cfl.entity.enumtype.CarType;
import com.chanfinecloud.cfl.entity.enumtype.PlateColor;
import com.chanfinecloud.cfl.entity.enumtype.PlateType;


/**
 * Created by zengx on 2019/5/25.
 * Describe:
 */
public class EnumUtils {
    public static String getPlateColorString(String plateColor) {
        if(plateColor==null){
            return "";
        }
        String result = "";
        for(PlateColor color:PlateColor.values()){
            if(plateColor.equals(color.getValue())){
                result=color.getColor();
            }
        }
        return result;
    }
    public static String getPlateTypeString(String plateType) {
        if(plateType==null){
            return "";
        }
        String result = "";
        for(PlateType type:PlateType.values()){
            if(plateType.equals(type.getValue())){
                result=type.getType();
            }
        }
        return result;
    }
    public static String getCarColorString(String carColor) {
        if(carColor==null){
            return "";
        }
        String result = "";
        for(CarColor color:CarColor.values()){
            if(carColor.equals(color.getValue())){
                result=color.getColor();
            }
        }
        return result;
    }
    public static String getCarTypeString(String carType) {
        if(carType==null){
            return "";
        }
        String result = "";
        for(CarType type:CarType.values()){
            if(carType.equals(type.getValue())){
                result=type.getType();
            }
        }
        return result;
    }
}
