package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;


import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ResovledAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.ResolvedActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件module
 */
@Module
public class ResolvedModule {
    private final Context mContext;
    private final ResolvedActivity mView;

    public ResolvedModule(Context mContext, ResolvedActivity mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @ActivityScope
    @Provides
    public IResolvedPresenter providePresenter() {
        return new ResolvedPresenter(mContext, mView);
    }

    @ActivityScope
    @Provides
    public ResovledAdapter provideAdapter() {
        return new ResovledAdapter(new ArrayList<EventDetailBean>(), mContext);
    }
}
