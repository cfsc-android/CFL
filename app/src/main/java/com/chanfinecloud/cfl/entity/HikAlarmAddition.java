package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

public class HikAlarmAddition implements Serializable {
    public static final long serialVersionUID=1L;

    /**
     * alarmSyscode : g3g5hy25t234t45y22t34
     * plateNo : 浙A12345
     * cardNo :
     */

    private String alarmSyscode;
    private String plateNo;
    private String cardNo;

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
}
