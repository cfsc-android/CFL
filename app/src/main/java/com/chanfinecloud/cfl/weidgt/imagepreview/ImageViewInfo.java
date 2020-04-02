package com.chanfinecloud.cfl.weidgt.imagepreview;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe: 图片实体
 */
public class ImageViewInfo implements IPreviewInfo {
    private String mUrl;  //图片地址
    private Rect mBounds; // 记录坐标
    private String mVideoUrl;

    private String mDescription = "描述信息";

    public ImageViewInfo(String url) {
        mUrl = url;
    }

    public ImageViewInfo(String videoUrl, String url) {
        mUrl = url;
        mVideoUrl = videoUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public String getUrl() {//将你的图片地址字段返回
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public Rect getBounds() {//将你的图片显示坐标字段返回
        return mBounds;
    }

    @Nullable
    @Override
    public String getVideoUrl() {
        return mVideoUrl;
    }

    public void setBounds(Rect bounds) {
        mBounds = bounds;
    }

    public void setVideoUrl(String videoUrl) {
        mVideoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUrl);
        dest.writeParcelable(mBounds, flags);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
    }

    protected ImageViewInfo(Parcel in) {
        mUrl = in.readString();
        mBounds = in.readParcelable(Rect.class.getClassLoader());
        mDescription = in.readString();
        mVideoUrl = in.readString();
    }

    public static final Parcelable.Creator<ImageViewInfo> CREATOR = new Parcelable.Creator<ImageViewInfo>() {
        @Override
        public ImageViewInfo createFromParcel(Parcel source) {
            return new ImageViewInfo(source);
        }

        @Override
        public ImageViewInfo[] newArray(int size) {
            return new ImageViewInfo[size];
        }
    };
}
