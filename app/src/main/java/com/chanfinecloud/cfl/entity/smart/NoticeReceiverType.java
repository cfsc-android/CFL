package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 公告接收方
 */
public enum NoticeReceiverType {
    业主(1),
    员工(2),
    全部(3);
    private final int type;

    NoticeReceiverType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}