package com.chanfinecloud.cfl.entity.smart;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2020/3/19.
 * Version: 1.0
 * Describe:
 */
public class ProjectTreeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * level : 1
     * children :
     * title : 长房明宸府
     * key : 16f274b06fb2f8c87eb107841bfbbc05
     */

    private int level;
    private List<ProjectTreeEntity> children;
    private String title;
    private String key;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<ProjectTreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectTreeEntity> children) {
        this.children = children;
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
