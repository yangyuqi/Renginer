package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.NewChangePresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.NewChangeActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建问题module
 */
@Module
public class NewChangeModule {
    private final NewChangeActivity mView;
    private final Context mContext;

    public NewChangeModule(NewChangeActivity mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @ActivityScope
    @Provides
    public INewVariousPresenter providePresenter(){
        return new NewChangePresenter(mView,mContext);
    }
}
