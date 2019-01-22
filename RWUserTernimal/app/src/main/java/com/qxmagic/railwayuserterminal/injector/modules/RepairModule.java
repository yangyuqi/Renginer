package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairPresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.RepairPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IRepairView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/22 0022.
 * 报修module
 */
@Module
public class RepairModule {
    private final IRepairView mView;
    private final Context mContext;

    public RepairModule(IRepairView mView, Context mContext) {
        this.mView = mView;
        this.mContext=mContext;
    }

    @ActivityScope
    @Provides
    public IRepairPresenter providePresenter() {
        return new RepairPresenter(mView,mContext);
    }
}
