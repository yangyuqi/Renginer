package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.EventDetailPresenter;
import com.qxmagic.railwayuserterminal.ui.event.UnresolvedDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件详情module
 */
@Module
public class UnresolvedDetailModule {
    private final UnresolvedDetailActivity mView;
    private final String mEventId;
    private final Context mContext;

    public UnresolvedDetailModule(Context mContext, UnresolvedDetailActivity mView, String mEventId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @ActivityScope
    @Provides
    public IBasePresenter providePresenter() {
        return new EventDetailPresenter(mView,mContext, mEventId);
    }

//    @ActivityScope
//    @Provides
//    public BaseRecyclerViewAdapter provideAdapter() {
//        return new RepairImagesAdapter(new ArrayList<String>(), mContext);
//    }
}
