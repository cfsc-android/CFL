package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * 此类描述的是:房屋信息对象
 * @author TanYong
 * create at 2017/5/8 14:32
 */
public class RoomInfoEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private String address;
    private String  roomId;//房屋标识
    private String  cellId;//单元标识
    private String  code;//房屋编号
    private String  ownerId;//业主标识
    private String  propertyArea;//房屋面积

    private String building;//楼栋
    private String houses;//楼盘
    private String roomNumber;//房号

    /**
     * 判断是否选中:1、选中，2、没选中
     */
    private int checkedFlag;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getPropertyArea() {
        return propertyArea;
    }

    public void setPropertyArea(String propertyArea) {
        this.propertyArea = propertyArea;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getHouses() {
        return houses;
    }

    public void setHouses(String houses) {
        this.houses = houses;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCheckedFlag() {
        return checkedFlag;
    }

    public void setCheckedFlag(int checkedFlag) {
        this.checkedFlag = checkedFlag;
    }
}
