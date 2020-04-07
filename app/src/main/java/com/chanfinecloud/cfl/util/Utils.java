package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.chanfinecloud.cfl.R;
import com.chanfinecloud.cfl.CFLApplication;
import com.chanfinecloud.cfl.ui.base.BaseActivity;

import org.xutils.image.ImageOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private static String content;
    private static long oneTime;
    private static long twoTime;
    private static Toast toast;

    /**
     * 弹出Toast
     *
     * @param str
     */
    public static void showPrompt(String str) {
        if ((getUiContext() == null)) {
            return;
        } else if (Utils.isEmpty(str)) {
            return;
        }
        if (toast == null) {
            content = str;
            toast = Toast.makeText(getUiContext(), str, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            if (Utils.isEmpty(content)) {
                return;
            }
            twoTime = System.currentTimeMillis();
            if (str.equals(content) && twoTime - oneTime > Toast.LENGTH_SHORT) {
                toast.show();
            } else {
                content = str;
                toast.setText(content);
                toast.show();
            }
            oneTime = twoTime;
        }
    }

    /**
     * @param str
     * @return
     * @方法说明:判断字符串是否为 * @方法名称:isEmpty
     * @返回 boolean
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("") || str.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * @return
     * @方法说明:获取各大UI的activity的上下文
     * @方法名称:getUiContext
     * @返回值:Context
     */
    public static Context getUiContext() {
        Context context = null;
        if (LynActivityManager.getInstance().currentActivity() != null) {
            if (LynActivityManager.getInstance().currentActivity() instanceof BaseActivity) {
                BaseActivity activity = (BaseActivity) LynActivityManager.getInstance().currentActivity();
                context = (Context) activity;
            }
        }
        return context;
    }

    /**
     * @param mobileNums
     * @return
     * @方法说明:Test whether is Mobile phone number
     * @方法名称:isMobileNO
     * @返回 boolean
     */
    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189,191
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8])|(19[0-9]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    public static ImageOptions getImageOption(){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(120, 120)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.drawable.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.drawable.ic_launcher)
                //设置支持gif
                .setIgnoreGif(false)
                //设置显示圆形图片
                .setCircular(false)
                .setSquare(true)
                .build();
        return imageOptions;
    }
    public static String getStringValue(String value) {
        if (null == value) {
            value = "";
        }
        return value;
    }

    /**
     * @param con
     * @param resouce_Id
     * @return
     * @方法说明:加载布局文件
     * @方法名称:LoadXmlView
     * @返回 View
     */
    public static View LoadXmlView(Context con, int resouce_Id) {
        LayoutInflater flat = LayoutInflater.from(con);
        View view = flat.inflate(resouce_Id, null);
        return view;
    }

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    /**
     * 计算状态栏高度高度 getStatusBarHeight
     *
     * @return
     */
    public static int getStatusBarHeight() {
        return getInternalDimensionSize(Resources.getSystem(),
                STATUS_BAR_HEIGHT_RES_NAME);
    }
    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将Drawable转化为Bitmap
     *
     * @param drawable Drawable
     * @return Bitmap
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    public static long getDateTimeByStringAndFormat(String dateString, String formatString)
    {

        long ntime = 0;
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date end_date = null;
        try {
            end_date = sdf.parse(dateString);
            ntime = end_date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return ntime;
    }

}
