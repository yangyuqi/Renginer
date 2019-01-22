package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.NewPublishPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.NewPublishActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建发布module
 */
@Module
public class NewPublishModule {
    private final NewPublishActivity mView;
    private final Context mContext;

    public NewPublishModule(NewPublishActivity mView,  Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @ActivityScope
    @Provides
    public INewVariousPresenter providePresenter(){
        return new NewPublishPresenter(mView,mContext);
    }
}
