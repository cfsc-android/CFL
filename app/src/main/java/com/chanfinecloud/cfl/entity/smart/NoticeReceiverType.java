package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 公告接收方
 */
public enum NoticeReceiverType {
    业主("YZ"),
    家属("JS"),
    租户("ZH"),
    商户("SH"),
    员工("YG"),
    游客("YK");
    private final String type;

    NoticeReceiverType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}