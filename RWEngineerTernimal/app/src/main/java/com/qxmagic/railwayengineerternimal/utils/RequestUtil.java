package com.qxmagic.railwayengineerternimal.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.qxmagic.railwayengineerternimal.RWETApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Christian on 2017/4/18 0018.
 * 获取公共参数，将请求对象转为json字符串的工具类
 */

public class RequestUtil {
    /**
     * 公共参数
     *
     * @return 公共参数uid
     */
    public static HashMap<String, String> getCommonParams() {
        HashMap<String, String> params = new HashMap<>();
        String uid = "";
        String tokenD = "";
        if (null != RWETApplication.getInstance().mLoginUser) {
            uid = RWETApplication.getInstance().mLoginUser.userId;
            tokenD = RWETApplication.getInstance().mLoginUser.token;
        }
        if (!TextUtils.isEmpty(uid)) {
            params.put("u", uid);
        }
        if (!TextUtils.isEmpty(tokenD)) {
            HashMap<String, String> map = new HashMap<>();
            map.put("uid", uid);
            try {
                tokenD = DESUtil.encryption(mapToJson(map), tokenD);
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.put("d", tokenD);
        }
        return params;
    }

    /**
     * 将请求参数map对象转为json
     *
     * @param map 包含请求参数的请求对象
     * @param <T> 请求参数中value的值
     * @return 转过之后的json
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    /**
     * 获得加密的token key值
     *
     * @param mContext 上下文
     * @return tokenKey
     */
    public static String getTokenKey(Context mContext) {
        String tokenKey = "";
        if (null != RWETApplication.getInstance().mLoginUser) {
            tokenKey = RWETApplication.getInstance().mLoginUser.token;
        } else {
            tokenKey = SharedPreferencesUtil.getSharedPreferences(mContext, SharedPreferencesUtil.UserInfoPreference.LOGIN_TOKEN, "");
        }
        return tokenKey;
    }


}
