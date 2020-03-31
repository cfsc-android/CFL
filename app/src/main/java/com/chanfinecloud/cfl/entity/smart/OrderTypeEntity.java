package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工单类型
 */
public class OrderTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 1
     * parentId : 0
     * name : 入室维修
     * remark :
     * standardtime : 12
     * parentName :
     */

    private int id;
    private int parentId;
    private String name;
    private String remark;
    private int standardtime;
    private String parentName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStandardtime() {
        return standardtime;
    }

    public void setStandardtime(int standardtime) {
        this.standardtime = standardtime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
