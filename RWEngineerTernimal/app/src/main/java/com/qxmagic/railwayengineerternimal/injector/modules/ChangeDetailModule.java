package com.qxmagic.railwayengineerternimal.injector.modules;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ChangeDetailPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题详情module
 */
@Module
public class ChangeDetailModule {
    private final ChangeDetailActivity mView;
    private final String mEventId;

    public ChangeDetailModule(ChangeDetailActivity mView, String mEventId) {
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @ActivityScope
    @Provides
    public IBasePresenter providePresenter() {
        return new ChangeDetailPresenter(mView, mEventId);
    }
}
