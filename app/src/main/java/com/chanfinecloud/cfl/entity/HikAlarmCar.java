package com.chanfinecloud.cfl.entity;


import java.io.Serializable;

public class HikAlarmCar implements Serializable {
    public static final long serialVersionUID=1L;

    private String alarmSyscode;
    private String plateNo;
    private String cardNo;
    private String driver;
    private String driverPhone;
    private String remark;
    private String beginTime;
    private String endTime;

    public String getAlarmSyscode() {
        return alarmSyscode;
    }

    public void setAlarmSyscode(String alarmSyscode) {
        this.alarmSyscode = alarmSyscode;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
