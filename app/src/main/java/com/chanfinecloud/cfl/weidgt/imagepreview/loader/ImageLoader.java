package com.chanfinecloud.cfl.weidgt.imagepreview.loader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by zengx on 2019/6/11.
 * Describe:
 */
public interface ImageLoader {

    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    void displayImage(Context context, Object path, ImageView imageView);

    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    void displayImage(Context context, Object path, ImageView imageView, int width, int height, Drawable placeholder, DiskCacheStrategy strategy);


    /**
     * 展示图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    void displayImage(Context context, Object path, ImageView imageView, Drawable placeholder, DiskCacheStrategy strategy);
}
