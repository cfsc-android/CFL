package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/29.
 * Version: 1.0
 * Describe:
 */
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id : 170856be6633082dd0b45fa4f10ba2d2
     * name : 微信小程序上线.txt
     * isImg : false
     * contentType : text/plain
     * size : 72
     * domain : http://10.222.1.38:80/
     * url : group1/M00/00/02/Ct4BJl5XZ2aAPlEPAAAASLdCxeY499.txt
     * source : FASTDFS
     * resourceKey : 170856b7889c42e352fff3f4bf680464
     * createTime : 2020-02-27T06:53:26.000+0000
     */

    private String id;
    private String name;
    private boolean isImg;
    private String contentType;
    private int size;
    private String domain;
    private String url;
    private String source;
    private String resourceKey;
    private String createTime;

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

    public boolean isIsImg() {
        return isImg;
    }

    public void setIsImg(boolean isImg) {
        this.isImg = isImg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
