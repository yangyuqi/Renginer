package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.ResolvedModule;
import com.qxmagic.railwayuserterminal.ui.event.ResolvedActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ResolvedModule.class)
public interface ResolvedComponent {
    void inject(ResolvedActivity activity);
}
