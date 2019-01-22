package com.qxmagic.railwayengineerternimal.logic.global;

import android.os.Environment;

/**
 * Created by Christian on 2017/3/16 0016.
 * 常量类 用于存放常量
 */

public class GlobalConstant {
    /**
     * sd卡根路径
     */
    public static String SDCARD_PATH;

    /**
     * 初始化sd卡根路径
     * @return
     */
    static {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SDCARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        } else {
            SDCARD_PATH = "/mnt/sdcard/";
        }
    }

    /**
     * mtn应用的包名
     */
    public static final String MTN_APP_PACKAGE_NAME = "com.qxmagic.railwayengineerternimal";

    /**
     * sd卡目录
     */
    public static final String SDCARD_BASE_PATH = SDCARD_PATH
            + MTN_APP_PACKAGE_NAME + "/";

    /**
     * 日志保存路径（SD卡）
     */
    public static final String LOG_PATH_SD = SDCARD_BASE_PATH + "Log/";

    /**
     * 图片缓存的目录
     */
    public static final String IMAGE_CACHE_DIR = GlobalConstant.SDCARD_BASE_PATH
            + "Image/";

    /**
     * 文件缓存的目录
     */
    public static final String FILE_CACHE_DIR = GlobalConstant.SDCARD_BASE_PATH
            + "Cache/";

    /**
     * 默认屏幕的宽
     */
    public static final int DEFAULT_SCREEN_WIDTH = 0;

    /**
     * 默认屏幕的高
     */
    public static final int DEFAULT_SCREEN_HEIGHT = 0;

}
