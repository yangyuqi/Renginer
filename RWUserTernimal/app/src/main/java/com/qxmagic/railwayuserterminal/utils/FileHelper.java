package com.qxmagic.railwayuserterminal.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.logic.global.GlobalConstant;

import java.io.File;
import java.text.DecimalFormat;

/**
 * 文件帮助类
 */
public class FileHelper {
    /**
     * 该变量是从sd卡读取文件时默认的字符缓冲区的大小
     */
    private static final int MAX_LENTH = 1024;

    /**
     * 文件的存放路径
     */
    private static String filePath = null;

    /**
     * 大小单位为k时，除数
     */
    private static final float SIZE_F_K = 1024.0f;

    /**
     * 大小单位为m时，除数
     */
    private static final float SIZE_F_M = 1048576.0f;

    /**
     * 大小单位为g时，除数
     */
    private static final float SIZE_F_G = 1073741824.0f;

    /**
     * 大小单位为k时，区分界限
     */
    private static final long SIZE_L_K = 1024l;

    /**
     * 大小单位为m时，区分界限
     */
    private static final long SIZE_L_M = 1048576l;

    /**
     * 大小单位为g时，区分界限
     */
    private static final long SIZE_L_G = 1073741824l;

    /**
     * 在程序第一次安装的时候就在sd卡的主目录下新建一个系统所需的目录
     *
     * @return 创建目录是否成功 changed by shaoqing.huang 2011-03-10创建一个存储WGT应用的目录
     */
    public static boolean makeDir() {
        boolean isComplete = false;
        // 创建日志目录
        File file = new File(GlobalConstant.LOG_PATH_SD);
        if (!isFileExist(file)) {
            isComplete = file.mkdirs();
            Logger.i("创建异常日志目录是否成功:" + isComplete);
        }
        return isComplete;
    }


    /**
     * 是否存在此文件
     *
     * @param file 判断是否存在的文件
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExist(final File file) {
        // 在无SD卡时file会为空
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    /**
     * 是否存在此文件
     *
     * @param filePath 判断是否存在的文件路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isFileExist(final String filePath) {
        try {
            File file = new File(filePath);
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除路径指向的文件
     *
     * @param fileName 文件的名称
     * @return true删除成功，false删除失败
     */
    private static boolean deleteFile(final String fileName) {
        boolean isComplete = false;

        File file = new File(fileName);

        if (file.exists()) {
            isComplete = file.delete();
        } else {
            isComplete = true;
        }
        return isComplete;
    }

    /**
     * 本地文件大小
     *
     * @param fileName 文件的名称
     * @return 返回文件的大小
     */
    public static long getLocalFileSize(final String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return 0;
        }
        File file = null;
        file = new File(fileName);
        long length = 0;
        if (isFileExist(file)) {
            length = file.length();
        }
        return length;
    }

    /**
     * 获取SD卡的剩余空间
     *
     * @return SD卡的剩余的字节数
     */
    public static long getFreeSD() {
        long nAvailableCount = 0l;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        long lSize = stat.getBlockSize();
        long lBlock = stat.getAvailableBlocks();
        nAvailableCount = lSize * lBlock;
        return nAvailableCount;
    }


    /**
     *  获得缓存大小
     */
    public static String getCacheSize(Context context) {
        long cacheSize = getDirSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getDirSize(context.getExternalCacheDir());
        }
        File file = new File(GlobalConstant.FILE_CACHE_DIR);
        cacheSize+=getDirSize(file);
        return getFileSizeByLong(cacheSize);
    }

    /**
     * 清楚缓存
     */
    public static void clearCache(Context context){
        deleteDir(new File(
                GlobalConstant.FILE_CACHE_DIR));
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除文件夹下的所有文件
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @param delFile [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static void deleteDir(File delFile) {
        File[] f = delFile.listFiles(); //取得文件夹里面的路径
        if (f == null) {
            delFile.delete();
        } else {
            if (f != null) {
                for (File nFile : f) {
                    if (nFile.isDirectory()) {
                        deleteDir(nFile);
                    } else {
                        nFile.delete();
                    }
                }
            }
            delFile.delete();
        }
        delFile.delete();
    }

    /**
     * 我获取指定目录的文件大小
     * <功能详细描述>
     *
     * @param dirPath 目录路径
     * @return String [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getAllFileInDirSize(String dirPath) {
        File file = new File(dirPath);
        return getFileSizeByLong(getDirSize(file));
    }

    /**
     * 获取指定目录下的文件大小
     * <功能详细描述>
     *
     * @param file [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static long getDirSize(File file) {
        long dirSize = 0;
        if (null == file) {
            return 0;
        }
        File[] list = file.listFiles();
        if (null == list) {
            return 0;
        }
        int length = list.length;
        if (length == 0) {
            return 0;
        }
        File f = null;
        for (int i = 0; i < length; i++) {
            f = list[i];
            if (null == f) {
                continue;
            }
            if (f.isDirectory()) {
                dirSize = dirSize + getDirSize(f);
            } else {
                dirSize = dirSize + f.length();
            }
        }
        return dirSize;
    }

    /**
     * 获取文件的大小 kb mb gb...
     *
     * @param fileSize fileSize
     * @return 大小
     */
    public static String getFileSizeByLong(long fileSize) {
        String appSizeStr = "";
        DecimalFormat df = null;
        if (df == null) {
            df = new DecimalFormat("#0.00");
        }
        if (fileSize <= 0) {
            appSizeStr = "0 KB";
        } else if (fileSize < SIZE_L_K) {
            appSizeStr = df.format(fileSize / SIZE_F_K) + " " + "KB";
        } else if (fileSize < SIZE_L_M) {
            appSizeStr = df.format(fileSize / SIZE_F_K) + " " + "KB";
        } else if (fileSize < SIZE_L_G) {
            appSizeStr = df.format(fileSize / SIZE_F_M) + " " + "MB";
        } else {
            appSizeStr = df.format(fileSize / SIZE_F_G) + " " + "GB";
        }
        return appSizeStr;
    }
}
