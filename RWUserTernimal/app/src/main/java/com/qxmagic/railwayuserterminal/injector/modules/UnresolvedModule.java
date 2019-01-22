package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.UnresolvedPresenter;
import com.qxmagic.railwayuserterminal.ui.event.UnresolvedActivity;
import com.qxmagic.railwayuserterminal.ui.event.adapter.UnresovledAdapter;

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
    public IBasePresenter providePresenter() {
        return new UnresolvedPresenter(mView, mContext);
    }

    @ActivityScope
    @Provides
    public UnresovledAdapter provideAdapter() {
        return new UnresovledAdapter(new ArrayList<EventDetailBean>(), mContext);
    }
}
