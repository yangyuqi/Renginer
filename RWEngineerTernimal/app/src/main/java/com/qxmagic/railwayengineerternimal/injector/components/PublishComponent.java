package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.PublishModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.PublishFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布component
 */
@FragmentScope
@Component(modules = PublishModule.class)
public interface PublishComponent {
    void inject(PublishFragment fragment);
}
