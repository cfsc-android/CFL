package com.chanfinecloud.cfl.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.chanfinecloud.cfl.CFLApplication;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Loong on 2020/2/7.
 * Version: 1.0
 * Describe: SharedPreferences工具类
 */
public class SharedPreferencesUtil {

    private volatile static SharedPreferencesUtil instance=null;
    private SharedPreferencesUtil () {}
    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            synchronized (LynActivityManager.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 存储对象数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param ob 值
     * @return boolean
     */
    public boolean saveObject(Context context, String spName, String key, Object ob) {
        if (ob == null) {
            return false;
        }
        boolean falg = false;
        SharedPreferences preferences = CFLApplication.getAppContext()
                .getSharedPreferences(spName, context.MODE_PRIVATE);
        // 创建字节输出
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            // 创建对象输出流，并封装字节流
            oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(ob);
            // 将字节流编码成base64的字符窜

            String oAuth_Base64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            falg = preferences.edit().putString(key, oAuth_Base64).commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }

                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return falg;
        }
    }

    /**
     * 获取对象数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return Object
     */
    public Object getObject(Context context, String spName, String key) {
        Object ob = null;
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        String productBase64 = preferences.getString(key, "");
        // 读取字节
        byte[] base64 = Base64.decodeBase64(productBase64.getBytes());
        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        ObjectInputStream bis = null;
        try {
            // 再次封装
            bis = new ObjectInputStream(bais);
            // 读取对象
            ob = bis.readObject();
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }

                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ob;
    }

    /**
     * 存储Integer数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param value 值
     */
    public void saveIntValue(Context context, String spName, String key,
                             int value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).commit();

    }

    /**
     * 获取Integer数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return int
     */
    public int getIntValue(Context context, String spName, String key) {
        return getIntValue(context, spName, key, 0);
    }
    /**
     * 获取Integer数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param defaultValue 默认值
     * @return int
     */
    public int getIntValue(Context context, String spName, String key,
                           int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    /**
     * 存储Float数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param value 默认值
     */
    public void saveFloatValue(Context context, String spName, String key,
                               float value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putFloat(key, value).commit();

    }

    /**
     * 获取Float数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return float
     */
    public float getFloatValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    /**
     * 获取Boolean数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param value 值
     */
    public void saveBooleanValue(Context context, String spName, String key,
                                 boolean value) {

        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putBoolean(key, value).commit();

    }

    /**
     * 获取Boolean数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return boolean
     */
    public boolean getBooleanValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * 获取Boolean数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param isDefault 默认值
     * @return String
     */
    public boolean getBooleanValue(Context context, String spName, String key,
                                   boolean isDefault) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getBoolean(key, isDefault);
    }

    /**
     * 存储Long数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param value 值
     */
    public void saveLongValue(Context context, String spName, String key,
                              long value) {

        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);

        preferences.edit().putLong(key, value).commit();
    }

    /**
     * 获取Long数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return long
     */
    public long getLongValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }

    /**
     * 存储String数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @param value 值
     */
    public void saveStringValue(Context context, String spName, String key,
                                String value) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    /**
     * 获取String数据
     * @param context 上下文
     * @param spName 作用域
     * @param key 键
     * @return String
     */
    public String getStringValue(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(spName,
                context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}

