package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ReassignPresenter;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ReassignAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.reassign.ReassignActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 重新指派事件module
 */
@Module
public class ReassignModule {
    private final Context mContext;
    private final ReassignActivity mView;

    public ReassignModule(Context mContext, ReassignActivity mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @ActivityScope
    @Provides
    public IResolvedPresenter providePresenter() {
        return new ReassignPresenter(mContext, mView);
    }

    @ActivityScope
    @Provides
    public ReassignAdapter provideAdapter() {
        return new ReassignAdapter(new ArrayList<EventDetailBean>(), mContext);
    }
}
