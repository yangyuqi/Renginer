package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IUnResolvedPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.UnresolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.UnresovledAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.UnresolvedActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未解决事件module
 */
@Module
public class UnresolvedModule {
    private final Context mContext;
    private final UnresolvedActivity mView;

    public UnresolvedModule(Context mContext, UnresolvedActivity mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @ActivityScope
    @Provides
    public IUnResolvedPresenter providePresenter() {
        return new UnresolvedPresenter(mView, mContext);
    }

    @ActivityScope
    @Provides
    public UnresovledAdapter provideAdapter() {
        return new UnresovledAdapter(new ArrayList<EventDetailBean>(), mContext);
    }
}
