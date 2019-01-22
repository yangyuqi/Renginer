package com.qxmagic.railwayengineerternimal.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by poorgod on 2016/11/11.
 * 公共工具类
 */
public class Util {

    private static float sPixelDensity = -1f;

    /**
     * 判断集合是否为空
     *
     * @param list 集合
     * @return
     */
    public static boolean isListEmpty(List<?> list) {
        if (null == list) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * drawable转为bitmap
     *
     * @param drawable 图片的drawable
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * dp和px换算
     *
     * @param context 上下文
     * @param dp      dp转换数字
     * @return 转换后的数字
     */
    public static int dpToPixel(Context context, int dp) {
        return (int) (dpToPixel(context, (float) dp) + .5f);
    }

    /**
     * dp和px换算
     *
     * @param context 上下文
     * @param dp      dp转换数字
     * @return 转换后的数字
     */
    public static synchronized float dpToPixel(Context context, float dp) {
        if (sPixelDensity < 0) {
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity) context).getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(metrics);
            sPixelDensity = metrics.density;
        }
        return sPixelDensity * dp;
    }

    /**
     * 格式化java时间,只保留时分秒
     * @param time java时间
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatJavaTimeHHmmss(String time)
    {
        try
        {
            StringBuffer buffer = new StringBuffer();
            buffer.append(time);
            long timeLong = Long.valueOf(buffer.toString());
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            return sdf.format(new Date(timeLong));
        }
        catch (Exception e)
        {
            return time;
        }
    }

    /**
     * 判断是否存在SD卡 <一句话功能简述> <功能详细描述>
     *
     * @return [参数说明]
     *
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isHasSDcard()
    {
        if (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 目录创建 <一句话功能简述> <功能详细描述>
     *
     * @param path [父目录的路径]
     *
     * @return File [返回类型说明] 返回父目录
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static File makeDirs(String path)
    {
        File file = new File(path);
        if (null != file && !file.exists())
        {
            file.mkdirs();
        }
        return file;
    }
    /**
     * 通过指定包名找到应用程序
     */
    public static List<ResolveInfo> findActivitiesForPackage(Context context,
                                                             String packageName)
    {
        PackageManager packageManager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN");
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        mainIntent.setPackage(packageName);
        List<ResolveInfo> apps = packageManager
                .queryIntentActivities(mainIntent, 0);
        return apps != null ? apps : new ArrayList<ResolveInfo>();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static String checkNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    public static boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        return scrollDifference != 0 && ((scrollY > 0) || (scrollY < scrollDifference - 1));

    }
}
