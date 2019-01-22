package com.qxmagic.railwayengineerternimal.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by xjf on 2017/3/8. 首页导航fragment页面切换适配器
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> mFragments;
    
    private List<String> mTitles;
    
    public TabFragmentPagerAdapter(FragmentManager fm,
                                   List<Fragment> mFragments)
    {
        super(fm);
        this.mFragments = mFragments;
    }
    
    public TabFragmentPagerAdapter(FragmentManager fm,
                                   List<Fragment> mFragments, List<String> mTitles)
    {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }
    
    @Override
    public Fragment getItem(int position)
    {
        return mFragments.get(position);
    }
    
    @Override
    public int getCount()
    {
        return mFragments.size();
    }
    
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles == null ? null : mTitles.get(position);
    }
}
