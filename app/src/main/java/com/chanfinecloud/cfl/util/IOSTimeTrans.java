package com.chanfinecloud.cfl.util;

public class IOSTimeTrans {
    public static String trans(String iosTime){
        String result="";
        if(iosTime == null){
            return result;
        }
        result=iosTime.substring(0,19).replace("T"," ");
        return result;
    }
}
