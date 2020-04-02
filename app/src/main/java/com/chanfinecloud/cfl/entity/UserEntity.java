package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/5.
 * Version: 1.0
 * Describe:
 */
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * id : 1
     * createBy :
     * updateBy :
     * createTime : 2020-03-04 10:03:12
     * updateTime :
     * username : 123
     * password : $2a$10$Veft7gw9/6DO/zVD./7Wn.PBf9UjslozXNrlDA4nAjwBTOyJX01SG
     * salt :
     * realName :
     * avatarUrl :
     * avatarStr :
     * phone :
     * email :
     * birthday :
     * sex : 0
     * status : 0
     * workNo :
     * post :
     * type :
     * description :
     * lastLoginTime :
     * lastLoginIp :
     * loginCount : 0
     * roles :
     * roleId :
     * oldPassword :
     * newPassword :
     * department :
     */

    private String id;
    private String createBy;
    private String updateBy;
    private String createTime;
    private String updateTime;
    private String username;
    private String password;
    private String salt;
    private String realName;
    private String avatarUrl;
    private String avatarStr;
    private String phone;
    private String email;
    private String birthday;
    private int gender;
    private int status;
    private String workNo;
    private String post;
    private String type;
    private String description;
    private String lastLoginTime;
    private String lastLoginIp;
    private int loginCount;
    private String roles;
    private String roleId;
    private String oldPassword;
    private String newPassword;
    private String department;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarStr() {
        return avatarStr;
    }

    public void setAvatarStr(String avatarStr) {
        this.avatarStr = avatarStr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
