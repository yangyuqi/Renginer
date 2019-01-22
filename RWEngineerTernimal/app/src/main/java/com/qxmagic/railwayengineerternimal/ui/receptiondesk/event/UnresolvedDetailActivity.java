package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.adapter.TabFragmentPagerAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.PublishFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.UnresolvedEventDetailFragment;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UnresolvedDetailActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.unresolved_tabLayout)
    TabLayout tabLayout;

    private String eventId;

    private String title;

    private EventDetailBean detailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailBean = (EventDetailBean) bundle.getSerializable("eventBean");
            if (detailBean != null) {
                eventId = detailBean.id;
                title = detailBean.faultType;
            }
        }
        initViews();
        _configFragments();
    }

    private void _configFragments() {
        List<Fragment> list = new ArrayList<>();
        UnresolvedEventDetailFragment eventDetailFragment = new UnresolvedEventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("eventId", eventId);
        eventDetailFragment.setArguments(bundle);
        list.add(eventDetailFragment);

        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("eventBean", detailBean);
        questionFragment.setArguments(bundle1);
        list.add(questionFragment);

        ChangeFragment changeFragment = new ChangeFragment();
        changeFragment.setArguments(bundle1);
        list.add(changeFragment);

        PublishFragment publishFragment = new PublishFragment();
        publishFragment.setArguments(bundle1);
        list.add(publishFragment);

        List<String> mTitles = new ArrayList<>();
        mTitles.add("事件内容");
        mTitles.add("问题");
        mTitles.add("变更");
        mTitles.add("发布");
        viewPager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager(), list, mTitles));
//        viewPager.addOnPageChangeListener(mViewPagerListener);
        tabLayout.setupWithViewPager(viewPager);
        LinearLayout linear = (LinearLayout) tabLayout.getChildAt(0);
        linear.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linear.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_tablayout_vertical));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_unresolved_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "未解决事件详情", R.mipmap.common_back_icon, 0, false, true, null, null);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }
}
