package com.qxmagic.railwayuserterminal.ui.event.resolved;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.adapter.TabFragmentPagerAdapter;
import com.qxmagic.railwayuserterminal.ui.iview.IBaseView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ResolvedDetailActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.resolved_tabLayout)
    TabLayout tabLayout;

//    private String eventId;

    private EventDetailBean detailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        eventId = getIntent().getStringExtra("eventId");
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            detailBean = (EventDetailBean) bundle.getSerializable("detailBean");
            if (null == detailBean) {
                detailBean = new EventDetailBean();
            }
        }

        initViews();
        _configFragments();
    }

    private void _configFragments() {
        List<Fragment> list = new ArrayList<>();
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("eventId", eventId);
        bundle.putSerializable("detailBean",detailBean);
        eventDetailFragment.setArguments(bundle);
        list.add(eventDetailFragment);

        EventWordsFragment eventWordsFragment = new EventWordsFragment();
        eventWordsFragment.setArguments(bundle);
        list.add(eventWordsFragment);
        List<String>mTitles=new ArrayList<>();
        mTitles.add("事件内容");
        mTitles.add("我的留言");
//        mTitles.add("News");
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), list,mTitles));
//        viewPager.addOnPageChangeListener(mViewPagerListener);
        tabLayout.setupWithViewPager(viewPager);
        LinearLayout linear= (LinearLayout) tabLayout.getChildAt(0);
        linear.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linear.setDividerDrawable(ContextCompat.getDrawable(this,R.drawable.divider_tablayout_vertical));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_resolved_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this,"已解决事件详情",R.mipmap.common_back_icon,0,false,true,null,null);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }
}
