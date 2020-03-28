package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import org.xutils.common.util.LogUtil;

import java.io.File;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 文件路径工具类
 */
public class FilePathUtil {

    /**
     * 通过Uri获取文件路径
     * @param context Context
     * @param pUri Uri
     * @return String
     */
    public static String getPathByUri(Context context,Uri pUri)
    {
        String _Path = pUri.getPath();

        if (_Path.endsWith(".jpg"))
        {
            LogUtil.d("path-->" + subPath(_Path));
            return subPath(_Path);
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(pUri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    /**
     * 获取子路径
     * @param pPath 完整路径
     * @return String
     */
    private static String subPath(String pPath)
    {
        String[] array = pPath.split("/");
        return pPath.substring(array[1].length() + 1);
    }

    /**
     * 路径不存在则创建
     * @param pPath 完整路径
     * @return String
     */
    public static String createPathIfNotExist(String pPath)
    {
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {
            LogUtil.d("SD卡不存在");
            return null;
        }
        String _AbsolutePath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + pPath;
        LogUtil.d("dbDir->" + _AbsolutePath);
        File dirFile = new File(_AbsolutePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs())
            {
                LogUtil.d("文件夹创建失败");
                return null;
            }
        }
        return _AbsolutePath;
    }

}
