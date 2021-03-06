package com.chanfinecloud.cfl.adapter.smart;

import com.chanfinecloud.cfl.entity.smart.ResourceEntity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/2.
 * Version: 1.0
 * Describe:
 */
public class CarEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 17061bc9b8839b7fbcad08b4b009d594
     * createBy : admin
     * updateBy :
     * createTime : 2020-02-20 16:35:15
     * updateTime :
     * householdId : 16fa73e5607ee21f4ff5e0842e7b5d4c
     * roomId : 16fa72dd4e7034b8aa5a2af40d1972ce
     * plateNO : 川A123AB
     * ownerPhone : 13786099183
     * ownerName : 好啊
     * vehicleType : 1
     * vehicleImageId
     * vehicleColor : WHITE
     * plateType : BZMYC
     * plateColor : BLUE
     * parkingAddress : 128好
     * parkingNO : 123456
     * parkStartdate :
     * parkEnddate :
     * status : 0 字段状态(0-正常,1-冻结,2-删除)
     * description :
     * auditStatus : 0  审核状态（0 未审核，1审核通过）
     * householdName : 马冬梅
     * projectName : 长房明宸府
     * mobile : 13687954685
     * projectId : 16f274b06fb2f8c87eb107841bfbbc05
     * periodTime :
     * nickName : 马冬梅
     * due :
     * type : 1
     * vehicleImageResource
     */

    private String id;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private String householdId;
    private String plateNO;
    private String ownerPhone;
    private String ownerName;
    private String vehicleType;
    private String vehicleImageId;
    private String vehicleColor;
    private String plateType;
    private String plateColor;
    private String parkingAddress;
    private String parkingNO;
    private String startTime;
    private String endTime;
    private int status;
    private String description;
    private int auditStatus;
    private String householdName;
    private String projectName;
    private String mobile;
    private String projectId;
    private String nickName;
    private String overDue;
    private int type;
    private ResourceEntity vehicleImageResource;

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

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getPlateNO() {
        return plateNO;
    }

    public void setPlateNO(String plateNO) {
        this.plateNO = plateNO;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleImageId() {
        return vehicleImageId;
    }

    public void setVehicleImageId(String vehicleImageId) {
        this.vehicleImageId = vehicleImageId;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getParkingNO() {
        return parkingNO;
    }

    public void setParkingNO(String parkingNO) {
        this.parkingNO = parkingNO;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ResourceEntity getVehicleImageResource() {
        return vehicleImageResource;
    }

    public void setVehicleImageResource(ResourceEntity vehicleImageResource) {
        this.vehicleImageResource = vehicleImageResource;
    }
}
