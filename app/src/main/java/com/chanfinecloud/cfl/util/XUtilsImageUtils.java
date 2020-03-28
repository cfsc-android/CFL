package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.widget.ImageView;

import com.chanfinecloud.cfl.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by zengx on 2019/4/24.
 * Describe:
 */
public class XUtilsImageUtils {
    /**
     * 显示图片（默认情况）
     * @param imageView
     * @param iconUrl
     */
    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setPlaceholderScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setFailureDrawableId(R.drawable.img_placeholder)
                .setLoadingDrawableId(R.drawable.img_placeholder)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }

    /**
     * 显示图片scaleType
     * @param imageView
     * @param iconUrl
     * @param scaleType
     */
    public static void display(ImageView imageView, String iconUrl, ImageView.ScaleType scaleType) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(scaleType)
                .setPlaceholderScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setFailureDrawableId(R.drawable.img_placeholder)
                .setLoadingDrawableId(R.drawable.img_placeholder)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }

    /**
     * 显示圆角图片
     * @param imageView
     * @param iconUrl
     * @param radius
     */
    public static void display(ImageView imageView, String iconUrl, int radius, Context context) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(SizeUtils.dip2px(context,radius))
                .setIgnoreGif(false)
                .setCrop(true)//是否对图片进行裁剪
                .setPlaceholderScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setFailureDrawableId(R.drawable.img_placeholder)
                .setLoadingDrawableId(R.drawable.img_placeholder)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     * @param imageView
     * @param iconUrl
     * @param isCircluar
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                .setPlaceholderScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setFailureDrawableId(R.drawable.img_placeholder)
                .setLoadingDrawableId(R.drawable.img_placeholder)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    public static ImageOptions getImageOption(){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(120, 120)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.ic_launcher)
                //设置支持gif
                .setIgnoreGif(false)
                //设置显示圆形图片
                .setCircular(false)
                .setSquare(true)
                .build();
        return imageOptions;
    }
}

