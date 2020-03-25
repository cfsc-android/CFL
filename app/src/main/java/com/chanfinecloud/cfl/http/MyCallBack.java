package com.chanfinecloud.cfl.http;

import android.util.Log;

import org.xutils.common.Callback;


/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: XUtils 请求回调
 */
public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    @Override
    public void onSuccess(ResultType result) {
        Log.e("XUtil","onSuccess");
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Log.e("XUtil","onError:"+ex.getMessage());
    }

    @Override
    public void onCancelled(CancelledException cex) {
        Log.e("XUtil","onCancelled");
    }

    @Override
    public void onFinished() {
        Log.e("XUtil","onFinish");
    }


}
