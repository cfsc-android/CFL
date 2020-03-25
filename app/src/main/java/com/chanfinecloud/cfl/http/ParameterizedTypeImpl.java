package com.chanfinecloud.cfl.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: JSON泛型解析
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;
    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    public ParameterizedTypeImpl(Class raw, Type type) {
        this.raw = raw;
        this.args = new Type[]{type};
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {return null;}
}