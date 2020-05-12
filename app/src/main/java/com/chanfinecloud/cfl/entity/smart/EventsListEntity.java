package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Damien on 2020/5/29.
 * Version: 1.0
 * Describe:
 */
public class EventsListEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;
    private List<EventsEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<EventsEntity> getData() {
        return data;
    }

    public void setData(List<EventsEntity> data) {
        this.data = data;
    }
}
