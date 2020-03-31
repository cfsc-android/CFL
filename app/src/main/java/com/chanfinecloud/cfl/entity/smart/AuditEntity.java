package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/21.
 * Version: 1.0
 * Describe:
 */
public class AuditEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 170f5e89b6fbb1cbc236ef24c749bbd6
     * createBy : a75d45a015c44384a04449ee80dc3503
     * updateBy :
     * createTime : 2020-03-20 11:07:07
     * updateTime :
     * mobile : 18073667908
     * type : JS
     * roomId : ec93bb06f5be4c1f19522ca78180e2i4
     * householdId :
     * idcardNo : 123456789+++
     * faceId :
     * status : 1
     * remark : 电话地洞
     * delFlag : 0
     */

    private String id;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private String mobile;
    private String type;
    private String roomId;
    private String householdId;
    private String idcardNo;
    private String faceId;
    private int status;
    private String remark;
    private int delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
