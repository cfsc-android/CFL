package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工作流实体类
 */
public class WorkflowProcessesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 170d32863ebf82f3a30c75f4363a88ff
     * handlerId : a75d45a015c44384a04449ee80dc3503
     * handlerName : 超级管理员
     * assigneeId :
     * avatarUrl : http://img3.imgtn.bdimg.com/it/u=378824344,1185609431&fm=26&gp=0.jpg
     * handlerType : 员工
     * handlerMobile : 13787176775
     * nodeName : 客服中心确认工单
     * operation : 客服接单
     * resourceKey :
     * notAcceptable : 接受
     * remark :
     * shortDesc : 工程部
     * createTime : 2020-03-13 17:10:05
     * updateTime
     * processResult :
     */

    private String id;
    private String handlerId;
    private String handlerName;
    private String assigneeId;
    private String avatarUrl;
    private String handlerType;
    private String handlerMobile;
    private String nodeName;
    private String operation;
    private String resourceKey;
    private String notAcceptable;
    private String remark;
    private String briefDesc;
    private String createTime;
    private String updateTime;
    private String processResult;
    private List<ResourceEntity> resourceValue;
    private List<OperationInfoEntity> operationInfos;
    private List<WorkflowFormContentEntity> formContent;

    public List<WorkflowFormContentEntity> getFormContent() {
        return formContent;
    }

    public void setFormContent(List<WorkflowFormContentEntity> formContent) {
        this.formContent = formContent;
    }
    public List<ResourceEntity> getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(List<ResourceEntity> resourceValue) {
        this.resourceValue = resourceValue;
    }

    public List<OperationInfoEntity> getOperationInfos() {
        return operationInfos;
    }

    public void setOperationInfos(List<OperationInfoEntity> operationInfos) {
        this.operationInfos = operationInfos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getHandlerType() {
        return handlerType;
    }

    public void setHandlerType(String handlerType) {
        this.handlerType = handlerType;
    }

    public String getHandlerMobile() {
        return handlerMobile;
    }

    public void setHandlerMobile(String handlerMobile) {
        this.handlerMobile = handlerMobile;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getNotAcceptable() {
        return notAcceptable;
    }

    public void setNotAcceptable(String notAcceptable) {
        this.notAcceptable = notAcceptable;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
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

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }
}
