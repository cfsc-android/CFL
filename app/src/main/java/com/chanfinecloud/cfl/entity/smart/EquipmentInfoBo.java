package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/27.
 * Version: 1.0
 * Describe:
 */
public class EquipmentInfoBo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * platformEquipId : 9d3108afb0bf4536be8494f9504c89b7
     * unitId :
     * phaseId :
     * vendorId :
     * groupId :
     * name : 一栋一单元门口
     * code :
     * type :
     * model :
     * location :
     * installTime :
     * deliverDate :
     * warrantyDate :
     * iotOrNot : false
     * iotConfig : [{"code":"deviceSerial","text":"序列号","value":"D16444599"},{"code":"validateCode","text":"验证码","value":"RNIQCE"}]
     * online : true
     * status : 0
     * description :
     */

    private String platformEquipId;
    private String unitId;
    private String phaseId;
    private String vendorId;
    private String groupId;
    private String name;
    private String code;
    private String type;
    private String model;
    private String location;
    private String installTime;
    private String deliverDate;
    private String warrantyDate;
    private boolean iotOrNot;
    private String iotConfig;
    private boolean online;
    private int status;
    private String description;

    public String getPlatformEquipId() {
        return platformEquipId;
    }

    public void setPlatformEquipId(String platformEquipId) {
        this.platformEquipId = platformEquipId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInstallTime() {
        return installTime;
    }

    public void setInstallTime(String installTime) {
        this.installTime = installTime;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public boolean isIotOrNot() {
        return iotOrNot;
    }

    public void setIotOrNot(boolean iotOrNot) {
        this.iotOrNot = iotOrNot;
    }

    public String getIotConfig() {
        return iotConfig;
    }

    public void setIotConfig(String iotConfig) {
        this.iotConfig = iotConfig;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
