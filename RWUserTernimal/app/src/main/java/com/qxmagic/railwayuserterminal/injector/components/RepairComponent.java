package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.RepairModule;
import com.qxmagic.railwayuserterminal.ui.event.repair.RepairActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/22 0022.
 * 报修component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = RepairModule.class)
public interface RepairComponent {
    void inject(RepairActivity activity);
}
