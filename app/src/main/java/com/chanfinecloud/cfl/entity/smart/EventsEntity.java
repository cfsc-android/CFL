package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by damien on 2020/5/09.
 * Version: 1.0
 * Describe:社区活动
 */
public class EventsEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     *"id": "171f1e683ac5a587eb3ea35484ba5fd0",
     *         "createBy": null,
     *         "updateBy": "",
     *         "createTime": "2020-05-08T01:29:08.000+0000",
     *         "updateTime": "2020-05-08T01:34:27.000+0000",
     *         "title": "胜利在你背后",
     *         "location": "新加坡大桥下",
     *         "enrollmentNumber": 0,
     *         "maxNumber": null,
     *         "singleMaxNumber": null,
     *         "activityCosts": 1500,
     *         "contactPerson": "你爷爷我",
     *         "contactNumber": "13545687415",
     *         "registrationDeadline": "2020-05-14",
     *         "startTime": "2020-05-19",
     *         "endTime": "2020-05-28",
     *         "content": "更是噶地方hdfhdf家看服务服务范围",
     *         "projectId": "",
     *         "coverImageId": "16fa84fe885aa4eea81c5ec4a9abba9b",
     *         "isClosed": 0,
     *         "coverImageResource": {
     *           "createTime": "2020-01-15T08:26:47.000+0000",
     *           "name": "周香林.jpg",
     *           "id": "16fa84fe885aa4eea81c5ec4a9abba9b",
     *           "url": "http://10.222.1.38:80/group1/M00/00/00/Ct4BJl4ezMaAeki0AAJO9YbnOaA767.jpg"
     *         },
     *         "status": 0
     */

    private String id;
    private double activityCosts;
    private String contactNumber;
    private String contactPerson;
    private String content;
    private String endTime;
    private int enrollmentNumber;
    private int maxNumber;
    private int singleMaxNumber;
    private String location;
    private String projectId;
    private String registrationDeadline;
    private String startTime;
    private int status;
    private String title;
    private String coverImageId;
    private int isClosed;
    private ResourceEntity coverImageResource;
    private boolean isParticipate;

    public boolean isParticipate() {
        return isParticipate;
    }

    public void setParticipate(boolean participate) {
        isParticipate = participate;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public int getSingleMaxNumber() {
        return singleMaxNumber;
    }

    public void setSingleMaxNumber(int singleMaxNumber) {
        this.singleMaxNumber = singleMaxNumber;
    }

    public String getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(String coverImageId) {
        this.coverImageId = coverImageId;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(int isClosed) {
        this.isClosed = isClosed;
    }

    public ResourceEntity getCoverImageResource() {
        return coverImageResource;
    }

    public void setCoverImageResource(ResourceEntity coverImageResource) {
        this.coverImageResource = coverImageResource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getActivityCosts() {
        return activityCosts;
    }

    public void setActivityCosts(double activityCosts) {
        this.activityCosts = activityCosts;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(int enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "EventsEntity{" +
                "id='" + id + '\'' +
                ", activityCosts=" + activityCosts +
                ", contactNumber='" + contactNumber + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", content='" + content + '\'' +
                ", endTime='" + endTime + '\'' +
                ", enrollmentNumber=" + enrollmentNumber +
                ", maxNumber=" + maxNumber +
                ", singleMaxNumber=" + singleMaxNumber +
                ", location='" + location + '\'' +
                ", projectId='" + projectId + '\'' +
                ", registrationDeadline='" + registrationDeadline + '\'' +
                ", startTime='" + startTime + '\'' +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", coverImageId='" + coverImageId + '\'' +
                ", isClosed=" + isClosed +
                ", coverImageResource=" + coverImageResource +
                ", isParticipate=" + isParticipate +
                '}';
    }
}
