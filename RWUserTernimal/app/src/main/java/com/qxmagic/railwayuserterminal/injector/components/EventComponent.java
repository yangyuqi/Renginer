package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.injector.modules.EventModule;
import com.qxmagic.railwayuserterminal.ui.event.EventFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/17 0017.
 * 事件component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = EventModule.class)
public interface EventComponent {
    void inject(EventFragment fragment);
}
