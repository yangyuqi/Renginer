package com.qxmagic.railwayengineerternimal.injector.modules;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IMinePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.MinePresenter;
import com.qxmagic.railwayengineerternimal.ui.mine.MineFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的module
 */
@Module
public class MineModule {
    private final MineFragment mView;

    public MineModule(MineFragment mView) {
        this.mView = mView;
    }

    @FragmentScope
    @Provides
    public IMinePresenter provideMinePresenter() {
        return new MinePresenter(mView);
    }
}
