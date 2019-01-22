/*
 * 文 件 名:  StringUtil.java
 * 描    述:  字符串工具类
 * 创 建 人:  李晨光
 * 创建时间:  2013-5-2
 */
package com.qxmagic.railwayengineerternimal.utils;

import java.text.DecimalFormat;

/**
 * 字符串工具类  此类封装了字符串相关操作方法
 * 
 * @author  李晨光
 * @version  [版本号, 2013-5-2]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class StringUtil
{
    /**
     * 判断字符串是否为空
     * @param str
     * @return true：空 ，false：非空
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String str)
    {
        return !((null != str) && (!"".equals(str)) && (!"null".equals(str)));
    }

    public static String getHostName(String urlString) {
        String head = "";
        int index = urlString.indexOf("://");
        if (index != -1) {
            head = urlString.substring(0, index + 3);
            urlString = urlString.substring(index + 3);
        }
        index = urlString.indexOf("/");
        if (index != -1) {
            urlString = urlString.substring(0, index + 1);
        }
        return head + urlString;
    }

    public static String getDataSize(long var0) {
        DecimalFormat var2 = new DecimalFormat("###.00");
        return var0 < 1024L ? var0 + "bytes" : (var0 < 1048576L ? var2.format((double) ((float) var0 / 1024.0F))
                + "KB" : (var0 < 1073741824L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F))
                + "MB" : (var0 < 0L ? var2.format((double) ((float) var0 / 1024.0F / 1024.0F / 1024.0F))
                + "GB" : "error")));
    }
}
