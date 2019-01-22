package com.qxmagic.railwayuserterminal.injector.modules;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairImagesPresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.RepairImagesPresenter;
import com.qxmagic.railwayuserterminal.ui.event.repair.RepairImagesActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/23 0023.
 * 报修图片module
 */
@Module
public class RepairImagesModule {
    private final RepairImagesActivity mView;

    public RepairImagesModule(RepairImagesActivity mView) {
        this.mView = mView;
    }

    @ActivityScope
    @Provides
    public IRepairImagesPresenter providePresenter() {
        return new RepairImagesPresenter(mView);
    }
}
