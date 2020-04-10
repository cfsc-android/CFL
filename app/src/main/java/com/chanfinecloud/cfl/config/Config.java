package com.chanfinecloud.cfl.config;

import android.Manifest;
import android.os.Environment;

import com.chanfinecloud.cfl.BuildConfig;

import java.io.File;

/**
 * Created by Loong on 2020/3/25.
 * Version: 1.0
 * Describe: 全局基础配置
 */
public class Config {

    public static final String BASE_URL= BuildConfig.BASE_URL;//后台服务地址
    public static final String ENV= BuildConfig.ENV;//当前运行环境

    public static final String PROVIDER_AUTHORITY="com.chanfinecloud.cfl.fileProvider";//清单文件中provider的authorities属性的值
    public static final String SD_APP_DIR_NAME = "CFL"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
    public static final String File_DIR_NAME = "file";    //存储文件在根目录下的文件夹名字

    public static final String AUTH=ENV.equals("debug")?"":"api-auth/";//鉴权
    public static final String SMS=ENV.equals("debug")?"":"sms-manager-ms/";//验证
    public static final String FILE=ENV.equals("debug")?"":"file-manager-ms/";//文件
    public static final String USER=ENV.equals("debug")?"":"api-user/";//用户
    public static final String ARTICLE=ENV.equals("debug")?"":"smart-content-ms/";//文章
    public static final String IOT=ENV.equals("debug")?"":"smart-iot-ms/";//物联
    public static final String WORKORDER=ENV.equals("debug")?"":"smart-workorder-ms/";//流程
    public static final String BASIC=ENV.equals("debug")?"":"smart-basic-ms/";//小区信息

    public static final String LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + SD_APP_DIR_NAME;//项目本地文件路径

    public static final String[] PERMISSION={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};//应用所需运行时权限

    public static final int CODETIME = 60;//验证码失效，单位：秒

    public static final int SET_JPUSH_ALIAS_SEQUENCE=0x01;//极光推送设置别名操作序列号
    public static final int SET_JPUSH_TAGS_SEQUENCE=0x02;//极光推送设置标签操作序列号
    public static final int CLEAR_JPUSH_TAGS_SEQUENCE=0x03;//极光推送清空标签操作序列号
    public static final int DELETE_JPUSH_ALIAS_SEQUENCE=0x04;//极光推送删除别名操作序列号
}
