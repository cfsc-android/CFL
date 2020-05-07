package com.chanfinecloud.cfl.entity.enumtype;

/**
 * damien 20200224
 * 极光推动枚举类型
 */
public enum JpushType {

    News("1001"),//新闻通知
    Orders("1002"),//工单
    Complain("1003"),//投诉
    OwnerVerifying("1004"),//业主待审核
    OwnerVerifyPass("1005"),//业主审核通过
    OwnerVerifyRefuse("1006"),//业主审核不通过
    CarVerifying("1007"),//车辆待审核
    CarVerifyPass("1008"),//车辆审核通过
    CarVerifyRefuse("1009");//车辆审核不通过
    private final String type;

    public String getType() {
        return type;
    }

    JpushType(String type) {
        this.type = type;
    }
}

