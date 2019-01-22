package com.qxmagic.railwayengineerternimal.injector.modules;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.EventWordsPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventWordsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件留言module
 */
@Module
public class EventWordsModule {
    private final IEventWordsView mView;
    private final Context mContext;
    private final String mRequestId;

    public EventWordsModule(IEventWordsView mView, Context mContext,String mRequestId) {
        this.mView = mView;
        this.mContext=mContext;
        this.mRequestId = mRequestId;
    }

    @FragmentScope
    @Provides
    public IBasePresenter providePresenter() {
        return new EventWordsPresenter(mView,mContext, mRequestId);
    }
}
