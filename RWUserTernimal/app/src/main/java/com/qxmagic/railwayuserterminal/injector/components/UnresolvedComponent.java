package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.UnresolvedModule;
import com.qxmagic.railwayuserterminal.ui.event.UnresolvedActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未解决事件component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = UnresolvedModule.class)
public interface UnresolvedComponent {
    void inject(UnresolvedActivity activity);
}
