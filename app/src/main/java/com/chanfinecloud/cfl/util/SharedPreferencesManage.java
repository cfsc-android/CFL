package com.chanfinecloud.cfl.util;


import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;

/**
 * Created by Loong on 2020/2/7.
 * Version: 1.0
 * Describe: SharedPreferences管理类
 */
public class SharedPreferencesManage {


    /**
     * 保存Token
     * @param token TokenEntity对象
     * @return boolean
     */
    public static boolean saveToken(TokenEntity token){
        return SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","Token",token);
    }

    /**
     * 获取Token
     * @return TokenEntity
     */
    public static TokenEntity getToken(){
        return (TokenEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","Token");
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public static void setUserInfo(UserInfoEntity userInfo){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","UserInfo",userInfo);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfoEntity getUserInfo(){
        return (UserInfoEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","UserInfo");
    }

}

