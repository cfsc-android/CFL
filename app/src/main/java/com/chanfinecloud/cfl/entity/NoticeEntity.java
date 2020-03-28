package com.chanfinecloud.cfl.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class NoticeEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 170768c29185de14263c1b8461c9ad09
     * createBy : admin
     * updateBy : admin
     * createTime : 2020-02-24 17:34:22
     * updateTime : 2020-02-24 17:42:38
     * projectId : 16f274b06fb2f8c87eb107841bfbbc05
     * title : ccccc
     * summary : cccccc
     * content : <p>cccc</p>
     * detailUrl :
     * coverPhoto :
     * notifyEvent : 工作进程
     * pushStatus : 0
     * auditStatus : 0
     * receiver : 0
     * browseNum : 0
     * praiseNum : 0
     * collectionNum : 0
     * forwardNum : 0
     * publishBy :
     * publishTime :
     * resourceValues :
     * updateName :
     * creator :
     * publisher :
     * projectName : 长房明宸府
     */

    private String id;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private String projectId;
    private String title;
    private String summary;
    private String content;
    private String detailUrl;
    private String coverPhoto;
    private String coverUrl;
    private String notifyEvent;
    private int pushStatus;
    private int auditStatus;
    private int receiver;
    private int browseNum;
    private int praiseNum;
    private int collectionNum;
    private int forwardNum;
    private String publishBy;
    private String publishTime;
    private String resourceValues;
    private String updateName;
    private String creator;
    private String publisher;
    private String projectName;
    private String isThumbup;
    private List<ResourceEntity> attachments;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getNotifyEvent() {
        return notifyEvent;
    }

    public void setNotifyEvent(String notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    public int getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(int pushStatus) {
        this.pushStatus = pushStatus;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public int getBrowseNum() {
        return browseNum;
    }

    public void setBrowseNum(int browseNum) {
        this.browseNum = browseNum;
    }

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public int getCollectionNum() {
        return collectionNum;
    }

    public void setCollectionNum(int collectionNum) {
        this.collectionNum = collectionNum;
    }

    public int getForwardNum() {
        return forwardNum;
    }

    public void setForwardNum(int forwardNum) {
        this.forwardNum = forwardNum;
    }

    public String getPublishBy() {
        return publishBy;
    }

    public void setPublishBy(String publishBy) {
        this.publishBy = publishBy;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getResourceValues() {
        return resourceValues;
    }

    public void setResourceValues(String resourceValues) {
        this.resourceValues = resourceValues;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getIsThumbup() {
        return isThumbup;
    }

    public void setIsThumbup(String isThumbup) {
        this.isThumbup = isThumbup;
    }

    public List<ResourceEntity> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ResourceEntity> attachments) {
        this.attachments = attachments;
    }
}
