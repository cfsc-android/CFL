package com.chanfinecloud.cfl.http;

import com.chanfinecloud.cfl.entity.smart.HikIscEntity;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class HikJsonParse {

    public static HikIscEntity parse(String json){
        return new Gson().fromJson(json, HikIscEntity.class);
    }

    public static <T> HikIscEntity<T> parse( String json, Class<T> clazz){
        Type type = new ParameterizedTypeImpl(HikIscEntity.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

}
