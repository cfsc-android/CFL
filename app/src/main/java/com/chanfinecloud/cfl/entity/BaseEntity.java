package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 通用实体，用于JSON解析
 */
public class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * success : true
     * message : 操作成功！
     * code : 200
     * result : null
     * timestamp : 1581906314664
     */

    private boolean success;
    private String message;
    private int code;
    private T result;
    private long timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
