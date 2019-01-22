package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IMyWordsPresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.MyWordsPresenter;
import com.qxmagic.railwayuserterminal.ui.event.MyWordsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件留言module
 */
@Module
public class MyWordsModule {
    private final MyWordsActivity mView;
    private final Context mContext;
//    private final EventDetailBean detailBean;
    private final String id;

    public MyWordsModule(MyWordsActivity mView, Context mContext,String id/*, EventDetailBean detailBean*/) {
        this.mView = mView;
        this.mContext = mContext;
        this.id = id;
    }

    @ActivityScope
    @Provides
    public IMyWordsPresenter providePresenter() {
        return new MyWordsPresenter(mView,mContext,id);
    }
}
