package com.chanfinecloud.cfl.util;

import android.content.pm.PackageManager;
import android.os.Build;

import com.chanfinecloud.cfl.CFLApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

import static com.chanfinecloud.cfl.config.Config.PERMISSION;


/**
 * Created by Loong on 2020/3/9.
 * Version: 1.0
 * Describe: 应用权限工具类
 */
public class PermissionUtil {
    public static final int REQUEST_CODE = 0x100;//权限检测返回码

    /**
     * 检查应用是否有权限
     * @return
     */
    public static String[] checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {//6.0才用动态权限
            return null;
        }

        List<String> unPermission = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < PERMISSION.length; i++) {
            if (ContextCompat.checkSelfPermission(CFLApplication.getAppContext(), PERMISSION[i]) != PackageManager.PERMISSION_GRANTED) {
                unPermission.add(PERMISSION[i]);//添加还未授予的权限
            }
        }
        //申请权限
        if (unPermission.size() > 0) {//有权限没有通过，需要申请
            String[] strings = new String[unPermission.size()];
           return unPermission.toArray(strings);
        }
        return null;
    }

}
