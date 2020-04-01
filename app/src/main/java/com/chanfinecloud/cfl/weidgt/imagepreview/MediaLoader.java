package com.chanfinecloud.cfl.weidgt.imagepreview;

import com.chanfinecloud.cfl.weidgt.imagepreview.loader.GlideMediaLoader;
import com.chanfinecloud.cfl.weidgt.imagepreview.loader.IMediaLoader;
import com.chanfinecloud.cfl.weidgt.imagepreview.loader.ImageLoader;

/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe:
 */
public class MediaLoader {
    private static volatile MediaLoader sInstance = null;

    private volatile IMediaLoader mLoader;

    private volatile ImageLoader mImageLoader;

    private MediaLoader() {

    }

    /**
     * 获取单例
     *
     * @return
     */
    public static MediaLoader getInstance() {
        if (sInstance == null) {
            synchronized (MediaLoader.class) {
                if (sInstance == null) {
                    sInstance = new MediaLoader();
                }
            }
        }
        return sInstance;
    }

    public static IMediaLoader get() {
        return getInstance().getLoader();
    }


    /**
     * 初始化加载图片类
     *
     * @param loader
     */
    public void initLoader(IMediaLoader loader) {
        mLoader = loader;
    }

    public IMediaLoader getLoader() {
        if (mLoader == null) {
            mLoader = new GlideMediaLoader();
        }
        return mLoader;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new GlideImageLoader();
        }
        return mImageLoader;
    }
}
