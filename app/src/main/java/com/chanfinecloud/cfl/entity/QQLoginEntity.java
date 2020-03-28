package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/7/3.
 * Describe:
 */
public class QQLoginEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * msg :
     * ret : 0
     * unionid :
     * gender : 男
     * is_yellow_vip : 0
     * city :
     * level : 0
     * openid : A4B0C6109DDBBE1B06A216655632FA84
     * profile_image_url : http://thirdqq.qlogo.cn/g?b=oidb&k=YicIiaYsH9KfOZTRUHMj74sg&s=100
     * accessToken : 2B1229895FC62993C29B2E0614738B84
     * access_token : 2B1229895FC62993C29B2E0614738B84
     * uid : A4B0C6109DDBBE1B06A216655632FA84
     * is_yellow_year_vip : 0
     * province :
     * screen_name : 哟，耍脾气、
     * name : 哟，耍脾气、
     * iconurl : http://thirdqq.qlogo.cn/g?b=oidb&k=YicIiaYsH9KfOZTRUHMj74sg&s=100
     * yellow_vip_level : 0
     * expiration : 1569892514673
     * vip : 0
     * expires_in : 1569892514673
     */

    private String msg;
    private String ret;
    private String unionid;
    private String gender;
    private String is_yellow_vip;
    private String city;
    private String level;
    private String openid;
    private String profile_image_url;
    private String accessToken;
    private String access_token;
    private String uid;
    private String is_yellow_year_vip;
    private String province;
    private String screen_name;
    private String name;
    private String iconurl;
    private String yellow_vip_level;
    private String expiration;
    private String vip;
    private String expires_in;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIs_yellow_vip() {
        return is_yellow_vip;
    }

    public void setIs_yellow_vip(String is_yellow_vip) {
        this.is_yellow_vip = is_yellow_vip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_yellow_year_vip() {
        return is_yellow_year_vip;
    }

    public void setIs_yellow_year_vip(String is_yellow_year_vip) {
        this.is_yellow_year_vip = is_yellow_year_vip;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getYellow_vip_level() {
        return yellow_vip_level;
    }

    public void setYellow_vip_level(String yellow_vip_level) {
        this.yellow_vip_level = yellow_vip_level;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "QQLoginEntity{" +
                "msg='" + msg + '\'' +
                ", ret='" + ret + '\'' +
                ", unionid='" + unionid + '\'' +
                ", gender='" + gender + '\'' +
                ", is_yellow_vip='" + is_yellow_vip + '\'' +
                ", city='" + city + '\'' +
                ", level='" + level + '\'' +
                ", openid='" + openid + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", access_token='" + access_token + '\'' +
                ", uid='" + uid + '\'' +
                ", is_yellow_year_vip='" + is_yellow_year_vip + '\'' +
                ", province='" + province + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", name='" + name + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", yellow_vip_level='" + yellow_vip_level + '\'' +
                ", expiration='" + expiration + '\'' +
                ", vip='" + vip + '\'' +
                ", expires_in='" + expires_in + '\'' +
                '}';
    }
}
