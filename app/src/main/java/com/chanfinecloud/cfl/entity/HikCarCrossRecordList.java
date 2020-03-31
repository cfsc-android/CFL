package com.chanfinecloud.cfl.entity;

import java.io.Serializable;
import java.util.List;

public class HikCarCrossRecordList implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * total : 1
     * pageNo : 1
     * pageSize : 15
     * list : []
     */

    private int total;
    private int pageNo;
    private int pageSize;
    private List<HikCarCrossRecord> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<HikCarCrossRecord> getList() {
        return list;
    }

    public void setList(List<HikCarCrossRecord> list) {
        this.list = list;
    }
}
