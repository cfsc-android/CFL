package com.chanfinecloud.cfl.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.chanfinecloud.cfl.util.NetworkUtils;


/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 网络检测广播服务
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    private NetEvent event;

    public NetBroadcastReceiver(NetEvent event) {
        this.event = event;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetworkUtils.getNetWorkState(context);
            // 接口回调传过去状态的类型
            event.onNetChange(netWorkState);
        }
    }

    /**
     * 自定义网络切换接口
     */
    public interface NetEvent {
        void onNetChange(int netMobile);
    }
}
