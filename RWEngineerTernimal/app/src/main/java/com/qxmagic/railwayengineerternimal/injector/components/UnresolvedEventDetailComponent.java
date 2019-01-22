package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.UnresolvedEventDetailModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.UnresolvedEventDetailFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件详情component
 */
@FragmentScope
@Component(modules = UnresolvedEventDetailModule.class)
public interface UnresolvedEventDetailComponent {
    void inject(UnresolvedEventDetailFragment fragment);
}
