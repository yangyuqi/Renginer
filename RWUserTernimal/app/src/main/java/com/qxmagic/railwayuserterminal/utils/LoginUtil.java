package com.qxmagic.railwayuserterminal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.logic.model.UserBean;
import com.qxmagic.railwayuserterminal.ui.login.LoginActivity;


/**
 * Created by Christian on 2017/4/11 0011.
 * 登录工具类
 */

public class LoginUtil {

    /**
     * 保存登录信息
     *
     * @param context  上下文
     * @param userItem 用户对象
     */
    public static void saveLoginInfo(Context context, UserBean userItem) {
        if (null != userItem) {
            // 登录成功后，同时更新全局变量中的用户信息
            RWUTApplication.getInstance().mLoginUser = userItem;
            //登录成功之后 发送广播到个人中心界面 修改UI

            SharedPreferencesUtil.saveSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.USERID,
                    userItem.userId);

//            SharedPreferencesUtil.saveSharedPreferences(context,
//                    SharedPreferencesUtil.UserInfoPreference.USER_ACCOUNT,
//                    userItem.userAccount);

            SharedPreferencesUtil.saveSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.TEL_PHONE,
                    userItem.phone);

            if (!TextUtils.isEmpty(userItem.password)) {
                SharedPreferencesUtil.saveSharedPreferences(context,
                        SharedPreferencesUtil.UserInfoPreference.PASSWORD,
                        userItem.password);
            }

            SharedPreferencesUtil.saveSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.HEADER,
                    userItem.userPortrait);

            SharedPreferencesUtil.saveSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.USERNAME,
                    userItem.username);


            SharedPreferencesUtil.saveSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.LOGIN_TOKEN,
                    userItem.token);
        }
    }

    /**
     * 获得登录用户信息
     *
     * @return 用户对象
     */
    public static UserBean getLoginUser(Context mContext) {
        UserBean userItem = new UserBean();
        userItem.userId = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.USERID, "");
