package com.qxmagic.railwayuserterminal.injector.modules;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.presenter.spresenter.EventPresenter;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.event.EventFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 事件module
 */
@Module
public class EventModule {
    private final EventFragment mView;

    public EventModule(EventFragment mView) {
        this.mView = mView;
    }

    @FragmentScope
    @Provides
    public IBasePresenter provideEventPresenter() {
        return new EventPresenter(mView);
    }

}
