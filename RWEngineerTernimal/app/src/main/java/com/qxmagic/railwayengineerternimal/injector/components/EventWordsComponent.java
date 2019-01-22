package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.EventWordsModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.EventWordsFragment;

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
