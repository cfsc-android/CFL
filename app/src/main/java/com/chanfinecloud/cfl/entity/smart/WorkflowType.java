package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工作流类型枚举
 */
public enum WorkflowType {
    Order("1","工单"),
    Complain("2","投诉");

    private final String type;
    private final String typeChs;
    WorkflowType(String type, String typeChs){
        this.type=type;
        this.typeChs=typeChs;
    }
    public String getType() {
        return type;
    }

    public String getTypeChs() {
        return typeChs;
    }
}
