package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/6/14.
 * Describe:
 */
public class HikParkInfo implements Serializable {
    public static final long serialVersionUID=1L;

    /**
     * parkIndexCode : 5a20fb59cf4f4191b3127b54a146141f
     *  parentParkIndexCode : null
     * parkName : xfj2车库
     * createTime : 2018-08-29T10:57:57:768+08:00
     * updateTime : 2018-08-29T11:01:33:420+08:00
     */

    private String parkIndexCode;
    private String parentParkIndexCode;
    private String parkName;
    private String createTime;
    private String updateTime;

    public String getParkIndexCode() {
        return parkIndexCode;
    }

    public void setParkIndexCode(String parkIndexCode) {
        this.parkIndexCode = parkIndexCode;
    }

    public String getParentParkIndexCode() {
        return parentParkIndexCode;
    }

    public void setParentParkIndexCode(String parentParkIndexCode) {
        this.parentParkIndexCode = parentParkIndexCode;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
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

    @Override
    public String toString() {
        return "HikParkInfo{" +
                "parkIndexCode='" + parkIndexCode + '\'' +
                ", parentParkIndexCode='" + parentParkIndexCode + '\'' +
                ", parkName='" + parkName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
