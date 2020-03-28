package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/7/5.
 * Describe:
 */
public class WeiXinLoginEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * country : 中国
     * unionid : on-4W5lb4AkWIPKdKYma9QM4LcGw
     * gender : 男
     * city : 长沙
     * openid : oLj_85mFN3ZZxkOikzgbdsx9qQCw
     * language : zh_CN
     * profile_image_url : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epoiaQwerOlibW8eKZ0TmcXbLDXjArx1MWegUGWIn4KXBktYLJ8lSy1weyn2XD3vXk7ibFIIRSkaxFPg/132
     * accessToken : 23_JdGvSUoEv4egA7jWdjOxF9RKaQbWhKu7wbyYqaBVNwXvj_CwihdpdYwM4AN-k_kP0a18HevwzgJXUnO8TRrhpRCqglaZ7uN7XiMhNXyqoMQ
     * access_token : 23_JdGvSUoEv4egA7jWdjOxF9RKaQbWhKu7wbyYqaBVNwXvj_CwihdpdYwM4AN-k_kP0a18HevwzgJXUnO8TRrhpRCqglaZ7uN7XiMhNXyqoMQ
     * uid : on-4W5lb4AkWIPKdKYma9QM4LcGw
     * province : 湖南
     * screen_name : 曾晓龙
     * name : 曾晓龙
     * iconurl : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epoiaQwerOlibW8eKZ0TmcXbLDXjArx1MWegUGWIn4KXBktYLJ8lSy1weyn2XD3vXk7ibFIIRSkaxFPg/132
     * expiration : 1562300648324
     * expires_in : 1562300648324
     * refreshToken : 23_-C7bPw8JFnDasYwj2jQRBnjtNmGEnn39F1iLoNxZCKNGDMm_b2RehO6lRShvMeXgxE1KX8tafjYOHdijWsOo5U1RrYd_tYhrTLyxiGG9psk
     */

    private String country;
    private String unionid;
    private String gender;
    private String city;
    private String openid;
    private String language;
    private String profile_image_url;
    private String accessToken;
    private String access_token;
    private String uid;
    private String province;
    private String screen_name;
    private String name;
    private String iconurl;
    private String expiration;
    private String expires_in;
    private String refreshToken;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "WeiXinLoginEntity{" +
                "country='" + country + '\'' +
                ", unionid='" + unionid + '\'' +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                ", openid='" + openid + '\'' +
                ", language='" + language + '\'' +
                ", profile_image_url='" + profile_image_url + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", access_token='" + access_token + '\'' +
                ", uid='" + uid + '\'' +
                ", province='" + province + '\'' +
                ", screen_name='" + screen_name + '\'' +
                ", name='" + name + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", expiration='" + expiration + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
