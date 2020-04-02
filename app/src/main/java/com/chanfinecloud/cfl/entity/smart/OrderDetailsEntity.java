package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/19.
 * Version: 1.0
 * Describe: 工单详情实体类
 */
public class OrderDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id : 170d2a6616400d4945581664ce1b94f6
     * createTime : 2020-03-13 14:48:04
     * createBy : a75d45a015c44384a04449ee80dc3503
     * code : 202003130001
     * statusId : 11
     * typeId : 3
     * customerService : a75d45a015c44384a04449ee80dc3503
     * departmentLeader : 16f82eb48735ade136117ef41b187bfa
     * lastHandler : a75d45a015c44384a04449ee80dc3503
     * lastHandleTime : 2020-03-13 15:15:27
     * createType : 1
     * finishTime :
     * serviceAcceptTime : 2020-03-13 14:49:34
     * serviceRejectTime :
     * serviceRejectReason :
     * remark : 备注
     * expectTimeText :
     * householdId : 16fa73e5607ee21f4ff5e0842e7b5d4c
     * roomId : 16fa72dd4e7034b8aa5a2af40d1972ce
     * projectId : 16f274b06fb2f8c87eb107841bfbbc05
     * problemResourceKey :
     * mobile : 13412340007
     * problemDesc : 新工单流程测试
     * linkMan : 李锦记
     * manualCost :
     * materialsCost :
     * chargeTime :
     * processInstanceId : 765005
     * expectTime :
     * handleStatus : 1
     * liveResourceKey :
     * liveDesc :
     * liveTime :
     * handleDesc :
     * handleTime :
     * isFinish : 0
     * reportType : 1
     * solveStatus : 0
     * closeType :
     * closeTime :
     * closeDesc :
     * comment :
     * commentLevel :
     * handleResourceKey :
     * employeeRejectReason :
     * employeeRejectTime :
     * employeeRejectResourceKey :
     * address : Mars
     * householdName : 马冬梅
     * householdMobile : 13647441096
     * taskInfoVo :
     * workTypeName : 厨卫维修
     * workStatusName : 接单
     * briefDesc : 1栋 一单元 3202
     * phaseName : 一期
     * phaseId : 16f27973c392f54863e7b90438cac119
     * projectName : 长房明宸府
     * lastHandlerName : 超级管理员
     * createName : 超级管理员
     * creatorAvatarUrl : http://img3.imgtn.bdimg.com/it/u=378824344,1185609431&fm=26&gp=0.jpg
     * customerServiceName : 超级管理员
     * departmentLeaderName : 鲁班
     */

    private String id;
    private String createTime;
    private String createBy;
    private String code;
    private int statusId;
    private int typeId;
    private String customerService;
    private String departmentLeader;
    private String lastHandler;
    private String lastHandleTime;
    private int createType;
    private String finishTime;
    private String serviceAcceptTime;
    private String serviceRejectTime;
    private String serviceRejectReason;
    private String remark;
    private String expectTimeText;
    private String householdId;
    private String roomId;
    private String projectId;
    private String problemResourceKey;
    private String mobile;
    private String problemDesc;
    private String linkMan;
    private String manualCost;
    private String materialsCost;
    private String chargeTime;
    private String processInstanceId;
    private String expectTime;
    private int handleStatus;
    private String liveResourceKey;
    private String liveDesc;
    private String liveTime;
    private String handleDesc;
    private String handleTime;
    private int isFinish;
    private int reportType;
    private int solveStatus;
    private String closeType;
    private String closeTime;
    private String closeDesc;
    private String comment;
    private String commentLevel;
    private String handleResourceKey;
    private String employeeRejectReason;
    private String employeeRejectTime;
    private String employeeRejectResourceKey;
    private String address;
    private String householdName;
    private String householdMobile;
    private String taskInfoVo;
    private String workTypeName;
    private String workStatusName;
    private String briefDesc;
    private String phaseName;
    private String phaseId;
    private String projectName;
    private String lastHandlerName;
    private String createName;
    private String creatorAvatarUrl;
    private String customerServiceName;
    private String departmentLeaderName;
    private List<ResourceEntity> resourceValue;
    private List<WorkflowProcessesEntity> processes;


    public List<ResourceEntity> getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(List<ResourceEntity> resourceValue) {
        this.resourceValue = resourceValue;
    }

    public List<WorkflowProcessesEntity> getProcesses() {
        return processes;
    }

    public void setProcesses(List<WorkflowProcessesEntity> processes) {
        this.processes = processes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCustomerService() {
        return customerService;
    }

    public void setCustomerService(String customerService) {
        this.customerService = customerService;
    }

    public String getDepartmentLeader() {
        return departmentLeader;
    }

    public void setDepartmentLeader(String departmentLeader) {
        this.departmentLeader = departmentLeader;
    }

    public String getLastHandler() {
        return lastHandler;
    }

    public void setLastHandler(String lastHandler) {
        this.lastHandler = lastHandler;
    }

    public String getLastHandleTime() {
        return lastHandleTime;
    }

    public void setLastHandleTime(String lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
    }

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getServiceAcceptTime() {
        return serviceAcceptTime;
    }

    public void setServiceAcceptTime(String serviceAcceptTime) {
        this.serviceAcceptTime = serviceAcceptTime;
    }

    public String getServiceRejectTime() {
        return serviceRejectTime;
    }

    public void setServiceRejectTime(String serviceRejectTime) {
        this.serviceRejectTime = serviceRejectTime;
    }

    public String getServiceRejectReason() {
        return serviceRejectReason;
    }

    public void setServiceRejectReason(String serviceRejectReason) {
        this.serviceRejectReason = serviceRejectReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExpectTimeText() {
        return expectTimeText;
    }

    public void setExpectTimeText(String expectTimeText) {
        this.expectTimeText = expectTimeText;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProblemResourceKey() {
        return problemResourceKey;
    }

    public void setProblemResourceKey(String problemResourceKey) {
        this.problemResourceKey = problemResourceKey;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getManualCost() {
        return manualCost;
    }

    public void setManualCost(String manualCost) {
        this.manualCost = manualCost;
    }

    public String getMaterialsCost() {
        return materialsCost;
    }

    public void setMaterialsCost(String materialsCost) {
        this.materialsCost = materialsCost;
    }

    public String getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(String chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(String expectTime) {
        this.expectTime = expectTime;
    }

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getLiveResourceKey() {
        return liveResourceKey;
    }

    public void setLiveResourceKey(String liveResourceKey) {
        this.liveResourceKey = liveResourceKey;
    }

    public String getLiveDesc() {
        return liveDesc;
    }

    public void setLiveDesc(String liveDesc) {
        this.liveDesc = liveDesc;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getHandleDesc() {
        return handleDesc;
    }

    public void setHandleDesc(String handleDesc) {
        this.handleDesc = handleDesc;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public int getSolveStatus() {
        return solveStatus;
    }

    public void setSolveStatus(int solveStatus) {
        this.solveStatus = solveStatus;
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCloseDesc() {
        return closeDesc;
    }

    public void setCloseDesc(String closeDesc) {
        this.closeDesc = closeDesc;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getHandleResourceKey() {
        return handleResourceKey;
    }

    public void setHandleResourceKey(String handleResourceKey) {
        this.handleResourceKey = handleResourceKey;
    }

    public String getEmployeeRejectReason() {
        return employeeRejectReason;
    }

    public void setEmployeeRejectReason(String employeeRejectReason) {
        this.employeeRejectReason = employeeRejectReason;
    }

    public String getEmployeeRejectTime() {
        return employeeRejectTime;
    }

    public void setEmployeeRejectTime(String employeeRejectTime) {
        this.employeeRejectTime = employeeRejectTime;
    }

    public String getEmployeeRejectResourceKey() {
        return employeeRejectResourceKey;
    }

    public void setEmployeeRejectResourceKey(String employeeRejectResourceKey) {
        this.employeeRejectResourceKey = employeeRejectResourceKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTaskInfoVo() {
        return taskInfoVo;
    }

    public void setTaskInfoVo(String taskInfoVo) {
        this.taskInfoVo = taskInfoVo;
    }

    public String getWorkTypeName() {
        return workTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        this.workTypeName = workTypeName;
    }

    public String getWorkStatusName() {
        return workStatusName;
    }

    public void setWorkStatusName(String workStatusName) {
        this.workStatusName = workStatusName;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLastHandlerName() {
        return lastHandlerName;
    }

    public void setLastHandlerName(String lastHandlerName) {
        this.lastHandlerName = lastHandlerName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreatorAvatarUrl() {
        return creatorAvatarUrl;
    }

    public void setCreatorAvatarUrl(String creatorAvatarUrl) {
        this.creatorAvatarUrl = creatorAvatarUrl;
    }

    public String getCustomerServiceName() {
        return customerServiceName;
    }

    public void setCustomerServiceName(String customerServiceName) {
        this.customerServiceName = customerServiceName;
    }

    public String getDepartmentLeaderName() {
        return departmentLeaderName;
    }

    public void setDepartmentLeaderName(String departmentLeaderName) {
        this.departmentLeaderName = departmentLeaderName;
    }
}
