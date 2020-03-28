package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/7/30.
 * Describe:
 */
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String projectName;//项目名称
    private String projectAddress;//项目地址
    private String projectHost;//项目服务端Host
    private String projectLogo;//项目Logo
    private String jPushTag;//项目极光推送TAG

    public ProjectInfo(String projectName, String projectAddress, String projectHost, String jPushTag) {
        this.projectName = projectName;
        this.projectAddress = projectAddress;
        this.projectHost = projectHost;
        this.jPushTag = jPushTag;
    }

    public ProjectInfo(String projectName, String projectAddress, String projectHost, String projectLogo, String jPushTag) {
        this.projectName = projectName;
        this.projectAddress = projectAddress;
        this.projectHost = projectHost;
        this.projectLogo = projectLogo;
        this.jPushTag = jPushTag;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(String projectLogo) {
        this.projectLogo = projectLogo;
    }

    public String getjPushTag() {
        return jPushTag;
    }

    public void setjPushTag(String jPushTag) {
        this.jPushTag = jPushTag;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectHost() {
        return projectHost;
    }

    public void setProjectHost(String projectHost) {
        this.projectHost = projectHost;
    }
}
