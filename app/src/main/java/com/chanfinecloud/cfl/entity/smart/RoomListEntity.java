package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/19.
 * Version: 1.0
 * Describe:
 */
public class RoomListEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;
    private List<RoomEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RoomEntity> getData() {
        return data;
    }

    public void setData(List<RoomEntity> data) {
        this.data = data;
    }
}
