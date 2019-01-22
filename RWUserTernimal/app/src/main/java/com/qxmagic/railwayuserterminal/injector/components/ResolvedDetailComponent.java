package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.injector.modules.ResolvedDetailModule;
import com.qxmagic.railwayuserterminal.ui.event.resolved.EventDetailFragment;

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
