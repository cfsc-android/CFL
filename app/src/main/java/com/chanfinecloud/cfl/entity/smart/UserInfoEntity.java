package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/2/27.
 * Version: 1.0
 * Describe:
 */
public class UserInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 1710656f0812ddd736f80ef40bcb53df
     * name : 三年的风格和
     * nickName : 211
     * avatarId :
     * avatarResource :
     * type :
     * mobile : 18073667575
     * backupPhone :
     * faceId :
     * defaultCardNo : 900900070
     * idcardType :
     * idcardTypeDisplay :
     * idcardNo :
     * gender : 1
     * genderDisplay :
     * birthday :
     * email :
     * checkinDate :
     * country :
     * nation :
     * wechatNo :
     * qqNo :
     * hobby :
     * registered :
     * education :
     * educationDisplay :
     * marriage :
     * marriageDisplay :
     * occupation :
     * industry :
     * income :
     * workCompany :
     * companyAddr :
     * unitFax :
     * ancestor :
     * householdType :
     * householdTypeDisplay :
     */

    private String id;
    private String name;
    private String nickName;
    private String avatarId;
    private String avatarResource;
    private String type;
    private String mobile;
    private String backupPhone;
    private String faceId;
    private int defaultCardNo;
    private String idcardType;
    private String idcardTypeDisplay;
    private String idcardNo;
    private String gender;
    private String genderDisplay;
    private String birthday;
    private String email;
    private String checkinDate;
    private String country;
    private String nation;
    private String wechatNo;
    private String qqNo;
    private String hobby;
    private String registered;
    private String education;
    private String educationDisplay;
    private String marriage;
    private String marriageDisplay;
    private String occupation;
    private String industry;
    private String income;
    private String workCompany;
    private String companyAddr;
    private String unitFax;
    private String ancestor;
    private String householdType;
    private String householdTypeDisplay;


    private CurrentDistrictEntity currentDistrict;
    private List<HouseholdRoomEntity> roomList;
    private List<EntranceCardBoEntity> entranceCardBoList;
    private List<EquipmentInfoBo> equipmentInfoBoList;


    public CurrentDistrictEntity getCurrentDistrict() {
        return currentDistrict;
    }

    public void setCurrentDistrict(CurrentDistrictEntity currentDistrict) {
        this.currentDistrict = currentDistrict;
    }

    public List<HouseholdRoomEntity> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<HouseholdRoomEntity> roomList) {
        this.roomList = roomList;
    }

    public List<EntranceCardBoEntity> getEntranceCardBoList() {
        return entranceCardBoList;
    }

    public void setEntranceCardBoList(List<EntranceCardBoEntity> entranceCardBoList) {
        this.entranceCardBoList = entranceCardBoList;
    }

    public List<EquipmentInfoBo> getEquipmentInfoBoList() {
        return equipmentInfoBoList;
    }

    public void setEquipmentInfoBoList(List<EquipmentInfoBo> equipmentInfoBoList) {
        this.equipmentInfoBoList = equipmentInfoBoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarResource() {
        return avatarResource;
    }

    public void setAvatarResource(String avatarResource) {
        this.avatarResource = avatarResource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBackupPhone() {
        return backupPhone;
    }

    public void setBackupPhone(String backupPhone) {
        this.backupPhone = backupPhone;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public int getDefaultCardNo() {
        return defaultCardNo;
    }

    public void setDefaultCardNo(int defaultCardNo) {
        this.defaultCardNo = defaultCardNo;
    }

    public String getIdcardType() {
        return idcardType;
    }

    public void setIdcardType(String idcardType) {
        this.idcardType = idcardType;
    }

    public String getIdcardTypeDisplay() {
        return idcardTypeDisplay;
    }

    public void setIdcardTypeDisplay(String idcardTypeDisplay) {
        this.idcardTypeDisplay = idcardTypeDisplay;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderDisplay() {
        return genderDisplay;
    }

    public void setGenderDisplay(String genderDisplay) {
        this.genderDisplay = genderDisplay;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducationDisplay() {
        return educationDisplay;
    }

    public void setEducationDisplay(String educationDisplay) {
        this.educationDisplay = educationDisplay;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMarriageDisplay() {
        return marriageDisplay;
    }

    public void setMarriageDisplay(String marriageDisplay) {
        this.marriageDisplay = marriageDisplay;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getWorkCompany() {
        return workCompany;
    }

    public void setWorkCompany(String workCompany) {
        this.workCompany = workCompany;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getUnitFax() {
        return unitFax;
    }

    public void setUnitFax(String unitFax) {
        this.unitFax = unitFax;
    }

    public String getAncestor() {
        return ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    public String getHouseholdType() {
        return householdType;
    }

    public void setHouseholdType(String householdType) {
        this.householdType = householdType;
    }

    public String getHouseholdTypeDisplay() {
        return householdTypeDisplay;
    }

    public void setHouseholdTypeDisplay(String householdTypeDisplay) {
        this.householdTypeDisplay = householdTypeDisplay;
    }
}
