package com.qxmagic.railwayuserterminal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * <一句话功能简述>
 * SharedPreferences统一管理类
 *
 * @author administrator
 * @version [版本号, 2013-10-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SharedPreferencesUtil {
    public static final String REPAIR_DESCRIPTION = "repair_description";

    /**
     * 用户信息
     */
    public static class UserInfoPreference {
        /**
         * 用户ID
         */
        public static final String USERID = "user_id";

        /**
         * 用户账号
         */
        public static final String USER_ACCOUNT = "user_account";

        /**
         * 手机号码
         */
        public static final String TEL_PHONE = "telphone";

        /**
         * 密码
         */
        public static final String PASSWORD = "password";

        /**
         * 头像
         */
        public static final String HEADER = "header";

        /**
         * 姓名
         */
        public static final String USERNAME = "userName";

        /**
         * 登录的token值，用于自动登录时使用
         */
        public static final String LOGIN_TOKEN = "login_token";

        /**
         * 是否记住密码
         */
        public static final String REMEMBER_PASSWORD = "is_remember_password";

    }

    /**
     * 获取默认的SharedPreferences
     *
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("rwut", Context.MODE_PRIVATE);
    }

    /**
     * 保存设置<BR>
     * 保存一些常量到SharedPreferences
     *
     * @param context
     * @param map
     */
    public static void saveSharedPreferences(Context context,
                                             Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        for (String key : map.keySet()) {
            editor.putString(key, map.get(key));
        }
        editor.commit();
    }

    /**
     * 保存设置<BR>
     * 保存一些常量到SharedPreferences
     *
     * @param key   键
     * @param value 要保存的值
     */
    public static void saveSharedPreferences(Context context, String key,
                                             String value) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存设置<BR>
     * 保存一些常量到SharedPreferences
     *
     * @param key   键
     * @param value 要保存的值
     */
    public static void saveSharedPreferences(Context context, String key,
                                             long value) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存设置<BR>
     * 保存一些常量到SharedPreferences
     *
     * @param key   键
     * @param value 要保存的值
     */
    public static void saveSharedPreferences(Context context, String key,
                                             int value) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存设置<BR>
     * 保存一些常量到SharedPreferences
     *
     * @param key   键
     * @param value 要保存的值
     */
    public static void saveSharedPreferences(Context context, String key,
                                             boolean value) {
        SharedPreferences sp = getSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获得保存到SharedPreferences的常量<BR>
     *
     * @param context
     * @param key          键
     * @param defaultValue 默认值
     * @return 保存在SharedPreferences中键所对应的值
     */
    public static String getSharedPreferences(Context context, String key,
                                              String defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(key, defaultValue);
    }

    /**
     * 获得保存到SharedPreferences的常量<BR>
     *
     * @param context
     * @param key          键
     * @param defaultValue 默认值
     * @return 保存在SharedPreferences中键所对应的值
     */
    public static int getSharedPreferences(Context context, String key,
                                           int defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 获得保存到SharedPreferences的常量<BR>
     *
     * @param context
     * @param key          键
     * @param defaultValue 默认值
     * @return 保存在SharedPreferences中键所对应的值
     */
    public static long getSharedPreferencesByLong(Context context, String key,
                                                  long defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getLong(key, defaultValue);
    }

    /**
     * 获得保存到SharedPreferences的常量<BR>
     *
     * @param context
     * @param key          键
     * @param defaultValue 默认值
     * @return 保存在SharedPreferences中键所对应的值
     */
    public static boolean getSharedPreferences(Context context, String key,
                                               boolean defaultValue) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(key, defaultValue);
    }
}