//        userItem.userAccount = SharedPreferencesUtil.getSharedPreferences(mContext,
//                SharedPreferencesUtil.UserInfoPreference.USER_ACCOUNT, "");
        userItem.phone = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.TEL_PHONE, "");
        userItem.password = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.PASSWORD, "");
        userItem.userPortrait = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.HEADER, "");
        userItem.username = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.USERNAME, "");
        userItem.token = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.LOGIN_TOKEN, "");
        return userItem;
    }

    /**
     * 清空登录用户信息
     *
     * @param context 上下文
     */
    public static void clearLoginInfo(Context context) {
        if (null == context) {
            return;
        }
        SharedPreferencesUtil.saveSharedPreferences(context,
                SharedPreferencesUtil.UserInfoPreference.USERID,
                "");

//        SharedPreferencesUtil.saveSharedPreferences(context,
//                SharedPreferencesUtil.UserInfoPreference.USER_ACCOUNT,
//                "");

//        SharedPreferencesUtil.saveSharedPreferences(context,
//                SharedPreferencesUtil.UserInfoPreference.TEL_PHONE,
//                "");
//
//        SharedPreferencesUtil.saveSharedPreferences(context,
//                SharedPreferencesUtil.UserInfoPreference.PASSWORD,
//                "");

        SharedPreferencesUtil.saveSharedPreferences(context,
                SharedPreferencesUtil.UserInfoPreference.HEADER,
                "");

        SharedPreferencesUtil.saveSharedPreferences(context,
                SharedPreferencesUtil.UserInfoPreference.USERNAME,
                "");

        SharedPreferencesUtil.saveSharedPreferences(context,
                SharedPreferencesUtil.UserInfoPreference.LOGIN_TOKEN,
                "");


        // 退出登录成功后，同时更新全局变量中的用户信息
        RWUTApplication.getInstance().mLoginUser = null;
    }


    /**
     * 更改用户当前z头像
     *
     * @param mContext 上下文
     * @param imgUrl   用户当前的积分值
     */
    public static void updateUserPortrait(Context mContext, String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            SharedPreferencesUtil.saveSharedPreferences(mContext,
                    SharedPreferencesUtil.UserInfoPreference.HEADER,
                    imgUrl);
        }
        if (null != RWUTApplication.getInstance().mLoginUser) {
            RWUTApplication.getInstance().mLoginUser.userPortrait = imgUrl;
        }
    }

    /**
     * 更改用户当前昵称
     *
     * @param mContext 上下文
     * @param username 新的手机号
     */
    public static void updateUsername(Context mContext, String username) {
        if (!TextUtils.isEmpty(username)) {
            SharedPreferencesUtil.saveSharedPreferences(mContext,
                    SharedPreferencesUtil.UserInfoPreference.USERNAME,
                    username);
        }
        if (null != RWUTApplication.getInstance().mLoginUser) {
            RWUTApplication.getInstance().mLoginUser.username = username;
        }
    }

    /**
     * 更改用户当前手机号
     *
     * @param mContext 上下文
     * @param phone    新的手机号
     */
    public static void updateUserPhone(Context mContext, String phone) {
        if (!TextUtils.isEmpty(phone)) {
            SharedPreferencesUtil.saveSharedPreferences(mContext,
                    SharedPreferencesUtil.UserInfoPreference.TEL_PHONE,
                    phone);
        }
        if (null != RWUTApplication.getInstance().mLoginUser) {
            RWUTApplication.getInstance().mLoginUser.phone = phone;
        }
    }

    /**
     * 更改用户当前密码
     *
     * @param mContext 上下文
     * @param password 新的密码
     */
    public static void updateUserPassword(Context mContext, String password) {
        if (!TextUtils.isEmpty(password)) {
            SharedPreferencesUtil.saveSharedPreferences(mContext,
                    SharedPreferencesUtil.UserInfoPreference.PASSWORD,
                    password);
        }
        if (null != RWUTApplication.getInstance().mLoginUser) {
            RWUTApplication.getInstance().mLoginUser.password = password;
        }
    }


    /**
     * 判断是否能够自动登录
     * true 能
     * false 不能
     *
     * @param mContext 上下文
     */
    public static boolean isCanAutoLogin(Context mContext) {
        String userId = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.USERID,
                "");
        String token = SharedPreferencesUtil.getSharedPreferences(mContext,
                SharedPreferencesUtil.UserInfoPreference.LOGIN_TOKEN,
                "");
        return !TextUtils.isEmpty(userId) && !TextUtils.isEmpty(token);
    }

    /**
     * 获取登录用户id
     *
     * @param context 上下文
     * @return 用户id
     */
    public static String getUserId(Context context) {
        if (null == context) {
            return null;
        }
        String userId;
        if (null != RWUTApplication.getInstance()
                && null != RWUTApplication.getInstance().mLoginUser
                && !TextUtils.isEmpty(RWUTApplication.getInstance().mLoginUser.userId)) {
            userId = RWUTApplication.getInstance().mLoginUser.userId;
        } else {
            userId = SharedPreferencesUtil.getSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.USERID,
                    null);
        }
        return Util.checkNull(userId);
    }

    /**
     * 获取登录用户名
     *
     * @param context 上下文
     * @return 用户名
     */
    public static String getUserName(Context context) {
        if (null == context) {
            return null;
        }
        String userName;
        if (null != RWUTApplication.getInstance()
                && null != RWUTApplication.getInstance().mLoginUser
                && !TextUtils.isEmpty(RWUTApplication.getInstance().mLoginUser.username)) {
            userName = RWUTApplication.getInstance().mLoginUser.username;
        } else {
            userName = SharedPreferencesUtil.getSharedPreferences(context,
                    SharedPreferencesUtil.UserInfoPreference.USERNAME,
                    null);
        }
        return Util.checkNull(userName);
    }


    /**
     * 启动登录页面
     * <功能详细描述>
     *
     * @param mContext [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void startLogin(Context mContext) {
        if (null == mContext) {
            //防止mContext为null时，启动登录页面异常
            mContext = RWUTApplication.getInstance().getApplicationContext();
        }
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!(mContext instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
    }


}
