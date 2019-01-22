package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ChangeDetailModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeDetailActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题详情component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class ,modules = ChangeDetailModule.class)
public interface ChangeDetailComponent {
    void inject(ChangeDetailActivity activity);
}
