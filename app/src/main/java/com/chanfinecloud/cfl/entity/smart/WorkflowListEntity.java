package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 流程列表实体
 */
public class WorkflowListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private List<WorkflowEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<WorkflowEntity> getData() {
        return data;
    }

    public void setData(List<WorkflowEntity> data) {
        this.data = data;
    }
}
