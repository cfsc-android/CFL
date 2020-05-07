package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 流程实体
 */
public class WorkflowEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 170c894ae6cd0e72f3fd1c248f9bfae1
     * processId : 170cdbff1fd3ffba96c768b456abc0e9
     * problemDesc : 工单走起
     * problemResourceKey :
     * liveResourceKey :
     * typeId : 4
     * typeName : 综合维修
     * statusId : 9
     * statusName : 员工检视工单
     * createBy : a75d45a015c44384a04449ee80dc3503
     * createTime : 2020-03-11 15:52:32
     * creator : 超级管理员
     * avatarUrl : http://img3.imgtn.bdimg.com/it/u=378824344,1185609431&fm=26&gp=0.jpg
     * creatorMobile : 13787176775
     * briefDesc : 工程部
     * handlerId :
     * nodeName : 到场拍照
     * assignTime : 2020-03-12 15:57:54
     */
    private String id;
    private Object processId;
    private String problemDesc;
    private String code;
    private String projectId;
    private String projectName;
    private String problemResourceKey;
    private String liveResourceKey;
    private int typeId;
    private String typeName;
    private int statusId;
    private String statusName;
    private String householdId;
    private String createTime;
    private String householdName;
    private String householdMobile;
    private String address;
    private String assigneeId;
    private String nodeName;
    private String assignTime;
    private List<ResourceEntity> problemResourceValue;
    private List<ResourceEntity> liveResourceValue;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getProcessId() {
        return processId;
    }

    public void setProcessId(Object processId) {
        this.processId = processId;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getProblemResourceKey() {
        return problemResourceKey;
    }

    public void setProblemResourceKey(String problemResourceKey) {
        this.problemResourceKey = problemResourceKey;
    }

    public String getLiveResourceKey() {
        return liveResourceKey;
    }

    public void setLiveResourceKey(String liveResourceKey) {
        this.liveResourceKey = liveResourceKey;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public String getHouseholdMobile() {
        return householdMobile;
    }

    public void setHouseholdMobile(String householdMobile) {
        this.householdMobile = householdMobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public List<ResourceEntity> getProblemResourceValue() {
        return problemResourceValue;
    }

    public void setProblemResourceValue(List<ResourceEntity> problemResourceValue) {
        this.problemResourceValue = problemResourceValue;
    }

    public List<ResourceEntity> getLiveResourceValue() {
        return liveResourceValue;
    }

    public void setLiveResourceValue(List<ResourceEntity> liveResourceValue) {
        this.liveResourceValue = liveResourceValue;
    }
}
