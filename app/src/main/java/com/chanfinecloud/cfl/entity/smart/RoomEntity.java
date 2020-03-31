package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/27.
 * Version: 1.0
 * Describe:
 */
public class RoomEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 16f3ac1665814320b80f1e94825be615
     * unitId : ec93bb06f5be4c1f19522ca78180e2i6
     * unitName : 三单元
     * name : 1003
     * code : 123123
     * floor : 12
     * number : 1003
     * floorNo : 10
     * roomNo : 3
     * propertySpace : 123
     * usingArea : 123
     * paymentMode : QUARTER
     * paymentModeDisplay :
     * checkinStatus : 已入住
     * checkinStatusDisplay :
     * status : 0
     * description :
     * apartment : SS
     * apartmentDisplay :
     * purpose :
     * orientation : S
     * orientationDisplay :
     * decorationStandard : JZ
     * decorationStandardDisplay :
     * propertyNo :
     * propertyName :
     * propertyMark :
     * createBy :
     * updateBy : a75d45a015c44384a04449ee80dc3503
     * createTime : 2019-12-25 09:52:31
     * updateTime : 2020-02-29 11:24:12
     * projectId : ec93bb06f5be4c1f19522ca78180e2i9
     * projectName : 长房时代城
     * phaseId : ec93bb06f5be4c1f19522ca78180e2i8
     * phaseName : 二期
     * buildingId : ec93bb06f5be4c1f19522ca78180e2i7
     * buildingName : 8栋
     * fullName : 8栋-三单元-1003
     * householdType :
     * householdTypeDisplay :
     * roomBoMap :
     */

    private String id;
    private String unitId;
    private String unitName;
    private String name;
    private String code;
    private String floor;
    private String number;
    private String floorNo;
    private String roomNo;
    private double propertySpace;
    private double usingArea;
    private String paymentMode;
    private String paymentModeDisplay;
    private String checkinStatus;
    private String checkinStatusDisplay;
    private int status;
    private String description;
    private String apartment;
    private String apartmentDisplay;
    private String purpose;
    private String orientation;
    private String orientationDisplay;
    private String decorationStandard;
    private String decorationStandardDisplay;
    private String propertyNo;
    private String propertyName;
    private String propertyMark;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private String projectId;
    private String projectName;
    private String phaseId;
    private String phaseName;
    private String buildingId;
    private String buildingName;
    private String fullName;
    private String householdType;
    private String householdTypeDisplay;
    private String roomBoMap;
    private List<RoomHouseholdEntity> householdBoList;

    public List<RoomHouseholdEntity> getHouseholdBoList() {
        return householdBoList;
    }

    public void setHouseholdBoList(List<RoomHouseholdEntity> householdBoList) {
        this.householdBoList = householdBoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public double getPropertySpace() {
        return propertySpace;
    }

    public void setPropertySpace(double propertySpace) {
        this.propertySpace = propertySpace;
    }

    public double getUsingArea() {
        return usingArea;
    }

    public void setUsingArea(double usingArea) {
        this.usingArea = usingArea;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentModeDisplay() {
        return paymentModeDisplay;
    }

    public void setPaymentModeDisplay(String paymentModeDisplay) {
        this.paymentModeDisplay = paymentModeDisplay;
    }

    public String getCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(String checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getCheckinStatusDisplay() {
        return checkinStatusDisplay;
    }

    public void setCheckinStatusDisplay(String checkinStatusDisplay) {
        this.checkinStatusDisplay = checkinStatusDisplay;
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

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getApartmentDisplay() {
        return apartmentDisplay;
    }

    public void setApartmentDisplay(String apartmentDisplay) {
        this.apartmentDisplay = apartmentDisplay;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientationDisplay() {
        return orientationDisplay;
    }

    public void setOrientationDisplay(String orientationDisplay) {
        this.orientationDisplay = orientationDisplay;
    }

    public String getDecorationStandard() {
        return decorationStandard;
    }

    public void setDecorationStandard(String decorationStandard) {
        this.decorationStandard = decorationStandard;
    }

    public String getDecorationStandardDisplay() {
        return decorationStandardDisplay;
    }

    public void setDecorationStandardDisplay(String decorationStandardDisplay) {
        this.decorationStandardDisplay = decorationStandardDisplay;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyMark() {
        return propertyMark;
    }

    public void setPropertyMark(String propertyMark) {
        this.propertyMark = propertyMark;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(String householdType) {
        this.householdType = householdType;
    }

    public String getHouseholdTypeDisplay() {
        return householdTypeDisplay;
    }

    public void setHouseholdTypeDisplay(String householdTypeDisplay) {
        this.householdTypeDisplay = householdTypeDisplay;
    }

    public String getRoomBoMap() {
        return roomBoMap;
    }

    public void setRoomBoMap(String roomBoMap) {
        this.roomBoMap = roomBoMap;
    }
}
