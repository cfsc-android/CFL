package com.chanfinecloud.cfl.util;


import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.entity.HikUser;
import com.chanfinecloud.cfl.entity.LoginUserEntity;
import com.chanfinecloud.cfl.entity.ProjectInfo;
import com.chanfinecloud.cfl.entity.QQLoginEntity;
import com.chanfinecloud.cfl.entity.RoomInfoEntity;
import com.chanfinecloud.cfl.entity.ThirdInfoEntity;
import com.chanfinecloud.cfl.entity.TokenEntity;
import com.chanfinecloud.cfl.entity.WeiXinLoginEntity;
import com.chanfinecloud.cfl.entity.smart.OrderStatusEntity;
import com.chanfinecloud.cfl.entity.smart.OrderTypeEntity;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;

import java.util.ArrayList;
import java.util.List;

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

    public static void setParkIndexCode(String parkIndexCode){
        SharedPreferencesUtil.getInstance().saveStringValue(CFLApplication.getAppContext(),
                "park_index_code", "parkIndexCode", parkIndexCode);
    }

    public static String getParkIndexCode(){
        return SharedPreferencesUtil.getInstance().getStringValue(CFLApplication.getAppContext(),
                "park_index_code", "parkIndexCode");
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

    public static UserInfoEntity getUserInfo(){
        return (UserInfoEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","userInfo");
    }


    public static void setTokenEntity(TokenEntity token){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","token",token);
    }
    public static TokenEntity getTokenEntity(){
        return (TokenEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","token");
    }

    public static void setComplainType(List<OrderTypeEntity> list){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","complainType",list);
    }

    public static List<OrderTypeEntity> getComplainType(){
        return (List<OrderTypeEntity>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","complainType");
    }

    public static void setOrderType(List<OrderTypeEntity> list){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","orderType",list);
    }


    public static void setComplainStatus(List<OrderStatusEntity> list){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","complainStatus",list);
    }

    public static List<OrderStatusEntity> getComplainStatus(){
        return (List<OrderStatusEntity>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","complainStatus");
    }

    public static List<OrderTypeEntity> getOrderType(){
        return (List<OrderTypeEntity>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","orderType");
    }
    public static void setOrderStatus(List<OrderStatusEntity> list){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","orderStatus",list);
    }

    public static List<OrderStatusEntity> getOrderStatus(){
        return (List<OrderStatusEntity>) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","orderStatus");
    }

    public static void setAvatarReseource(ResourceEntity resource){
        SharedPreferencesUtil.getInstance().saveObject(CFLApplication.getAppContext(),"cfl","avatar",resource);
    }

    public static ResourceEntity getAvatarResource(){
        return (ResourceEntity) SharedPreferencesUtil.getInstance().getObject(CFLApplication.getAppContext(),"cfl","avatar");
    }

    public static void setPushFlag(boolean flag){
        SharedPreferencesUtil.getInstance().saveBooleanValue(CFLApplication.getAppContext(),"cfl","PushFlag",flag);
    }

    public static boolean getPushFlag(){
        return SharedPreferencesUtil.getInstance().getBooleanValue(CFLApplication.getAppContext(),"cfl","PushFlag",true);
    }


    public static void setNotificationFlag(boolean flag){
        SharedPreferencesUtil.getInstance().saveBooleanValue(CFLApplication.getAppContext(),"cfl","NotificationFlag",flag);
    }

    public static boolean getNotificationFlag(){
        return SharedPreferencesUtil.getInstance().getBooleanValue(CFLApplication.getAppContext(),"cfl","NotificationFlag",true);
    }
}
