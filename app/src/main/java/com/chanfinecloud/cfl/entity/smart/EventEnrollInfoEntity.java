package com.chanfinecloud.cfl.entity.smart;

public class EventEnrollInfoEntity {

    /**
     *          " age": 0,
     * 			"eventId": "",
     * 			"gender": "男",
     * 			"householdId": "",
     * 			"id": "",
     * 			"mobile": "17348524785",
     * 			"name": "粘人",
     * 			"remark": ""
     */
    private int age;
    private String eventId;
    private String gender;
    private String householdId;
    private String id;
    private String mobile;
    private String name;
    private String remark;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(String householdId) {
        this.householdId = householdId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public String toString() {
        return "EventEnrollInfoEntity{" +
                "age=" + age +
                ", eventId='" + eventId + '\'' +
                ", gender='" + gender + '\'' +
                ", householdId='" + householdId + '\'' +
                ", id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
