package com.qxmagic.railwayengineerternimal.injector.modules;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ReceptionDeskPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.ReceptionDeskFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的module
 */
@Module
public class ReceptionDeskModule {
    private final ReceptionDeskFragment mView;
    private final Context mContext;

    public ReceptionDeskModule(ReceptionDeskFragment mView,Context mContext) {
        this.mView = mView;
        this.mContext=mContext;
    }

    @FragmentScope
    @Provides
    public IBasePresenter provideMinePresenter() {
        return new ReceptionDeskPresenter(mView, mContext);
    }
}
