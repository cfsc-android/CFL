package com.chanfinecloud.cfl.http;

import com.chanfinecloud.cfl.entity.BaseEntity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: JSON解析
 */
public class JsonParse {

    /**
     * 解析json，BaseEntity[result]为空时
     * @param json json字符串
     * @return BaseEntity
     */
    public static BaseEntity parse(String json){
        return new Gson().fromJson(json, BaseEntity.class);
    }


    /**
     * 解析json，BaseEntity[result]不为空时且BaseEntity[result]为一个对象
     * @param json json字符串
     * @param clazz Class<T>
     * @return BaseEntity
     */
    public static <T> BaseEntity<T> parse( String json, Class<T> clazz){
        Type type = new ParameterizedTypeImpl(BaseEntity.class, new Class[]{clazz});
        return new Gson().fromJson(json, type);
    }

    /**
     * 解析json，BaseEntity[result]不为空时且BaseEntity[result]为一个list
     * @param json json字符串
     * @param mType Type
     * @return BaseEntity
     */
    public static <T> BaseEntity<T> parse( String json, Type mType){
        Type type = new ParameterizedTypeImpl(BaseEntity.class, mType);
        return new Gson().fromJson(json, type);
    }

    public static <T> T parseObject( String json,Class<T> clazz){
        return new Gson().fromJson(json, clazz);
    }
}
