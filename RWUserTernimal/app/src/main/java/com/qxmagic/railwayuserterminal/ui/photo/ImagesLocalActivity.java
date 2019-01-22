package com.qxmagic.railwayuserterminal.ui.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.album.component.PhotoView;
import com.qxmagic.railwayuserterminal.ui.album.component.PhotoViewAttacher;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ImageLoader;
import com.qxmagic.railwayuserterminal.utils.ListUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 本地图集展示类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  gac
 * @version  [版本号, 2014-9-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ImagesLocalActivity extends BaseActivity
{
    
    /**
     * 标题布局
     */
    private View mTitleLayout;
    
    /**
     * 标题view
     */
    private TextView mTitleView;
    
    /**
     * 底部位置布局 
     */
    private LinearLayout mBottomLayout;
    
    /**
     * 底部位置图标
     */
    private ImageView[] mIndicatorImgs;
    
    /**
     * 标题和底部布局是否 在显示
     */
    private boolean isShowing = true;
    
    /**
     * 标题内容
     */
    private String title = "";
    
    /**
     * 图片对象集合
     */
    private ArrayList<String> imageUrlList = new ArrayList<>();
    
    /**
     * 大小
     */
    private int size;
    
    /**
     * viewPager
     */
    private ViewPager mViewPager;
    
    /**
     * view集合
     */
    private ArrayList<PhotoView> mViewList;
    
    /**
     * 下标
     */
    private int index = 0;
    
    /**
     * 当前选中的position
     */
    private int currentSelectPosition = 0;
    
    /**
     * 是否删除图片
     */
    private boolean isDelete = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getBundle();
        initViews();
        initBottomView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_images;
    }

    /**
     * 获取数据
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void getBundle()
    {
        isDelete = getIntent().getBooleanExtra("isDelete", false);
        imageUrlList= getIntent().getStringArrayListExtra("imageUrlList");
//        ArrayList<String>list = getIntent().getStringArrayListExtra("imageUrlList");
//        if(!ListUtil.isEmpty(list)){
//            for (String url:list){
//                ImageBean bean=new ImageBean();
//                bean.imageUrl=url;
//                imageUrlList.add(bean);
//            }
//        }
        index = getIntent().getIntExtra("index", 0);
        currentSelectPosition = index;
        if (!ListUtil.isEmpty(imageUrlList))
        {
            size = imageUrlList.size();
            title = "第" + (index + 1) + "张(共" + size + "张)";
        }
    }
    
    @Override
    public void initViews()
    {
        if (isDelete)
        {
            CommonTitleUtil.initCommonTitle(this, title, "删除", false, false, new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("position", currentSelectPosition);
                    if (imageUrlList != null
                            && imageUrlList.size() > currentSelectPosition)
                    {
                        intent.putExtra("bean",
                                imageUrlList.get(currentSelectPosition));
                    }
                    setResult(1000, intent);
                    finish();
                }
            });
        }else{
            CommonTitleUtil.initCommonTitle(this, title, R.mipmap.common_back_icon, false, true);
        }
        mViewPager = (ViewPager) findViewById(R.id.images_viewpager);
        mTitleLayout = findViewById(R.id.images_title_layout);
        mTitleView = (TextView) findViewById(R.id.common_title_center_text);
        mTitleView.setText(title);
        mBottomLayout = (LinearLayout) findViewById(R.id.images_bottom_layout);
        
        if (size <= 0)
        {
            return;
        }
        if (imageUrlList != null)
        {
            if (null == mViewList)
            {
                mViewList = new ArrayList<>();
            }
            else
            {
                mViewList.clear();
            }
            for (int i = 0; i < imageUrlList.size(); i++)
            {
                PhotoView imageView = new PhotoView(mContext);
                imageView.setBackgroundResource(R.mipmap.default_square);
                mViewList.add(imageView);
            }
            mViewPager.setAdapter(new MyPageAdapter());
            mViewPager.setOnPageChangeListener(new MyPageChangeListener());
            mViewPager.setCurrentItem(index);
        }
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    public void bindListener()
    {
        
    }
    
    /**
     * 初始化底部位置指示布局
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void initBottomView()
    {
        if (size <= 0)
        {
            return;
        }
        mIndicatorImgs = new ImageView[size];
        
        //每次添加广告位点时，清空所有之前添加
        mBottomLayout.removeAllViews();
        
        for (int i = 0; i < size; i++)
        {
            if (i < mIndicatorImgs.length)
            {
                mIndicatorImgs[i] = new ImageView(mContext);
                mIndicatorImgs[i].setPadding(5, 0, 5, 0);
                mIndicatorImgs[i].setClickable(true);
                mIndicatorImgs[i].setEnabled(false);
                mIndicatorImgs[i].setImageResource(R.drawable.page_indicator_selector);
                mBottomLayout.addView(mIndicatorImgs[i]);
            }
        }
        //设置为白色，即选中状态
        mIndicatorImgs[index].setEnabled(true);
        
    }
    

    class MyPageAdapter extends PagerAdapter
    {
        
        @Override
        public int getCount()
        {
            return mViewList.size();
        }
        
        @Override
        public boolean isViewFromObject(View arg0, Object arg1)
        {
            return arg0 == arg1;
        }
        
        @Override
        public int getItemPosition(Object object)
        {
            return super.getItemPosition(object);
        }
        
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2)
        {
            ((ViewPager) arg0).removeView(mViewList.get(arg1));
        }
        
        @Override
        public Object instantiateItem(View arg0, int arg1)
        {
            ((ViewPager) arg0).addView(mViewList.get(arg1));
            if (arg1 == 0 && imageUrlList.size() > 0)
            {
                setBitmap(arg1);
            }
            mViewList.get(arg1).setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener()
            {
                
                @Override
                public void onPhotoTap(View view, float x, float y)
                {
                    hideOrShowView();
                }
            });
            return mViewList.get(arg1);
        }
        
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1)
        {
            
        }
        
        @Override
        public Parcelable saveState()
        {
            return null;
        }
        
        @Override
        public void startUpdate(View arg0)
        {
        }
        
        @Override
        public void finishUpdate(View arg0)
        {
        }
    }
    
    // 指引页面更改事件监听器
    class MyPageChangeListener implements OnPageChangeListener
    {
        
        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }
        
        @Override
        public void onPageSelected(int arg0)
        {
            currentSelectPosition = arg0;
            setCurIndicator(arg0);
            mTitleView.setText("第" + (arg0 + 1) + "张(共" + size + "张)");
            setBitmap(arg0);
        }
    }
    
    private void setBitmap(int position)
    {
        String path = imageUrlList.get(position);
        ImageLoader.loadFit(this,path,mViewList.get(position),R.mipmap.default_icon);
//        Bitmap bitmap = BitmapFactory.decodeFile(imageUrlList.get(position).imageUrl);
//        if (bitmap == null)
//        {
////                        bitmap = BitmapFactory.decodeFile(imageUrlList.get(position));
//            //如果图片不存在
//            if (path == null || !new File(path).exists())
//            {
//                return;
//            }
//            //这个地方对图片进行压缩
//            bitmap = revitionImageSize(path);
////            if (null != bitmap)
////            {
////                mManager.addBitmapToMemoryCache(path, bitmap);
////            }
//        }else{
//            mViewList.get(position).setImageBitmap(bitmap);
//        }
    }
    
    public Bitmap revitionImageSize(String path)
    {
        BufferedInputStream in = null;
        try
        {
            
            in = new BufferedInputStream(new FileInputStream(new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            //            options.inPreferredConfig = Bitmap.Config.RGB_565;
            
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            Bitmap bitmap = null;
            
            //先判断原图的内存
            while (true)
            {
                if ((options.outWidth >> i <= 960)
                        && (options.outHeight >> i <= 960))
                {
                    in = new BufferedInputStream(new FileInputStream(new File(
                            path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
            
            return bitmap;
            
        }
        catch (Exception e)
        {
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    private void hideOrShowView()
    {
        if (isShowing)
        {
            mTitleLayout.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
            isShowing = false;
        }
        else
        {
            mTitleLayout.setVisibility(View.VISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
            isShowing = true;
        }
    }
    
    /**
    * 设置当前的小点的位置
    */
    private void setCurIndicator(int position)
    {
        if (mIndicatorImgs == null)
        {
            return;
        }
        for (int i = 0; i < mIndicatorImgs.length; i++)
        {
            if (i == position)
            {
                mIndicatorImgs[i].setEnabled(true);
            }
            else
            {
                mIndicatorImgs[i].setEnabled(false);
            }
        }
    }
}
