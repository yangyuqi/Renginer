package com.qxmagic.railwayuserterminal.injector.modules;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IMinePresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.MinePresenter;
import com.qxmagic.railwayuserterminal.ui.mine.MineFragment;

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
