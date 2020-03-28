package com.chanfinecloud.cfl.entity.eventbus;

/**
 * Created by zengx on 2019/5/7.
 * Describe:
 */
public class FaceCollectionEventBusData {
    private String faceDisTime;
    private String faceDisUrl;

    public FaceCollectionEventBusData(String faceDisTime, String faceDisUrl) {
        this.faceDisTime = faceDisTime;
        this.faceDisUrl = faceDisUrl;
    }

    public String getFaceDisTime() {
        return faceDisTime;
    }

    public void setFaceDisTime(String faceDisTime) {
        this.faceDisTime = faceDisTime;
    }

    public String getFaceDisUrl() {
        return faceDisUrl;
    }

    public void setFaceDisUrl(String faceDisUrl) {
        this.faceDisUrl = faceDisUrl;
    }
}
