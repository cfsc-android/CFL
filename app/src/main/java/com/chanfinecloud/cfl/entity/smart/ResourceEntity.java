package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/20.
 * Version: 1.0
 * Describe: 流程图片资源实体
 */
public class ResourceEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * createTime : 2020-02-18T05:58:59.000+0000
     * name : 图片1.jpg
     * id : 17056e0d23cbe099b060cda41f0bd8f7
     * url : http://10.222.1.38:80/group1/M00/00/01/Ct4BJl5LfSOAYE1-AABsyUXZDXU531.jpg
     */

    private String createTime;
    private String name;
    private String id;
    private String url;
    private String contentType;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
