package com.chanfinecloud.cfl.util;


import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.LoginUserEntity;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.entity.QQLoginEntity;
import com.chanfinecloud.cfl.entity.ThirdInfoEntity;
import com.chanfinecloud.cfl.entity.WeiXinLoginEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;

import java.util.ArrayList;

/**
 * 此类描述的是:文件存储管理
 *
 * @author TanYong
 *         create at 2017/4/21 9:44
 */
public class FileManagement {

    public static void setCustomerProject(ProjectInfo projectInfo){
        ArrayList<ProjectInfo> list=getCustomerProjects();
        if(list==null){
            list=new ArrayList<>();
        }
        list.add(projectInfo);
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "customerProject", "customerProject", list);
    }

    public static ArrayList<ProjectInfo> getCustomerProjects(){
        return (ArrayList<ProjectInfo>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),
                "customerProject", "customerProject");
    }

    public static void removeCustomerProject(ArrayList<Integer> deleteList){
        ArrayList<ProjectInfo> temp=new ArrayList<>();
        ArrayList<ProjectInfo> list=getCustomerProjects();
        for (int i = 0; i < list.size(); i++) {
            if(!deleteList.contains(i+2)){
                temp.add(list.get(i));
            }
        }
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "customerProject", "customerProject", temp);
    }

    public static void setProjectInfo(ProjectInfo projectInfo){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "projectInfo", "projectInfo", projectInfo);
    }

    public static ProjectInfo getProjectInfo(){
        return (ProjectInfo) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),
                "projectInfo", "projectInfo");
    }

    public static void setThirdInfo(ArrayList<ThirdInfoEntity> thirdInfo){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "thirdInfo", "thirdInfo", thirdInfo);
    }

    public static ArrayList<ThirdInfoEntity> getThirdInfo(){
        return (ArrayList<ThirdInfoEntity>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),
                "thirdInfo", "thirdInfo");
    }


    public static void setLoginType(String type){
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(),
                "login_type", "loginType", type);
    }

    public static String getLoginType(){
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(),
                "login_type", "loginType");
    }

    public static void setWXLogin(WeiXinLoginEntity wxLogin){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "wx_login", "wxLogin", wxLogin);
    }

    public static WeiXinLoginEntity getWXLogin(){
        return (WeiXinLoginEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),
                "wx_login", "wxLogin");
    }

    public static void setQQLogin(QQLoginEntity qqLogin){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),
                "qq_login", "qqLogin", qqLogin);
    }

    public static QQLoginEntity getQQLogin(){
        return (QQLoginEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),
                "qq_login", "qqLogin");
    }

    public static void setParkIndexCode(String parkIndexCode){
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(),
                "park_index_code", "parkIndexCode", parkIndexCode);
    }

    public static String getParkIndexCode(){
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(),
                "park_index_code", "parkIndexCode");
    }

    public static void setNoticeFlag(String flag){
        SharedPreferencesUtil.getInstance().saveStringValue(
                CFLApplication.getAppContext(), "notice_flag",
                "noticeFlag", flag);
    }

    public static String getNoticeFlag(){
        String noticeFlag= SharedPreferencesUtil.getInstance().getStringValue(
                CFLApplication.getAppContext(),"notice_flag","noticeFlag"
        );

        return noticeFlag;
    }

    public static void setCurrentIp(String ip){
        SharedPreferencesUtil.getInstance().saveStringValue(
                CFLApplication.getAppContext(), "ip",
                "IP", ip);
    }

    public static String getCurrentIp(){
        String ip= SharedPreferencesUtil.getInstance().getStringValue(
                CFLApplication.getAppContext(),"ip","IP"
        );

        return ip;
    }

    public static void setHikToken(String token){
        SharedPreferencesUtil.getInstance().saveStringValue(
                CFLApplication.getAppContext(), "hik_token",
                "HikToken", token);
    }
    public static String getHikToken(){
        String token= SharedPreferencesUtil.getInstance().getStringValue(
                CFLApplication.getAppContext(),"hik_token","HikToken"
        );

        return token;
    }


    public static void setBaseUser(LoginUserEntity loginUserEntity) {
        SharedPreferencesUtil.getInstance().saveObject(
                CFLApplication.getAppContext(), "loginUserEntity_key",
                "LoginUserEntity", loginUserEntity);
    }

    /**
     * @return 获取文件用户数据
     */
    public static LoginUserEntity getLoginUserEntity() {
        LoginUserEntity u = (LoginUserEntity) SharedPreferencesUtil.getInstance().getObject(
                CFLApplication.getAppContext(), "loginUserEntity_key",
                "LoginUserEntity");
        return u;
    }

    public static void saveServerTime(long time) {
        long lTime = time - System.currentTimeMillis();
        SharedPreferencesUtil.getInstance().saveLongValue(CFLApplication.getAppContext(), "ConfigurationVariable", "getServerTime", lTime);
    }

    public static long getServerTime() {
        return SharedPreferencesUtil.getInstance().getLongValue(CFLApplication.getAppContext(), "ConfigurationVariable",
                "getServerTime");
    }

    public static void saveTokenInfo(String tokenInfo) {
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(), "tokenInfo", "getTokenInfo", tokenInfo);
    }

    public static String getTokenInfo() {
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(), "tokenInfo", "getTokenInfo");
    }
    public static void saveIsFromShop(boolean isFromShop) {
        SharedPreferencesUtil.getInstance().saveBooleanValue(CFLApplication.getAppContext(), "isFromShop", "isFromShop", isFromShop);
    }

    public static boolean getIsFromShop() {
        return SharedPreferencesUtil.getInstance().getBooleanValue(CFLApplication.getAppContext(), "isFromShop", "isFromShop");
    }

    /**
     * @author TanYong
     * create at 2017/6/14 21:19
     * TODO：保存极光别名
     */
    public static void saveJpushAlias(String alias) {
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(), "jpushAlias", "jpushAlias", alias);
    }

    /**
     * @author TanYong
     * create at 2017/6/14 21:20
     * TODO：获取极光别名
     */
    public static String getJpushAlias() {
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(), "jpushAlias", "jpushAlias");
    }

    /**
     * @author TanYong
     * create at 2017/6/14 21:19
     * TODO：保存极光标签
     */
    public static void saveJpushTags(String tags) {
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(), "jpushTags", "jpushTags", tags);
    }

    /**
     * @author TanYong
     * create at 2017/6/14 21:20
     * TODO：获取极光标签
     */
    public static String getJpushTags() {
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(), "jpushTags", "jpushTags");
    }


    public static void setPhone(String phone){
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(),"cfl","phone",phone);
    }
    public static String getPhone(){
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(),"cfl","phone");
    }

    public static void setUserInfo(UserInfoEntity userInfo){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","userInfo",userInfo);
    }

    public static UserInfoEntity getUserInfoEntity(){
        return (UserInfoEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","userInfo");
    }
}
