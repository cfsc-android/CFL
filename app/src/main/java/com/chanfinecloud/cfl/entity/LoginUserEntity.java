package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * 此类描述的是:登录用户返回数据实体
 *
 * @author TanYong
 *         create at 2017/4/20 23:10
 */
public class LoginUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;//用户ID
    private String gender;//性别:1、男；2、女
    private String headImageUrl;//用户头像
    private String mobileNumber;//用户号码
    private String nickName;//用户昵称
    private String commonAddress;//地址
    private String receiptAddress;//我的收货地址

    private String projectId;

    private String cardNo;
    private String token;
    private String vehicleCode;
    private String deviceInfo;
    private String faceDisUrl;
    private String faceDisTime;

    public String getFaceDisUrl() {
        return faceDisUrl;
    }

    public void setFaceDisUrl(String faceDisUrl) {
        this.faceDisUrl = faceDisUrl;
    }

    public String getFaceDisTime() {
        return faceDisTime;
    }

    public void setFaceDisTime(String faceDisTime) {
        this.faceDisTime = faceDisTime;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCommonAddress() {
        return commonAddress;
    }

    public void setCommonAddress(String commonAddress) {
        this.commonAddress = commonAddress;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    @Override
    public String toString() {
        return "LoginUserEntity{" +
                "userId='" + userId + '\'' +
                ", gender='" + gender + '\'' +
                ", headImageUrl='" + headImageUrl + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", nickName='" + nickName + '\'' +
                ", commonAddress='" + commonAddress + '\'' +
                ", receiptAddress='" + receiptAddress + '\'' +
                ", projectId='" + projectId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", token='" + token + '\'' +
                ", vehicleCode='" + vehicleCode + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", faceDisUrl='" + faceDisUrl + '\'' +
                ", faceDisTime='" + faceDisTime + '\'' +
                '}';
    }
}
