package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.PublishDetailPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.PublishDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布详情module
 */
@Module
public class PublishDetailModule {
    private final PublishDetailActivity mView;
    private final Context mContext;
    private final String mEventId;

    public PublishDetailModule(Context mContext, PublishDetailActivity mView, String mEventId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @ActivityScope
    @Provides
    public IBasePresenter providePresenter() {
        return new PublishDetailPresenter(mContext, mView, mEventId);
    }
}
