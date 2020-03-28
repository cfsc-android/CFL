package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/7/8.
 * Describe:
 */
public class ThirdInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 7
     * accountId : on-4W5lb4AkWIPKdKYma9QM4LcGw
     * nickname : 曾晓龙
     * sex : 0
     * faceImg : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epoiaQwerOlibW8eKZ0TmcXbLDXjArx1MWegUGWIn4KXBktYLJ8lSy1weyn2XD3vXk7ibFIIRSkaxFPg/132
     * ownerId : 4561
     * type : 0
     * status : 0
     * createdate :
     * updatedate :
     */

    private int id;
    private String accountId;
    private String nickname;
    private String sex;
    private String faceImg;
    private int ownerId;
    private String type;
    private String status;
    private String createdate;
    private String updatedate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(String faceImg) {
        this.faceImg = faceImg;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }
}
