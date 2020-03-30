package com.chanfinecloud.cfl.http;

import org.xutils.common.Callback;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 请求参数封装
 */
public class RequestParam<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public RequestParam(String url, HttpMethod method) {
        this.url = url;
        this.method = method;
        this.paramType=ParamType.Urlencoded;
        this.authorization=true;
    }

    private String url;
    private HttpMethod method;
    private ParamType paramType;
    private Map<String, Object> requestMap;
    private Map<String,String> paramHeader;
    private Map<String,String> paramBody;
    private boolean authorization;
    private String filepath;
    private Callback.CommonCallback<T> callback;
    private Callback.ProgressCallback<T> progressCallback;

    public Map<String, String> getParamBody() {
        return paramBody;
    }

    public void setParamBody(Map<String, String> paramBody) {
        this.paramBody = paramBody;
    }

    public Map<String, String> getParamHeader() {
        return paramHeader;
    }

    public void setParamHeader(Map<String, String> paramHeader) {
        this.paramHeader = paramHeader;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public Callback.ProgressCallback<T> getProgressCallback() {
        return progressCallback;
    }

    public void setProgressCallback(Callback.ProgressCallback<T> progressCallback) {
        this.progressCallback = progressCallback;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public void setParamType(ParamType paramType) {
        this.paramType = paramType;
    }

    public Map<String, Object> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, Object> requestMap) {
        this.requestMap = requestMap;
    }

    public Callback.CommonCallback<T> getCallback() {
        return callback;
    }

    public void setCallback(Callback.CommonCallback<T> callback) {
        this.callback = callback;
    }
}
