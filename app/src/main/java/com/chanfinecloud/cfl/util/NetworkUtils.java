package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Loong on 2020/2/6.
 * Version: 1.0
 * Describe: 网络检查工具类
 */
public class NetworkUtils {

    private static final int NETWORK_NONE = -1;//没有连接网络

    private static final int NETWORK_MOBILE = 0;//移动网络

    private static final int NETWORK_WIFI = 1;//无线网络

    /**
     * 获取网络状态
     * @param context Context上下文
     * @return int
     */
    public static int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 判断有无网络
     * @return boolean
     */
    public static boolean isNetConnect(int netMobile) {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;
    }
}
