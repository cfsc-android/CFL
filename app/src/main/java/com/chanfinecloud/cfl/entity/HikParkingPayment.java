package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

public class HikParkingPayment implements Serializable {
    public static final long serialVersionUID=1L;


    /**
     * parkSyscode : h356j246h245gh245h245
     * chargeRuleSyscode : g245h245h245h245g25g5
     * parkName : 停车场 1
     * inRecordSyscode : gw4tgh245hg45hg245ghg45
     * enterTime : 2018-07-26T15:00:00+08:00
     * vehiclePicUri : /pic?=d7ei703i10cd*73a-d5108a-- 22cd0c9d6592ai2b4*=3d4id=
     * plateNoPicUri : /pic?=d7ei703i10cd*73a-d5108a-- 22cd0c9d6592ai2b4*=3d4id=
     * aswSyscode : brgshw45hq4gh45h45gh354g5g
     * plateNo : 浙 A12345
     * cardNo : 634565623
     * parkTime : 30
     * billSyscode : b456h45h5yhy245h45h5g45yh
     * calcType : 1
     * supposeCost : 3.00
     * deduceCost : 1.00
     * paidCost : 2.00
     * totalCost : 3.00
     * isUsedCoupon : 1
     * couponCode : vffg34g35g345g234g34ggf34
     * couponUsedMsg :
     * delayTime : 30
     */

    private String parkSyscode;
    private String chargeRuleSyscode;
    private String parkName;
    private String inRecordSyscode;
    private String enterTime;
    private String vehiclePicUri;
    private String plateNoPicUri;
    private String aswSyscode;
    private String plateNo;
    private String cardNo;
    private int parkTime;
    private String billSyscode;
    private int calcType;
    private String supposeCost;
    private String deduceCost;
    private String paidCost;
    private String totalCost;
    private int isUsedCoupon;
    private String couponCode;
    private String couponUsedMsg;
    private int delayTime;

    public String getParkSyscode() {
        return parkSyscode;
    }

    public void setParkSyscode(String parkSyscode) {
        this.parkSyscode = parkSyscode;
    }

    public String getChargeRuleSyscode() {
        return chargeRuleSyscode;
    }

    public void setChargeRuleSyscode(String chargeRuleSyscode) {
        this.chargeRuleSyscode = chargeRuleSyscode;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getInRecordSyscode() {
        return inRecordSyscode;
    }

    public void setInRecordSyscode(String inRecordSyscode) {
        this.inRecordSyscode = inRecordSyscode;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
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

    public String getAswSyscode() {
        return aswSyscode;
    }

    public void setAswSyscode(String aswSyscode) {
        this.aswSyscode = aswSyscode;
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

    public int getParkTime() {
        return parkTime;
    }

    public void setParkTime(int parkTime) {
        this.parkTime = parkTime;
    }

    public String getBillSyscode() {
        return billSyscode;
    }

    public void setBillSyscode(String billSyscode) {
        this.billSyscode = billSyscode;
    }

    public int getCalcType() {
        return calcType;
    }

    public void setCalcType(int calcType) {
        this.calcType = calcType;
    }

    public String getSupposeCost() {
        return supposeCost;
    }

    public void setSupposeCost(String supposeCost) {
        this.supposeCost = supposeCost;
    }

    public String getDeduceCost() {
        return deduceCost;
    }

    public void setDeduceCost(String deduceCost) {
        this.deduceCost = deduceCost;
    }

    public String getPaidCost() {
        return paidCost;
    }

    public void setPaidCost(String paidCost) {
        this.paidCost = paidCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public int getIsUsedCoupon() {
        return isUsedCoupon;
    }

    public void setIsUsedCoupon(int isUsedCoupon) {
        this.isUsedCoupon = isUsedCoupon;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponUsedMsg() {
        return couponUsedMsg;
    }

    public void setCouponUsedMsg(String couponUsedMsg) {
        this.couponUsedMsg = couponUsedMsg;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }
}



