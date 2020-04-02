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
    private String processId;
    private String problemDesc;
    private String problemResourceKey;
    private String liveResourceKey;
    private int typeId;
    private String typeName;
    private int statusId;
    private String statusName;
    private String createBy;
    private String createTime;
    private String creator;
    private String avatarUrl;
    private String creatorMobile;
    private String briefDesc;
    private String handlerId;
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

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCreatorMobile() {
        return creatorMobile;
    }

    public void setCreatorMobile(String creatorMobile) {
        this.creatorMobile = creatorMobile;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
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
