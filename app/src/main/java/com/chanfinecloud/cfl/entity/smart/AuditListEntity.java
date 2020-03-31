package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/21.
 * Version: 1.0
 * Describe:
 */
public class AuditListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private List<AuditEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AuditEntity> getData() {
        return data;
    }

    public void setData(List<AuditEntity> data) {
        this.data = data;
    }
}
