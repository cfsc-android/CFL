package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/17.
 * Version: 1.0
 * Describe: 工单状态
 */
public class OrderStatusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 1
     * name : 未受理
     * remark :
     * orderNO :
     */

    private int id;
    private String name;
    private String remark;
    private String orderNO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNO() {
        return orderNO;
    }

    public void setOrderNO(String orderNO) {
        this.orderNO = orderNO;
    }
}
