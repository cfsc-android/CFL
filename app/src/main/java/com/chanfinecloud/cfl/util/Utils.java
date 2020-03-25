package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.chanfinecloud.cfl.CFLApplication;

/**
 * Created by Loong on 2020/3/25.
 * Version: 1.0
 * Describe: Utils
 */
public class Utils {

    /**
     * 获取当前客户端版本信息versionName
     * @return String
     */
    public static String getCurrentVersion() {
        try {
            PackageInfo info = CFLApplication.getAppContext().getPackageManager().getPackageInfo(
                    CFLApplication.getAppContext().getPackageName(), 0);
            return info.versionName.trim();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return "";
    }
    /**
     * 获取当前客户端版本信息versionCode
     * @return int
     */
    public static int getCurrentBuild() {
        try {
            PackageInfo info = CFLApplication.getAppContext().getPackageManager().getPackageInfo(
                    CFLApplication.getAppContext().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        return 0;
    }

}
