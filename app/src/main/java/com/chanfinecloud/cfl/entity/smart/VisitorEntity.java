package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/29.
 * Version: 1.0
 * Describe:
 */
public class VisitorEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id : 1708f574269144168586f704d14b23c5
     * phaseId :
     * cardNo :
     * visitorName : zxl
     * effectTime : 2020-02-29 12:39:00
     * expireTime : 2020-02-29 16:39:00
     * openTimes : 4
     * qrcodeUrl : https://pic.hik-cloud.com/poseidon/cff3b073d0604d8abacec71dd9f0e844/1582952824104.jpg
     * createBy :
     * createTime : 2020-02-29 13:07:05
     */

    private String id;
    private String phaseId;
    private String cardNo;
    private String visitorName;
    private String effectTime;
    private String expireTime;
    private int openTimes;
    private String qrcodeUrl;
    private String createBy;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(String effectTime) {
        this.effectTime = effectTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getOpenTimes() {
        return openTimes;
    }

    public void setOpenTimes(int openTimes) {
        this.openTimes = openTimes;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
