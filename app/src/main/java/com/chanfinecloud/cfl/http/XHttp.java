package com.chanfinecloud.cfl.http;

import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.util.LogUtils;
import com.chanfinecloud.cfl.util.SharedPreferencesManage;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;


/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 通用请求封装
 */
public class XHttp {
    /**
     * 发送异步Get请求
     * @param url 请求地址
     * @param map 请求携带参数
     * @param callback 请求结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable Get(String url, Map<String, Object> map,Map<String, String> header,Callback.CommonCallback<T> callback,boolean authorization){
        LogUtil.d(url);
        RequestParams params=new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if(null!=map){
            for(Map.Entry<String, Object> entry : map.entrySet()){
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().get(params, callback);
    }

    /**
     * 发送异步post请求
     * @param url 请求地址
     * @param map 请求携带参数
     * @param callback 请求结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable Post(String url, Map<String, Object> map, Map<String, String> header,Callback.CommonCallback<T> callback,ParamType paramType,boolean authorization) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (null != map) {
            if(paramType==ParamType.Json){
                Gson gson=new Gson();
                LogUtil.d(gson.toJson(map));
                params.setAsJsonContent(true);
                params.setBodyContent(gson.toJson(map));
            }else{
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.addBodyParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        return x.http().post(params, callback);
    }

    /**
     * 发送异步put请求
     * @param url 请求地址
     * @param map 请求携带参数
     * @param callback 请求结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable Put(String url, Map<String, Object> map, Map<String, String> header,Callback.CommonCallback<T> callback,ParamType paramType,boolean authorization) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (null != map) {
            if(paramType==ParamType.Json){
                Gson gson=new Gson();
                LogUtil.d(gson.toJson(map));
                params.setAsJsonContent(true);
                params.setBodyContent(gson.toJson(map));
            }else{
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.addParameter(entry.getKey(), entry.getValue());
                }
            }

        }
        return x.http().request(HttpMethod.PUT,params, callback);
    }

    /**
     * 发送异步delete请求
     * @param url 请求地址
     * @param map 请求携带参数
     * @param callback 请求结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable Delete(String url, Map<String, Object> map, Map<String, String> header,Callback.CommonCallback<T> callback,ParamType paramType,boolean authorization) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (null != map) {
            if(paramType==ParamType.Json){
                Gson gson=new Gson();
                LogUtil.d(gson.toJson(map));
                params.setAsJsonContent(true);
                params.setBodyContent(gson.toJson(map));
            }else{
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    params.addParameter(entry.getKey(), entry.getValue());
                }
            }

        }
        return x.http().request(HttpMethod.DELETE,params, callback);
    }

    /**
     * 上传文件
     * @param url 请求地址
     * @param map 请求携带参数
     * @param callback 请求结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable UpLoadFile(String url, Map<String, Object> map,Map<String, String> header, Callback.CommonCallback<T> callback,boolean authorization) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                params.addParameter(entry.getKey(), entry.getValue());
                params.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        return x.http().post(params, callback);
    }


    /**
     * 下载文件
     * @param url 请求地址
     * @param filepath 文件下载保存地址
     * @param callback 下载结果回调
     * @param authorization 请求头是否携带token
     * @param <T> ResultType
     * @return Callback.Cancelable
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Map<String, String> header,Callback.ProgressCallback<T> callback,boolean authorization) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        params.addHeader(authorization?"Authorization":"",getAuthorization());
        if(null!=header){
            for(Map.Entry<String, String> entry : header.entrySet()){
                params.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        return x.http().get(params, callback);
    }

    /**
     * 获取请求头需要的token
     * @return String
     */
    private static String getAuthorization(){
        TokenEntity tokenEntity = SharedPreferencesManage.getToken();
        if(tokenEntity!=null){
            LogUtils.d("Authorization : bearer "+tokenEntity.getAccess_token());
            return "bearer "+tokenEntity.getAccess_token();
        }
        return "";
    }

}