package com.chanfinecloud.cfl.weidgt;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by zengx on 2019/4/25.
 * Describe:
 */
public class CameraPreview extends SurfaceView implements
        SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private static final int ORIENTATION=90;




    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void takePicture(Camera.ShutterCallback mShutterCallback, Camera.PictureCallback rawPictureCallback, Camera.PictureCallback jpegPictureCallback) {
        mCamera.takePicture(mShutterCallback, rawPictureCallback, jpegPictureCallback);
    }

    public void startPreview() {
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            int cameraCount = 0;
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras();
            for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) { // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                    mCamera = Camera.open(camIdx);
                }
            }
        }
        mCamera.setDisplayOrientation(ORIENTATION);
        try {
            mCamera.setPreviewDisplay(holder);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象

//        Camera.Size largestSize = getBestSupportedSize(parameters
//                .getSupportedPreviewSizes());
//        parameters.setPreviewSize(largestSize.width, largestSize.height);// 设置预览图片尺寸

        List<Camera.Size> list = parameters.getSupportedPictureSizes();
        for (Camera.Size size : list) {
            Log.e("size",size.width+"_"+size.height);
            if(size.height<=1280&&size.width<=1280){
                // 设置捕捉图片尺寸
                parameters.setPictureSize(size.width, size.height);
                break;
            }
        }
        Log.e("size--",parameters.getPictureSize().width+"_"+parameters.getPictureSize().height);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }
    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes) {
        // 取能适用的最大的SIZE
        Camera.Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }
}
