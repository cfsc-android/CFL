package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 审核状态枚举
 */
public enum ApprovalStatusType {
    Audit(0x01),//审核中
    Pass(0x02),//通过
    Refuse(0x03);//拒绝

    private final int type;
    ApprovalStatusType(int type){
        this.type=type;
    }

    public int getType() {
        return type;
    }
}
