package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.UnresolvedDetailModule;
import com.qxmagic.railwayuserterminal.ui.event.UnresolvedDetailActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件详情component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = UnresolvedDetailModule.class)
public interface UnresolvedDetailComponent {
    void inject(UnresolvedDetailActivity activity);
}
