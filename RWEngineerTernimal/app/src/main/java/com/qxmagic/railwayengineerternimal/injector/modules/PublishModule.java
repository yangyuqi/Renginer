package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ChangePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.PublishPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ChangeAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.PublishAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.PublishFragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布module
 */
@Module
public class PublishModule {
    private final Context mContext;
    private final PublishFragment mView;
    private final String mRequestId;

    public PublishModule(Context mContext, PublishFragment mView, String mRequestId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRequestId = mRequestId;
    }

    @FragmentScope
    @Provides
    public IBasePresenter providePresenter() {
        return new PublishPresenter(mContext, mView, mRequestId);
    }

    @FragmentScope
    @Provides
    public PublishAdapter provideAdapter() {
        return new PublishAdapter(new ArrayList<PublishDetailBean>(), mContext);
    }
}
