package com.chanfinecloud.cfl.weidgt.imagepreview;

import android.graphics.Rect;
import android.os.Parcelable;

import androidx.annotation.Nullable;


/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe: 图片实体预览接口
 */
public interface IPreviewInfo extends Parcelable {
    /**
     * @return 图片地址
     */
    String getUrl();

    /**
     * @return 记录坐标
     */
    Rect getBounds();
    /**
     * @return 获取视频链接
     */
    @Nullable
    String getVideoUrl();

}
