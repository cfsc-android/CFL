package com.chanfinecloud.cfl.weidgt.imagepreview.photoview.gestures;

import android.content.Context;
import android.os.Build;

/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe: 不通版本的手势实现
 */
public class VersionedGestureDetector {
    public static IGestureDetector newInstance(Context context, OnGestureListener listener) {
        final int sdkVersion = Build.VERSION.SDK_INT;
        IGestureDetector detector;

        if (sdkVersion < Build.VERSION_CODES.ECLAIR) {
            detector = new CupcakeGestureDetector(context);
        } else if (sdkVersion < Build.VERSION_CODES.FROYO) {
            detector = new EclairGestureDetector(context);
        } else {
            detector = new FroyoGestureDetector(context);
        }

        detector.setOnGestureListener(listener);

        return detector;
    }
}
