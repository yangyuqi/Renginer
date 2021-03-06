package com.qxmagic.railwayengineerternimal.ui.photo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.global.GlobalConstant;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.utils.BMapUtil;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.Util;
import com.qxmagic.railwayengineerternimal.widget.ScaleImageView;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片压缩
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author gac
 * @version [版本号, 2014-2-25]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressLint("HandlerLeak")
@SuppressWarnings("deprecation")
public class CompressPhotoActivity extends BaseActivity<IBasePresenter> {
    /**
     * 日志记录
     */
    public static final String TAG = "CompressPhotoActivity";

    private Bundle bundle;

    private String originalFilePath;

    private String filePath;

    private ScaleImageView mImageView;

    /**
     * 是否删除
     */
    private boolean isDelete = true;

    /**
     * 标题是否 在显示
     */
    private boolean isShowing = true;

    /**
     * 更新UI
     */
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (null == BitmapFactory.decodeFile(filePath)) {
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Message message = new Message();
                                message.what = 0;
                                handler.sendMessage(message);
                            }
                        }, 1000);
                        return;
                    }
                    mImageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        bindListener();
        getBundle();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_compress_photo;
    }

    @Override
    public void initViews() {
        CommonTitleUtil.initCommonTitle(this, "图片预览", "完成", false, false, new OnClickListener() {
            @Override
            public void onClick(View view) {
                isDelete = false;
                //来自爆料发布界面
                //将数据返回给上一activity
                Intent intent = new Intent();
                intent.putExtra("picPath", filePath);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mImageView = (ScaleImageView) findViewById(R.id.compress_image);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    public void bindListener() {
        mImageView.setDetector(new GestureDetector(new MySimpleGesture()));
    }

    public void getBundle() {
        bundle = getIntent().getExtras();
        if (bundle.getString("path") == null) {
            Uri uri = Uri.parse(bundle.getString("uri"));
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            if (null != actualimagecursor) {
                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor.getString(actual_image_column_index);
                filePath = new File(img_path).getAbsolutePath();
                originalFilePath=filePath;
                if (actualimagecursor != null) {
                    actualimagecursor.close();
                    actualimagecursor = null;
                }
            } else {
                String path = bundle.getString("uri");
                if (path.startsWith("file://")) {
                    path = path.replace("file://", "");
                }
                filePath = path;
                originalFilePath=filePath;
            }
        } else {
            filePath = bundle.getString("path");
            originalFilePath=filePath;
        }

        new PhotoTask(filePath).start();
    }

    @Override
    protected void onDestroy() {
        if (isDelete) {
            if (null != filePath && new File(filePath).exists()) {
                new File(filePath).delete();
            }
        }
        if (null != originalFilePath && new File(originalFilePath).exists()) {
            new File(originalFilePath).delete();
        }
        super.onDestroy();
    }

    /**
     * 继承thread 压缩图片处理
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @author gac
     * @version [版本号, 2013-5-16]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    private class PhotoTask extends Thread {

        private String path;

        public PhotoTask(String filePath) {
            super();
            this.path = filePath;
        }

        @Override
        public void run() {
            super.run();
            BufferedOutputStream bos = null;
            Bitmap icon = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);//此时返回bm为空
                Logger.d(TAG+"PhotoTask"+path);
                Logger.d(TAG+"PhotoTask"+options.outWidth + "*"
                        + options.outHeight);

                float percent = options.outHeight > options.outWidth ? options.outHeight / 1920f
                        : options.outWidth / 1080f;
                //                if (percent > 3)
                //                {
                //                    options.inSampleSize = 4;
                //                }
                //                else if (percent > 2 && percent < 3)
                //                {
                //                    options.inSampleSize = 2;
                //                }
                //                else if (percent < 1)
                //                {
                //                    percent = 1;
                //                }
                if (percent < 1) {
                    percent = 1;
                } else {
                    options.inSampleSize = 2;
                }
                int width = (int) (options.outWidth / percent);
                int height = (int) (options.outHeight / percent);
                icon = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                //初始化画布 绘制的图像到icon上  
                Canvas canvas = new Canvas(icon);
                //建立画笔  
                Paint photoPaint = new Paint();
                //获取跟清晰的图像采样  
                photoPaint.setDither(true);
                //过滤一些  
                photoPaint.setFilterBitmap(true);
                options.inJustDecodeBounds = false;
                Bitmap prePhoto = BMapUtil.bitmapFromFile(path, width, height);
                if (percent >= 1) {
                    prePhoto = Bitmap.createScaledBitmap(prePhoto,
                            width,
                            height,
                            true);
                    canvas.drawBitmap(prePhoto, 0, 0, photoPaint);

                    //                    if (bundle.getString("path") == null)
                    //                    {
                    String picName = System.currentTimeMillis() + ".jpg";
                    File file = Util.makeDirs(GlobalConstant.FILE_CACHE_DIR);
                    File picFile = new File(file, picName);
                    filePath = picFile.getAbsolutePath();
                    bos = new BufferedOutputStream(
                            new FileOutputStream(picFile));
                    //                    }
                    //                    else
                    //                    {
                    //                        bos = new BufferedOutputStream(new FileOutputStream(
                    //                                path));
                    //                    }
                    int quaility = (int) (100 / percent > 80 ? 80
                            : 100 / percent);
                    prePhoto.compress(CompressFormat.JPEG, quaility, bos);
                    bos.flush();
                    if (prePhoto != null && !prePhoto.isRecycled()) {
                        prePhoto.recycle();
                        prePhoto = null;
                        System.gc();
                    }
                }
            } catch (IOException e) {
                Logger.e(e.toString());
            } catch (Exception e) {
                Logger.e(e.toString());
            } finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (icon != null && !icon.isRecycled()) {
                    icon.recycle();
                    icon = null;
                    System.gc();
                }
            }
            Message message = new Message();
            message.what = 0;
            handler.sendMessage(message);
        }
    }

    private void hideOrShowView() {
        if (isShowing) {
            //            mContentView.setVisibility(View.GONE);
            isShowing = false;
        } else {
            //            mContentView.setVisibility(View.VISIBLE);
            isShowing = true;
        }
    }

    private class MySimpleGesture extends SimpleOnGestureListener {

        public boolean onDoubleTap(MotionEvent e) {
            mImageView.maxZoomTo((int) e.getX(), (int) e.getY());
            mImageView.cutting();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            hideOrShowView();
            return true;
        }
    }

}
