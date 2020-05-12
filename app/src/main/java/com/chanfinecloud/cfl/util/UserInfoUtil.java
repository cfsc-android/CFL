package com.chanfinecloud.cfl.util;

import android.text.TextUtils;

import com.chanfinecloud.cfl.entity.BaseEntity;
import com.chanfinecloud.cfl.entity.smart.CurrentDistrictEntity;
import com.chanfinecloud.cfl.entity.smart.FileEntity;
import com.chanfinecloud.cfl.entity.smart.HouseholdRoomEntity;
import com.chanfinecloud.cfl.entity.smart.NoticeReceiverType;
import com.chanfinecloud.cfl.entity.smart.ResourceEntity;
import com.chanfinecloud.cfl.entity.smart.UserInfoEntity;
import com.chanfinecloud.cfl.http.HttpMethod;
import com.chanfinecloud.cfl.http.JsonParse;
import com.chanfinecloud.cfl.http.MyCallBack;
import com.chanfinecloud.cfl.http.RequestParam;
import com.chanfinecloud.cfl.http.XHttp;
import com.chanfinecloud.cfl.ui.MainActivity;

import java.util.List;

import static com.chanfinecloud.cfl.config.Config.BASE_URL;
import static com.chanfinecloud.cfl.config.Config.BASIC;
import static com.chanfinecloud.cfl.config.Config.FILE;
import static com.chanfinecloud.cfl.util.FileManagement.getUserInfo;

/**
 * Created by Loong on 2020/4/8.
 * Version: 1.0
 * Describe:
 */
public class UserInfoUtil {
    public static String getCurrentHouseholdType(){
        String type=NoticeReceiverType.业主.getType();
        CurrentDistrictEntity currentDistrict= getUserInfo().getCurrentDistrict();
        if(!TextUtils.isEmpty(currentDistrict.getRoomId())){
            List<HouseholdRoomEntity> list= getUserInfo().getRoomList();
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getId().equals(currentDistrict.getRoomId())){
                    type=list.get(i).getHouseholdType();
                }
            }

        }else{
            type=NoticeReceiverType.游客.getType();
        }
        LogUtils.d(type);
        return type;
    }

    /**
     * 刷新服务器用户信息缓存
     */
    public static void refreshUserInfoByServerCache(OnRefreshListener listener){
        //接口更新后不需要再刷缓存了
        refreshUserInfo(listener);
        /*XHttp.Get(BASE_URL+BASIC+"basic/householdInfo/household-anon/refresh",null,null,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                BaseEntity baseEntity= JsonParse.parse(result);
                if(baseEntity.isSuccess()){
                    refreshUserInfo(listener);
                }else{
                    if(listener!=null)
                    listener.onFail(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if(listener!=null)
                listener.onFail(ex.getMessage());
            }
        },true);*/
    }

    /**
     * 刷新用户信息
     */
    public static void refreshUserInfo(OnRefreshListener listener){
        XHttp.Get(BASE_URL+BASIC+"basic/householdInfo/currentHousehold",null,null,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d(result);
                BaseEntity<UserInfoEntity> baseEntity= JsonParse.parse(result,UserInfoEntity.class);
                if(baseEntity.isSuccess()){
                    FileManagement.setUserInfo(baseEntity.getResult());//缓存用户信息
                    if(listener!=null)
                    listener.onSuccess();
                }else{
                    if(listener!=null)
                    listener.onFail(baseEntity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                if(listener!=null)
                listener.onFail(ex.getMessage());
            }
        },true);
    }

    /**
     * 缓存用户头像信息
     */
    /*public static void initAvatarResource(OnRefreshListener listener){
        String avatarId=FileManagement.getUserInfo().getAvatarId();
        if(avatarId!=null){
            XHttp.Get(BASE_URL+FILE+"files/byid/"+avatarId,null,null,new MyCallBack<String>(){
                @Override
                public void onSuccess(String result) {
                    super.onSuccess(result);
                    LogUtils.d(result);
                    BaseEntity<FileEntity> baseEntity= JsonParse.parse(result,FileEntity.class);
                    if(baseEntity.isSuccess()){
                        ResourceEntity resourceEntity=new ResourceEntity();
                        resourceEntity.setId(baseEntity.getResult().getId());
                        resourceEntity.setContentType(baseEntity.getResult().getContentType());
                        resourceEntity.setCreateTime(baseEntity.getResult().getCreateTime());
                        resourceEntity.setName(baseEntity.getResult().getName());
                        resourceEntity.setUrl(baseEntity.getResult().getDomain()+baseEntity.getResult().getUrl());
                        FileManagement.setAvatarReseource(resourceEntity);//缓存用户头像信息
                        if(listener!=null)
                            listener.onSuccess();
                    }else{
                        if(listener!=null)
                            listener.onFail(baseEntity.getMessage());
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    super.onError(ex, isOnCallback);
                    if(listener!=null)
                        listener.onFail(ex.getMessage());
                }
            },true);
        }
    }*/

    /**
     * 缓存业主人脸信息
     */
    public static void initFaceResource(OnRefreshListener listener){

    }


    /**
     * 回调接口
     */
    public interface OnRefreshListener{
        void onSuccess();
        void onFail(String msg);
    }
}
