package com.chanfinecloud.cfl;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.chanfinecloud.cfl.entity.core.Transition;
import com.chanfinecloud.cfl.util.Utils;
import com.pgyersdk.crash.PgyCrashManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import org.xutils.BuildConfig;
import org.xutils.common.Callback;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Loong on 2020/2/3.
 * Version: 1.0
 * Describe: 自定义Application
 */
public class CFLApplication extends Application {

    public static CFLApplication getInstance = null;
    private Context mContext;

    public static Map<Class, Transition> activityTrans=new HashMap<>();

    public static boolean bind;

    private RefWatcher refWatcher;
    private String TAG = "CFLApplication";

    public static RefWatcher getRefWatcher(Context context) {
        CFLApplication application = (CFLApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getInstance = this;
        mContext = getApplicationContext();
        x.Ext.init(this);//注册Utils
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        PgyCrashManager.register();//蒲公英crash收集注册

        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,"");//友盟注册
        UMConfigure.setLogEnabled(true);//友盟日志


        refWatcher = LeakCanary.install(this);//LeakCanary注册

        //极光推送初始化
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.main_background, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
            }

        });

        //友盟第三方登录/分享渠道设置
        PlatformConfig.setWeixin("wx9e26096ae63f011d", "22ba1246fa64314bb6cc97a7c10ac25c");//微信
        PlatformConfig.setQQZone("101569547", "00261965102559b4d8732e9a747c771a");//QQ
    }


    public static CFLApplication getInstance() {
        return getInstance;
    }

    public static Context getAppContext() {
        return CFLApplication.getInstance.mContext;
    }

    /**
     * 超过64K，需要采用分包
     * @param base Context
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this); // 初始化
    }


/*    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = mContext.getString(R.string.app_name);
            // 用户可以看到的通知渠道的描述
            String description = mContext.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            if (mNotificationManager != null)
                mNotificationManager.createNotificationChannel(mChannel);

        }
    }*/


}