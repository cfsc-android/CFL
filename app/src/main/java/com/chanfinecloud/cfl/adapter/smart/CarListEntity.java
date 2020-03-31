package com.chanfinecloud.cfl.adapter.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/2.
 * Version: 1.0
 * Describe:
 */
public class CarListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private List<CarEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CarEntity> getData() {
        return data;
    }

    public void setData(List<CarEntity> data) {
        this.data = data;
    }
}
