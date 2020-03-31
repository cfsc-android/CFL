package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;

/**
 * Created by Loong on 2020/3/19.
 * Version: 1.0
 * Describe:
 */
public class KeyTitleEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * title : 长房明宸府
     * key : 16f274b06fb2f8c87eb107841bfbbc05
     */

    private String title;
    private String key;

    public KeyTitleEntity(String title, String key) {
        this.title = title;
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
