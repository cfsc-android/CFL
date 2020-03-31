package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

public class HikUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * personId : d62b2106afd549e596628e68163defb2
     * personName : 测试账户2
     * gender : 2
     * orgPath : 默认组织/测试组
     * orgIndexCode : afb723fb-c7cc-40dd-a513-24b28f2f13f7
     * orgName : 测试组
     * certificateType : 111
     * certificateNo : 500231198910150089
     * fingerPrint : []
     * updateTime : 2019-04-12T10:26:49.323+08:00
     * birthday : null
     * phoneNo : 18073627912
     * address : null
     * personPhoto : null
     * email : null
     * education : null
     * nation : null
     * jobNo : 002
     */

    private String personId;
    private String personName;
    private int gender;
    private String orgPath;
    private String orgIndexCode;
    private String orgName;
    private int certificateType;
    private String certificateNo;
    private String updateTime;
    private String phoneNo;
    private String jobNo;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(String orgPath) {
        this.orgPath = orgPath;
    }

    public String getOrgIndexCode() {
        return orgIndexCode;
    }

    public void setOrgIndexCode(String orgIndexCode) {
        this.orgIndexCode = orgIndexCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(int certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    @Override
    public String toString() {
        return "HikUser{" +
                "personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                ", gender=" + gender +
                ", orgPath='" + orgPath + '\'' +
                ", orgIndexCode='" + orgIndexCode + '\'' +
                ", orgName='" + orgName + '\'' +
                ", certificateType=" + certificateType +
                ", certificateNo='" + certificateNo + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", jobNo='" + jobNo + '\'' +
                '}';
    }
}
