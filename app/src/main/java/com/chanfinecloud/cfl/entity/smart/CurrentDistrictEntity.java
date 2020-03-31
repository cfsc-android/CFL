package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/18.
 * Version: 1.0
 * Describe:
 */
public class CurrentDistrictEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * unitName : 三单元
     * phaseId : ec93bb06f5be4c1f19522ca78180e2i8
     * updateTime :
     * roomId : ec93bb06f5be4c1f19522ca78180e2i4
     * roomName : 1001
     * buildingId : ec93bb06f5be4c1f19522ca78180e2i7
     * householdId : 170ed4bfcdf485f34cde2654e988fc47
     * buildingName : 8栋
     * createBy :
     * createTime :
     * updateBy :
     * unitId : ec93bb06f5be4c1f19522ca78180e2i6
     * id : 170f208185354efefafaea541168fcb4
     * projectName : 长房时代城
     * projectId : ec93bb06f5be4c1f19522ca78180e2i9
     * phaseName : 二期
     */

    private String unitName;
    private String phaseId;
    private String updateTime;
    private String roomId;
    private String roomName;
    private String buildingId;
    private String householdId;
    private String buildingName;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String unitId;
    private String id;
    private String projectName;
    private String projectId;
    private String phaseName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }
}
