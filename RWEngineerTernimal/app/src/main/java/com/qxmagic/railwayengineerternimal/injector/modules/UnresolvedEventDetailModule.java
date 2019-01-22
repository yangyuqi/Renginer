package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IEventOperationPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.EventDetailPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.UnresolvedEventDetailFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件详情module
 */
@Module
public class UnresolvedEventDetailModule {
    private final UnresolvedEventDetailFragment mView;
    private Context mContext;
    private final String mEventId;

    public UnresolvedEventDetailModule(UnresolvedEventDetailFragment mView, Context mContext, String mEventId) {
        this.mView = mView;
        this.mContext = mContext;
        this.mEventId = mEventId;
    }

    @FragmentScope
    @Provides
    public IEventOperationPresenter providePresenter() {
        return new EventDetailPresenter(mView, mContext, mEventId);
    }
}
