package com.chanfinecloud.cfl.config;

import android.Manifest;

import com.chanfinecloud.cfl.BuildConfig;

/**
 * Created by Loong on 2020/3/25.
 * Version: 1.0
 * Describe: 全局基础配置
 */
public class Config {

    public static final String BASE_URL= BuildConfig.BASE_URL;//后台服务地址

    public static final String PROVIDER_AUTHORITY="com.chanfinecloud.cflforemployee.fileprovider";//清单文件中provider的authorities属性的值
    public static final String SD_APP_DIR_NAME = "CFL"; //存储程序在外部SD卡上的根目录的名字
    public static final String PHOTO_DIR_NAME = "photo";    //存储照片在根目录下的文件夹名字
    public static final String File_DIR_NAME = "file";    //存储文件在根目录下的文件夹名字

    public static final String[] PERMISSION={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};//应用所需运行时权限
}
