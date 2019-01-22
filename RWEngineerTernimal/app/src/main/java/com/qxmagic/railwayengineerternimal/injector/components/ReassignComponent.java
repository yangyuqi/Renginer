package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ReassignModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.reassign.ReassignActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 重新指派事件component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ReassignModule.class)
public interface ReassignComponent {
    void inject(ReassignActivity activity);
}
