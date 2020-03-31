package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工单实体
 */
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 170567dcde094125195823a47b0861d2
     * createTime : 2020-02-18 12:10:50
     * code : 202002180002
     * statusId : 1
     * typeId : 2
     * customerService :
     * departmentLeader : a75d45a015c44384a04449ee80dc3503
     * lastHandler :
     * createType : 0
     * completeTime :
     * acceptTime :
     * rejectedTime :
     * rejectedReason :
     * remark : 测试
     * planTimeString :
     * householdId : 16fa73e5607ee21f4ff5e0842e7b5d4c
     * roomId : 16fa72dd4e7034b8aa5a2af40d1972ce
     * projectId : ec93bb06f5be4c1f19522ca78180e2i9
     * resourcekey :
     * mobile :
     * problemDesc : 测试
     * linkMan :
     * manCost :
     * materialsCost :
     * sendfeeTime :
     * piid : 380018
     * planTime : 2020-02-18 12:10:39
     * handerStatus : 0
     * newResourcekey :
     * liveDesc :
     * liveTime :
     * lasthandlerDesc :
     * lasthandlerTime :
     * isfinish : 0
     * reportType : 1
     * solveType : 0
     * closeType :
     * closeTime :
     * closeDesc :
     * comment :
     * commentLevel :
     * handerResourcekey :
     * erejectedReason :
     * erejectedTime :
     * erejectedResourcekey :
     * address :
     * householdName : 马冬梅
     * householdMobile : 18073695867
     * taskInfoVos :
     * workTypeName : 空调维修
     * workStatusName : 未受理
     * roomNameAll : 1栋 一单元 3202
     * phaseName : 一期
     * phaseId : 16f27973c392f54863e7b90438cac119
     * projectName : 长房明宸府
     * lastHandlerName :
     * createName:
     * creatorAvatarUrl: http://img3.imgtn.bdimg.com/it/u=378824344,1185609431&fm=26&gp=0.jpg
     * resourceValue:[]
     * customerServiceName:
     * departmentLeaderName
     */

    private String id;
    private String createTime;
    private String code;
    private int statusId;
    private int typeId;
    private String customerService;
    private String departmentLeader;
    private String lastHandler;
    private int createType;
    private String completeTime;
    private String acceptTime;
    private String rejectedTime;
    private String rejectedReason;
    private String remark;
    private String planTimeString;
    private String householdId;
    private String roomId;
    private String projectId;
    private String resourcekey;
    private String mobile;
    private String problemDesc;
    private String linkMan;
    private String manCost;
    private String materialsCost;
    private String sendfeeTime;
    private String piid;
    private String planTime;
    private int handerStatus;
    private String newResourcekey;
    private String liveDesc;
    private String liveTime;
    private String lasthandlerDesc;
    private String lasthandlerTime;
    private int isfinish;
    private int reportType;
    private int solveType;
    private String closeType;
    private String closeTime;
    private String closeDesc;
    private String comment;
    private String commentLevel;
    private String handerResourcekey;
    private String erejectedReason;
    private String erejectedTime;
    private String erejectedResourcekey;
    private String address;
    private String householdName;
    private String householdMobile;
    private String taskInfoVos;
    private String workTypeName;
    private String workStatusName;
    private String roomNameAll;
    private String phaseName;
    private String phaseId;
    private String projectName;
    private String lastHandlerName;
    private String createName;
    private String creatorAvatarUrl;
    private List<ResourceEntity> resourceValue;
    private String customerServiceName;
    private String departmentLeaderName;


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

    public int getCreateType() {
        return createType;
    }

    public void setCreateType(int createType) {
        this.createType = createType;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getRejectedTime() {
        return rejectedTime;
    }

    public void setRejectedTime(String rejectedTime) {
        this.rejectedTime = rejectedTime;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPlanTimeString() {
        return planTimeString;
    }

    public void setPlanTimeString(String planTimeString) {
        this.planTimeString = planTimeString;
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

    public String getResourcekey() {
        return resourcekey;
    }

    public void setResourcekey(String resourcekey) {
        this.resourcekey = resourcekey;
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

    public String getManCost() {
        return manCost;
    }

    public void setManCost(String manCost) {
        this.manCost = manCost;
    }

    public String getMaterialsCost() {
        return materialsCost;
    }

    public void setMaterialsCost(String materialsCost) {
        this.materialsCost = materialsCost;
    }

    public String getSendfeeTime() {
        return sendfeeTime;
    }

    public void setSendfeeTime(String sendfeeTime) {
        this.sendfeeTime = sendfeeTime;
    }

    public String getPiid() {
        return piid;
    }

    public void setPiid(String piid) {
        this.piid = piid;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public int getHanderStatus() {
        return handerStatus;
    }

    public void setHanderStatus(int handerStatus) {
        this.handerStatus = handerStatus;
    }

    public String getNewResourcekey() {
        return newResourcekey;
    }

    public void setNewResourcekey(String newResourcekey) {
        this.newResourcekey = newResourcekey;
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

    public String getLasthandlerDesc() {
        return lasthandlerDesc;
    }

    public void setLasthandlerDesc(String lasthandlerDesc) {
        this.lasthandlerDesc = lasthandlerDesc;
    }

    public String getLasthandlerTime() {
        return lasthandlerTime;
    }

    public void setLasthandlerTime(String lasthandlerTime) {
        this.lasthandlerTime = lasthandlerTime;
    }

    public int getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(int isfinish) {
        this.isfinish = isfinish;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public int getSolveType() {
        return solveType;
    }

    public void setSolveType(int solveType) {
        this.solveType = solveType;
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

    public String getHanderResourcekey() {
        return handerResourcekey;
    }

    public void setHanderResourcekey(String handerResourcekey) {
        this.handerResourcekey = handerResourcekey;
    }

    public String getErejectedReason() {
        return erejectedReason;
    }

    public void setErejectedReason(String erejectedReason) {
        this.erejectedReason = erejectedReason;
    }

    public String getErejectedTime() {
        return erejectedTime;
    }

    public void setErejectedTime(String erejectedTime) {
        this.erejectedTime = erejectedTime;
    }

    public String getErejectedResourcekey() {
        return erejectedResourcekey;
    }

    public void setErejectedResourcekey(String erejectedResourcekey) {
        this.erejectedResourcekey = erejectedResourcekey;
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

    public String getTaskInfoVos() {
        return taskInfoVos;
    }

    public void setTaskInfoVos(String taskInfoVos) {
        this.taskInfoVos = taskInfoVos;
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

    public String getRoomNameAll() {
        return roomNameAll;
    }

    public void setRoomNameAll(String roomNameAll) {
        this.roomNameAll = roomNameAll;
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

    public List<ResourceEntity> getResourceValue() {
        return resourceValue;
    }

    public void setResourceValue(List<ResourceEntity> resourceValue) {
        this.resourceValue = resourceValue;
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
