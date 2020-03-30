package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class VisitorListEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;
    private List<VisitorEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<VisitorEntity> getData() {
        return data;
    }

    public void setData(List<VisitorEntity> data) {
        this.data = data;
    }
}
