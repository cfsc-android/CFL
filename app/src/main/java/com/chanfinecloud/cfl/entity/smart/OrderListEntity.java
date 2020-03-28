package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工单列表实体
 */
public class OrderListEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;
    private List<OrderEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderEntity> getData() {
        return data;
    }

    public void setData(List<OrderEntity> data) {
        this.data = data;
    }
}
