package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ChangePresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ChangeAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeFragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 变更module
 */
@Module
public class ChangeModule {
    private final Context mContext;
    private final ChangeFragment mView;
    private final String mRequestId;

    public ChangeModule(Context mContext, ChangeFragment mView, String mRequestId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRequestId = mRequestId;
    }

    @FragmentScope
    @Provides
    public IBasePresenter providePresenter() {
        return new ChangePresenter(mContext, mView, mRequestId);
    }

    @FragmentScope
    @Provides
    public ChangeAdapter provideAdapter() {
        return new ChangeAdapter(new ArrayList<ChangeDetailBean>(), mContext);
    }
}
