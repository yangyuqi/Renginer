package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.reassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.UnresolvedEventDetailFragment;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

/**
 * Created by Christian on 2017/3/30 0030.
 * 指派详情
 */
public class ReassignDetailActivity extends BaseActivity<IBasePresenter> implements IBaseView{
    private String mRequestId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initViews();
    }

    private void _dealIntent(Intent intent) {
        if(null!=intent){
            mRequestId=intent.getStringExtra("eventId");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reassign_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "指派详情", 0, false, true);
        Bundle bundle = new Bundle();
        bundle.putString("eventId",mRequestId);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.reassign_content,
                        Fragment.instantiate(this,
                                UnresolvedEventDetailFragment.class.getName(),
                                bundle))
                .commit();
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }
}
