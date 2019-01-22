package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ResolvedDetailModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.EventDetailFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件详情component
 */
@FragmentScope
@Component(modules = ResolvedDetailModule.class)
public interface ResolvedDetailComponent {
    void inject(EventDetailFragment fragment);
}
