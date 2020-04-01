package com.chanfinecloud.cfl.entity.smart;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 流程节点枚举
 */
public enum WorkflowOrderActionType {
    客服中心确认工单(0x01,"{\"formId\": 1001,\"formName\": \"客服中心确认工单\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"接受,不接受\",\"sort\":1}]}"),
    接单工单补充(0x02,"{\"formId\":1002,\"formName\":\"接单工单补充\",\"formContent\":[{\"formItemType\":\"assignId\",\"formItemLabel\":\"主管\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    分发工单(0x03,"{\"formId\":1003,\"formName\":\"分发工单\",\"formContent\":[{\"formItemType\":\"assignId\",\"formItemLabel\":\"职员\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\": \"choose\",\"formItemLabel\": \"指派,退回\",\"sort\":3}]}"),
    退回客服(0x04,"{\"formId\": 1004,\"formName\": \"退回客服\",\"formContent\": [{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":1},{\"formItemType\": \"choose\",\"formItemLabel\": \"重新指派,关闭\",\"sort\":2}]}"),
    员工检视与确认(0x05,"{\"formId\":1005,\"formName\":\"员工检视与确认\",\"formContent\":[{\"formItemType\":\"resourceKey\",\"formItemLabel\":\"照片\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"choose\",\"formItemLabel\":\"接受,退回\",\"sort\":3}]}"),
    职能主管处理(0x06,"{\"formId\": 1006,\"formName\": \"职能主管处理\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"重新指派,退回\",\"sort\":1}]}"),
    接单核算(0x07,"{\"formId\":1007,\"formName\":\"接单核算\",\"formContent\":[{\"formItemType\":\"materialCost\",\"formItemLabel\":\"材料费(元)\",\"sort\":1},{\"formItemType\":\"manualCost\",\"formItemLabel\":\"人工费(元)\",\"sort\":2},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":3},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":4}]}"),
    客户确认价格(0x08,"{\"formId\": 1008,\"formName\": \"客户确认价格\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"接受,不接受\",\"sort\":1}]}"),
    工作(0x09,"{\"formId\":1009,\"formName\":\"工作\",\"formContent\":[{\"formItemType\":\"resourceKey\",\"formItemLabel\":\"照片\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    客户验收(0x0a,"{\"formId\": 1010,\"formName\": \"客户验收\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"接受,不接受\",\"sort\":1}]}"),
    付费(0x0b,"{\"formId\": 1011,\"formName\": \"付费\",\"formContent\": [{\"formItemType\":\"fee\",\"formItemLabel\":\"付费(元)\",\"sort\":1},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":2}]}"),
    评价(0x0c,"{\"formId\":1012,\"formName\":\"评价\",\"formContent\":[{\"formItemType\":\"rate\",\"formItemLabel\":\"评分\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    回访(0x0d,"{\"formId\": 1013,\"formName\": \"回访\",\"formContent\": [{\"formItemType\":\"submit\",\"formItemLabel\":\"回访\",\"sort\":1}]}"),
    客服沟通确认(0x0f,"{\"formId\":1014,\"formName\":\"电话沟通业主\",\"formContent\":[{\"formItemType\":\"assignId\",\"formItemLabel\":\"主管\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\": \"choose\",\"formItemLabel\": \"指派,关闭\",\"sort\":3}]}"),
    到场查看(0x10,"{\"formId\":1015,\"formName\":\"到场查看\",\"formContent\":[{\"formItemType\":\"resourceKey\",\"formItemLabel\":\"照片\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    客户确认方案(0x11,"{\"formId\": 1016,\"formName\": \"业主确认方案\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"接受,不接受\",\"sort\":1}]}"),
    沟通与重新指派(0x12,"{\"formId\":1017,\"formName\":\"沟通与重新指派\",\"formContent\":[{\"formItemType\":\"assignId\",\"formItemLabel\":\"主管\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    分发员工实施(0x13,"{\"formId\":1018,\"formName\":\"分发员工实施\",\"formContent\":[{\"formItemType\":\"assignId\",\"formItemLabel\":\"职员\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    实施投诉解决方案(0x14,"{\"formId\":1019,\"formName\":\"实施投诉解决方案\",\"formContent\":[{\"formItemType\":\"resourceKey\",\"formItemLabel\":\"照片\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    方案是否完成(0x15,"{\"formId\": 1020,\"formName\": \"方案是否完成\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"完成,未完成\",\"sort\":1}]}"),
    客户确认(0x16,"{\"formId\": 1021,\"formName\": \"客户确认\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"满意,不满意\",\"sort\":1}]}"),
    投诉评价(0x17,"{\"formId\":1022,\"formName\":\"投诉评价\",\"formContent\":[{\"formItemType\":\"rate\",\"formItemLabel\":\"评分\",\"sort\":1},{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":2},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":3}]}"),
    继续沟通(0x18,"{\"formId\": 1023,\"formName\": \"继续沟通\",\"formContent\": [{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":1},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":2}]}"),
    填写整改措施(0x19,"{\"formId\": 1024,\"formName\": \"填写整改措施\",\"formContent\": [{\"formItemType\":\"remark\",\"formItemLabel\":\"备注\",\"sort\":1},{\"formItemType\":\"submit\",\"formItemLabel\":\"提交\",\"sort\":2}]}"),
    客户是否接受(0x1a,"{\"formId\": 1025,\"formName\": \"客户是否接受\",\"formContent\": [{\"formItemType\": \"choose\",\"formItemLabel\": \"接受,不接受\",\"sort\":1}]}");


    private final int code;
    private final String form;

    WorkflowOrderActionType(int code, String form) {
        this.code=code;
        this.form=form;
    }
    public int getCode() {
        return code;
    }

    public String getForm() {
        return form;
    }
}