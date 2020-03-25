package com.chanfinecloud.cfl.http;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 请求方法枚举
 */
public enum HttpMethod {
    Get(0x01),
    Post(0x02),
    Put(0x03),
    Delete(0x04),
    Upload(0x05),
    Download(0x06);
    private final int type;

    HttpMethod(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HttpMethod{" +
                "type=" + type +
                '}';
    }
}