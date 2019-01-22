package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.EventDetailPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.EventDetailFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件详情module
 */
@Module
public class ResolvedDetailModule {
    private final EventDetailFragment mView;
    private final String mEventId;
    private final Context mContext;

    public ResolvedDetailModule(Context mContext, EventDetailFragment mView, String mEventId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @FragmentScope
    @Provides
    public IBasePresenter providePresenter() {
        return new EventDetailPresenter(mView,mContext, mEventId);
    }

}
