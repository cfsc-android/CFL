package com.chanfinecloud.cfl.http;


import com.chanfinecloud.cfl.util.LogUtils;

import org.xutils.common.Callback;

/**
 * Created by Loong on 2020/2/28.
 * Version: 1.0
 * Describe: 下载回调
 */
public class MyProgressCallBack<ResultType> implements Callback.ProgressCallback<ResultType> {
    @Override
    public void onWaiting() {
        LogUtils.d("等待下载");
    }

    @Override
    public void onStarted() {
        LogUtils.d("开始下载");
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        LogUtils.d("下载中...");
    }

    @Override
    public void onSuccess(ResultType result) {
        LogUtils.d("下载完成");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        LogUtils.d("下载失败");
    }

    @Override
    public void onCancelled(CancelledException cex) {
        LogUtils.d("取消下载");
    }

    @Override
    public void onFinished() {
        LogUtils.d("下载完成");
    }
}
