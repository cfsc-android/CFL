package com.chanfinecloud.cfl.entity.smart;

import com.chanfinecloud.cfl.entity.NoticeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/23.
 * Version: 1.0
 * Describe:
 */
public class NoticeListEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int count;
    private List<NoticeEntity> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<NoticeEntity> getData() {
        return data;
    }

    public void setData(List<NoticeEntity> data) {
        this.data = data;
    }
}
