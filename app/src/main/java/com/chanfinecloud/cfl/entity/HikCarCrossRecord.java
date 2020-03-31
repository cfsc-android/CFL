package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

public class HikCarCrossRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * crossRecordSyscode : g45h245gh235g2354g
     * parkSyscode : gqwerg354g345g35g
     * parkName : 停车库 停车库 1
     * entranceSyscode : rggergqw45ghw45gb
     * entranceName : 出入口 出入口 1
     * vehicleOut : 1
     * releaseMode : 1
     * plateNo : 浙 A12345
     * cardNo : 54523451
     * vehicleColor : 1
     * vehicleType : 1
     * vehiclePicUri : /pic?=d7ei703i10cd*73a-d5108a-- 22cd0c9d6592aiid=
     * plateNoPicUri : /pic?=d7ei703i10cd*73a-d5108a-- 22cd0c9d6592aiid=
     * facePicUri : /pic?=d7ei703i10cd*73a-d5108a-- 22cd0c9d6592aiid=
     * aswSyscode : h4h45y13ty23hg24h
     * crossTime : 2018-07-26T15:00:00+08:00
     * createTime : 2018-07-26T15:00:00+08:00
     */

    private String crossRecordSyscode;
    private String parkSyscode;
    private String parkName;
    private String entranceSyscode;
    private String entranceName;
    private int vehicleOut;
    private int releaseMode;
    private String plateNo;
    private String cardNo;
    private int vehicleColor;
    private int vehicleType;
    private String vehiclePicUri;
    private String plateNoPicUri;
    private String facePicUri;
    private String aswSyscode;
    private String crossTime;
    private String createTime;

    public String getCrossRecordSyscode() {
        return crossRecordSyscode;
    }

    public void setCrossRecordSyscode(String crossRecordSyscode) {
        this.crossRecordSyscode = crossRecordSyscode;
    }

    public String getParkSyscode() {
        return parkSyscode;
    }

    public void setParkSyscode(String parkSyscode) {
        this.parkSyscode = parkSyscode;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getEntranceSyscode() {
        return entranceSyscode;
    }

    public void setEntranceSyscode(String entranceSyscode) {
        this.entranceSyscode = entranceSyscode;
    }

    public String getEntranceName() {
        return entranceName;
    }

    public void setEntranceName(String entranceName) {
        this.entranceName = entranceName;
    }

    public int getVehicleOut() {
        return vehicleOut;
    }

    public void setVehicleOut(int vehicleOut) {
        this.vehicleOut = vehicleOut;
    }

    public int getReleaseMode() {
        return releaseMode;
    }

    public void setReleaseMode(int releaseMode) {
        this.releaseMode = releaseMode;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(int vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehiclePicUri() {
        return vehiclePicUri;
    }

    public void setVehiclePicUri(String vehiclePicUri) {
        this.vehiclePicUri = vehiclePicUri;
    }

    public String getPlateNoPicUri() {
        return plateNoPicUri;
    }

    public void setPlateNoPicUri(String plateNoPicUri) {
        this.plateNoPicUri = plateNoPicUri;
    }

    public String getFacePicUri() {
        return facePicUri;
    }

    public void setFacePicUri(String facePicUri) {
        this.facePicUri = facePicUri;
    }

    public String getAswSyscode() {
        return aswSyscode;
    }

    public void setAswSyscode(String aswSyscode) {
        this.aswSyscode = aswSyscode;
    }

    public String getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(String crossTime) {
        this.crossTime = crossTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
