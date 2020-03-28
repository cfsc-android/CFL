package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by zengx on 2019/5/9.
 * Describe:
 */
public class ImageBanner implements Serializable {
    private static final long serialVersionUID = 1L;

    private String detailUrl;
    private String imageUrl;

    public ImageBanner(String detailUrl, String imageUrl) {
        this.detailUrl = detailUrl;
        this.imageUrl = imageUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
