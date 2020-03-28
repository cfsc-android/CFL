package com.chanfinecloud.cfl.entity.eventbus;

/**
 * Created by zengx on 2019/5/13.
 * Describe:
 */
public class NickNameEventBusData {
    private String nickName;

    public NickNameEventBusData(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
