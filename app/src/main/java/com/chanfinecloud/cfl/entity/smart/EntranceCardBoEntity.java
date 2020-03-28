package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/27.
 * Version: 1.0
 * Describe:
 */
public class EntranceCardBoEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 16f644472aeb92b7ea051a5424989661
     * householdId : 16f466fe301435f01e70c1845278eae1
     * cardNo : 2020
     * cardType : 4
     * sendStatus : false
     */

    private String id;
    private String householdId;
    private String cardNo;
    private String cardType;
    private boolean sendStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public boolean isSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(boolean sendStatus) {
        this.sendStatus = sendStatus;
    }
}
