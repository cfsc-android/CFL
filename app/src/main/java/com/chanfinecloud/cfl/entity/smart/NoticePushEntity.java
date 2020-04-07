package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/31.
 * Version: 1.0
 * Describe: 新闻推送的实体
 */
public class NoticePushEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * businessId : 16f3591718389385c2a6f4b4a85a05e6
     * type : 1
     */

    private String businessId;
    private String type;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
