package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.injector.modules.EventWordsModule;
import com.qxmagic.railwayuserterminal.ui.event.resolved.EventWordsFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件component
 */
@FragmentScope
@Component(modules = EventWordsModule.class)
public interface EventWordsComponent {
    void inject(EventWordsFragment fragment);
}
