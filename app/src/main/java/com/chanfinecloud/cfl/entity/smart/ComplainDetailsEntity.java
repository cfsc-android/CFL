package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/19.
 * Version: 1.0
 * Describe: 投诉详情实体类
 */
public class ComplainDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 170cdcfef4117986faf35674080b1fdf
     * roomId : 16fa72dd4e7034b8aa5a2af40d1972ce
     * projectId : 16f274b06fb2f8c87eb107841bfbbc05
     * typeId : 1
     * statusId : 4
     * customerService : a75d45a015c44384a04449ee80dc3503
     * departmentLeader : a75d45a015c44384a04449ee80dc3503
     * lastHandler : a75d45a015c44384a04449ee80dc3503
     * createTime : 2020-03-12 16:15:21
     * problemDesc : wo yao tou su
     * serviceAcceptTime : 2020-03-12 17:03:47
     * serviceRejectTime :
     * handleStatus : 0
     * isFinish : 0
     * finishType :
     * finishTime :
     * unsatisfiedDesc :
     * householdId : 16fa73e5607ee21f4ff5e0842e7b5d4c
     * processInstanceId : 732501
     * problemResourceKey : 3992805752164163931306b5c9fdf5ba
     * solveStatus : 0
     * reportType : 0
     * liveResourceKey : 7ea208e2af6c4376b53d0d0b1e1d5d9e
     * liveTime : 2020-03-12 17:05:51
     * liveDesc : I'm ok
     * solutionContent :
     * estimateDate :
     * handleDesc :
     * handleTime :
     * handleResourceKey :
     * comment :
     * emergencyLevel : 3
     * commentLevel :
     * reformMeasures :
     * reformDate :
     * createBy : a75d45a015c44384a04449ee80dc3503
     * projectName : 长房明宸府
     * complaintTypeName : 物业投诉
     * complaintStatusName : 到场处理
     * briefDesc : 1栋 一单元 3202
     * householdName : 马冬梅
     * customerServiceName : 超级管理员
     * departmentLeaderName : 超级管理员
     * lastHandlerName : 超级管理员
     * taskInfoVo :
     * householdMobile :
     * creatorAvatarUrl : http://img3.imgtn.bdimg.com/it/u=378824344,1185609431&fm=26&gp=0.jpg
     */

    private String id;
    private String roomId;
    private String projectId;
    private int typeId;
    private int statusId;
    private String customerService;
    private String departmentLeader;
    private String lastHandler;
    private String createTime;
    private String problemDesc;
    private String serviceAcceptTime;
    private String serviceRejectTime;
    private int handleStatus;
    private int isFinish;
    private String finishType;
    private String finishTime;
    private String unsatisfiedDesc;
    private String householdId;
    private String processInstanceId;
    private String problemResourceKey;
    private int solveStatus;
    private int reportType;
    private String liveResourceKey;
    private String liveTime;
    private String liveDesc;
    private String solutionContent;
    private String estimateDate;
    private String handleDesc;
    private String handleTime;
    private String handleResourceKey;
    private String comment;
    private String emergencyLevel;
    private String commentLevel;
    private String reformMeasures;
    private String reformDate;
    private String createBy;
    private String projectName;
    private String complaintTypeName;
    private String complaintStatusName;
    private String briefDesc;
    private String householdName;
    private String customerServiceName;
    private String departmentLeaderName;
    private String lastHandlerName;
    private String taskInfoVo;
    private String householdMobile;
    private String creatorAvatarUrl;
    private List<ResourceEntity> problemResourceValue;
    private List<WorkflowProcessesEntity> processes;

    public List<ResourceEntity> getProblemResourceValue() {
        return problemResourceValue;
    }

    public void setProblemResourceValue(List<ResourceEntity> problemResourceValue) {
        this.problemResourceValue = problemResourceValue;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public void setProblemDesc(String problemDesc) {
        this.problemDesc = problemDesc;
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

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public int getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(int isFinish) {
        this.isFinish = isFinish;
    }

    public String getFinishType() {
        return finishType;
    }

    public void setFinishType(String finishType) {
        this.finishType = finishType;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getUnsatisfiedDesc() {
        return unsatisfiedDesc;
    }

    public void setUnsatisfiedDesc(String unsatisfiedDesc) {
        this.unsatisfiedDesc = unsatisfiedDesc;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProblemResourceKey() {
        return problemResourceKey;
    }

    public void setProblemResourceKey(String problemResourceKey) {
        this.problemResourceKey = problemResourceKey;
    }

    public int getSolveStatus() {
        return solveStatus;
    }

    public void setSolveStatus(int solveStatus) {
        this.solveStatus = solveStatus;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public String getLiveResourceKey() {
        return liveResourceKey;
    }

    public void setLiveResourceKey(String liveResourceKey) {
        this.liveResourceKey = liveResourceKey;
    }

    public String getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getLiveDesc() {
        return liveDesc;
    }

    public void setLiveDesc(String liveDesc) {
        this.liveDesc = liveDesc;
    }

    public String getSolutionContent() {
        return solutionContent;
    }

    public void setSolutionContent(String solutionContent) {
        this.solutionContent = solutionContent;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
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

    public String getHandleResourceKey() {
        return handleResourceKey;
    }

    public void setHandleResourceKey(String handleResourceKey) {
        this.handleResourceKey = handleResourceKey;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(String commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getReformMeasures() {
        return reformMeasures;
    }

    public void setReformMeasures(String reformMeasures) {
        this.reformMeasures = reformMeasures;
    }

    public String getReformDate() {
        return reformDate;
    }

    public void setReformDate(String reformDate) {
        this.reformDate = reformDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getComplaintTypeName() {
        return complaintTypeName;
    }

    public void setComplaintTypeName(String complaintTypeName) {
        this.complaintTypeName = complaintTypeName;
    }

    public String getComplaintStatusName() {
        return complaintStatusName;
    }

    public void setComplaintStatusName(String complaintStatusName) {
        this.complaintStatusName = complaintStatusName;
    }

    public String getBriefDesc() {
        return briefDesc;
    }

    public void setBriefDesc(String briefDesc) {
        this.briefDesc = briefDesc;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
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

    public String getLastHandlerName() {
        return lastHandlerName;
    }

    public void setLastHandlerName(String lastHandlerName) {
        this.lastHandlerName = lastHandlerName;
    }

    public String getTaskInfoVo() {
        return taskInfoVo;
    }

    public void setTaskInfoVo(String taskInfoVo) {
        this.taskInfoVo = taskInfoVo;
    }

    public String getHouseholdMobile() {
        return householdMobile;
    }

    public void setHouseholdMobile(String householdMobile) {
        this.householdMobile = householdMobile;
    }

    public String getCreatorAvatarUrl() {
        return creatorAvatarUrl;
    }

    public void setCreatorAvatarUrl(String creatorAvatarUrl) {
        this.creatorAvatarUrl = creatorAvatarUrl;
    }
}
