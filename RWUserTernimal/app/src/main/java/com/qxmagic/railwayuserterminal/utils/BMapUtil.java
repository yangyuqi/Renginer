package com.qxmagic.railwayuserterminal.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.orhanobut.logger.Logger;

public class BMapUtil
{
    private static final String TAG = "===BMapUtil===";
    
    /**
     * 从view 得到图片
     * @param view
     * @return
     */
    public static Bitmap getBitmapFromView(View view)
    {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }
    
    /**
     * 将字节转换为bitmap
     * <功能详细描述>
     * @param data
     * @return [参数说明]
     * 
     * @return Bitmap [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static Bitmap getBitmapByte(byte[] data)
    {
        try
        {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        catch (Exception e)
        {
//            LogX.getInstance().e(\TAG, e.toString());
            Logger.e(e.toString());
        }
        return null;
    }
    
    /**
     * 将bitmap转换为字节
     * <功能详细描述>
     * @param bitmap
     * @return [参数说明]
     * 
     * @return byte[] [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static byte[] setBitmap2Byte(Bitmap bitmap)
    {
        byte[] data = null;
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //压缩为PNG格式,100表示跟原图大小一样
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            data = baos.toByteArray();
            baos.close();
        }
        catch (Exception e)
        {
//            LogX.getInstance().e(TAG, e.toString());
            Logger.e(e.toString());
        }
        return data;
    }
    
    /**
     * 获取一个指定大小的bitmap
     * 
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromFile(String pathName, int reqWidth,
            int reqHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options = calculateInSampleSize(options, reqWidth, reqHeight);
        return BitmapFactory.decodeFile(pathName, options);
    }
    
    /**
     * 图片压缩处理（使用Options的方法）
     * 
     * @使用方法 首先你要将Options的inJustDecodeBounds属性设置为true，BitmapFactory.decode一次图片。
     *       然后将Options连同期望的宽度和高度一起传递到到本方法中。
     *       之后再使用本方法的返回值做参数调用BitmapFactory.decode创建图片。
     * 
     * @explain BitmapFactory创建bitmap会尝试为已经构建的bitmap分配内存
     *          ，这时就会很容易导致OOM出现。为此每一种创建方法都提供了一个可选的Options参数
     *          ，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
     *          ，返回值也不再是一个Bitmap对象， 而是null。虽然Bitmap是null了，但是Options的outWidth、
     *          outHeight和outMimeType属性都会被赋值。
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    private static BitmapFactory.Options calculateInSampleSize(
            final BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        // 设置压缩比例
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }
}
