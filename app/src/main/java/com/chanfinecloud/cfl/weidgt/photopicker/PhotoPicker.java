package com.chanfinecloud.cfl.weidgt.photopicker;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import com.chanfinecloud.cfl.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * Created by Loong on 2019/12/31.
 * Version: 1.0
 * Describe: 照片选取
 */
public class PhotoPicker {
    public static final String authority="com.chanfinecloud.cfl.fileprovider";

    public static void pick(Activity context, int request_code){
        Matisse.from(context)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Customer)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.dp_120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(request_code);
    }
    public static void pick(Activity context, int maxSelectable,int request_code){
        Matisse.from(context)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Customer)
                .countable(true)
                .maxSelectable(maxSelectable)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.dp_120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(request_code);
    }
    public static void pick(Activity context,boolean capture, int request_code){
        Matisse.from(context)
                .choose(MimeType.ofAll())
                .theme(R.style.Matisse_Customer)
                .capture(capture)
                .captureStrategy(new CaptureStrategy(true, authority))
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.dp_120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(request_code);
    }
    public static void pick(Activity context,int maxSelectable, boolean capture,int request_code){
        Matisse.from(context)
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Customer)
                .capture(capture)
                .captureStrategy(new CaptureStrategy(true, authority))
                .countable(true)
                .maxSelectable(maxSelectable)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.dp_120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(request_code);
    }
}
