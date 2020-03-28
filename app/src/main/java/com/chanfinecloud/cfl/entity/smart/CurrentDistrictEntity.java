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
     * householdId : 170d7d64b85a488d3f7a70644ca9919c
     * createBy :
     * createTime :
     * updateBy :
     * phaseId :
     * unitId :
     * updateTime :
     * id : 170e8291888d053b21f009745288334d
     * projectId : ec93bb06f5be4c1f19522ca78180e2i9
     * projectName :
     * roomId :
     * buildingId :
     */

    private String householdId;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String phaseId;
    private String unitId;
    private String updateTime;
    private String id;
    private String projectId;
    private String projectName;
    private String roomId;
    private String buildingId;

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
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

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }
}
