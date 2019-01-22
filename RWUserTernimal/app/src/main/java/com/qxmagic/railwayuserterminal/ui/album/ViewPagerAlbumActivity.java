package com.qxmagic.railwayuserterminal.ui.album;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.album.component.HackyViewPager;
import com.qxmagic.railwayuserterminal.ui.album.component.PhotoView;
import com.qxmagic.railwayuserterminal.ui.album.component.PhotoViewAttacher;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ImageLoader;
import com.qxmagic.railwayuserterminal.utils.ListUtil;

public class ViewPagerAlbumActivity extends BaseActivity
{
    /**
     * 日志TAG
     */
    private static final String TAG = "===ViewPagerAlbumActivity===";
    
    /**
     * 标题布局
     */
    private LinearLayout mTitleLayout;
    
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
     * 当前位置
     */
    private int currentIndex;
    
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
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    
    /**
     * 大小
     */
    private int size;
    
    /**
     * viewPager
     */
    private HackyViewPager mViewPager;
    
    /**
     * view集合
     */
    private ArrayList<PhotoView> mViewList;
    
    /**
     * 下标
     */
    private int index = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        //        mViewPager = new HackyViewPager(this);
        //        setContentView(mViewPager);
        
        //        mViewPager.setAdapter(new SamplePagerAdapter());
        getBundle();
        initBottomView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_images;
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
        //设置当面默认的位置
        currentIndex = index;
        //设置为白色，即选中状态
        mIndicatorImgs[currentIndex].setEnabled(true);
        
    }
    
    class MyPageAdapter extends PagerAdapter
    {
        
        //        private static int[] sDrawables = { R.drawable.wallpaper,
        //                R.drawable.a1, R.drawable.a2, R.drawable.a3,
        //                R.drawable.wallpaper, R.drawable.wallpaper };
        
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
        public View instantiateItem(ViewGroup container, int position)
        {
            try
            {
                //                ((ViewPager) container).addView(mViewList.get(position));
                if (position == 0 && imageUrlList.size() > 0)
                {
                    ImageLoader.loadCenterCrop(mContext,imageUrlList.get(position),
                            mViewList.get(position),
                            R.mipmap.default_icon);
                }
                // Now just add PhotoView to ViewPager and return it
                container.addView(mViewList.get(position),
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                mViewList.get(position)
                        .setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener()
                        {
                            @Override
                            public void onPhotoTap(View view, float x, float y)
                            {
                                hideOrShowView();
                            }
                        });
                //                mViewList.get(position).setOnClickListener(new OnClickListener()
                //                {
                //                    @Override
                //                    public void onClick(View v)
                //                    {
                //                        hideOrShowView();
                //                    }
                //                });
            }
            catch (Exception e)
            {
//                LogX.getInstance().e(TAG, e.toString());
                Logger.e(e.toString());
            }
            return mViewList.get(position);
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }
    }
    
    @Override
    public void initViews()
    {
        CommonTitleUtil.initCommonTitle(this, title, 0, false, true);
        mViewPager = (HackyViewPager) findViewById(R.id.images_viewpager);
        mTitleLayout = (LinearLayout) findViewById(R.id.images_title_layout);
        mTitleView = (TextView) findViewById(R.id.common_title_center_text);
        mBottomLayout = (LinearLayout) findViewById(R.id.images_bottom_layout);
        
        if (size <= 0)
        {
            return;
        }
        if (imageUrlList != null)
        {
            if (null == mViewList)
            {
                mViewList = new ArrayList<PhotoView>();
            }
            else
            {
                mViewList.clear();
            }
            PhotoView imageView = null;
            for (int i = 0; i < imageUrlList.size(); i++)
            {
                imageView = new PhotoView(mContext);
                imageView.setImageResource(R.mipmap.common_transparent_img);
                mViewList.add(imageView);
            }
            mViewPager.setAdapter(new MyPageAdapter());
            mViewPager.setOnPageChangeListener(new MyPageChangeListener());
            mViewPager.setCurrentItem(index);
        }
        mTitleView.setText("第" + (index + 1) + "张(共" + size + "张)");
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

//    @Override
    public void bindListener()
    {
        
    }
    
//    @Override
    public int getActivityLayout()
    {
        return R.layout.activity_images;
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
            setCurIndicator(arg0);
            mTitleView.setText("第" + (arg0 + 1) + "张(共" + size + "张)");
            ImageLoader.loadCenterCrop(mContext,imageUrlList.get(arg0),
                    mViewList.get(arg0),
                   R.mipmap.default_icon);
        }
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
    
//    @Override
    public void getBundle()
    {
        imageUrlList = getIntent().getStringArrayListExtra("imageUrlList");
        index = getIntent().getIntExtra("index", 0);
        if (!ListUtil.isEmpty(imageUrlList))
        {
            size = imageUrlList.size();
            title = "第" + (index + 1) + "张(共" + size + "张)";
        }
    }
    
//    @Override
    public void initAdapter()
    {
        
    }
}
