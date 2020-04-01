package com.chanfinecloud.cfl.weidgt.imagepreview.photoview.gestures;

import android.view.MotionEvent;

/**
 * Created by Loong on 2020/2/18.
 * Version: 1.0
 * Describe: 手势探测器
 */
public interface IGestureDetector {
    boolean onTouchEvent(MotionEvent ev);

    boolean isScaling();

    boolean isDragging();

    void setOnGestureListener(OnGestureListener listener);
}
