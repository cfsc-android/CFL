package com.chanfinecloud.cfl.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Loong on 2020/3/25.
 * Version: 1.0
 * Describe:
 */
public class LynActivityManager {

    private volatile static LynActivityManager instance = null;
    private static Stack<Activity> activityStack;
    private LynActivityManager () {}

    public static LynActivityManager getInstance() {
        if (instance == null) {
            synchronized (LynActivityManager.class) {
                if (instance == null) {
                    instance = new LynActivityManager();
                }
            }
        }
        return instance;

    }

    /**
     * 销毁指定的Activity
     * @param activity Activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            // 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 销毁指定的Activity
     * @param cls Class
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                popActivity(activityStack.get(i));
            }
        }
    }

    /**
     * 获得当前栈顶Activity
     * @return Activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (null != activityStack) {
            if (!activityStack.empty()) {
                activity = activityStack.lastElement();
            }
        }
        return activity;
    }

    /**
     * 将当前Activity推入栈中
     * @param activity Activity
     */
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 退出栈中所有Activity,到指定的activity截止
     * @param cls Class
     */
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    /**
     * 退出栈中所有的activity
     */
    public void removeAllActivity() {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            popActivity(activity);
        }
    }

    /**
     * 退出除首页界面栈中所有的activity
     * @param homepageActivity Activity
     */
    public void removeAllThisActivity(Activity homepageActivity) {
        if(homepageActivity == null)return;
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            if (homepageActivity != activity) {
                popActivity(activity);
            }
        }
    }

    /**
     * 获取指定的activity
     * @param cls Class
     * @return Activity
     */
    public Activity getActivityByClass(Class cls) {
        if (activityStack == null) {
            return null;
        }
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity activity = activityStack.get(i);
            String name1 = activity.getClass().getName();
            String name2 = cls.getName();
            if (name1.equals(name2)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 判断当前的activity是当前的运行
     * @param cls Class
     * @return boolean
     */
    public boolean isCurrentActivity(Class cls) {
        String name1 = currentActivity().getClass().getName();
        String name2 = cls.getName();
        return name1.endsWith(name2);
    }
}
