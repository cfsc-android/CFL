package com.chanfinecloud.cfl.entity.smart;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * Created by zengx on 2019/6/4.
 * Describe:
 */
public class HikIscEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HikIscEntity{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + (data!=null?data.toString():"null") +
                '}';
    }
}
