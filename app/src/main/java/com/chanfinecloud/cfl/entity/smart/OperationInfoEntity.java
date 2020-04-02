package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/6.
 * Version: 1.0
 * Describe:
 */
public class OperationInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * choose: reject
     * name: flow21
     * id : D2B4B7804D1FFF12348F76639A4B7BFE
     * desc : 客服接受价格
     */

    private int choose;
    private String name;
    private String id;
    private String desc;

    public int getChoose() {
        return choose;
    }

    public void setChoose(int choose) {
        this.choose = choose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
