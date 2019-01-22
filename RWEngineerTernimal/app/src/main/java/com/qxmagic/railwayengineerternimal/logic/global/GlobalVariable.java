package com.qxmagic.railwayengineerternimal.logic.global;

/**
 * Created by Christian on 2017/3/16 0016.
 * 变量类 用于存放变量
 */

public class GlobalVariable {

    /**
     * 系统版本号
     */
    public static int versionCode = 1;

    /**
     * 系统版本号
     */
    public static String versionName = "1.0.0";

    /**
     * * 屏幕的宽
     */
    public static int screenWidth = 0;

    /**
     * 屏幕的高
     */
    public static int screenHeight = 0;

    /**
     * 当前分辨率的缩放比
     */
    public static float density;

    /**
     * 当前设备密度
     */
    public static float densityDPI;

    /**
     * 屏幕大小
     */
    public static String screenSize = GlobalConstant.DEFAULT_SCREEN_WIDTH + "*"
            + GlobalConstant.DEFAULT_SCREEN_HEIGHT;

    /**
     * 保存当前的Activity
     */
//    public  Activity currentActivity;

}
