package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.NewChangeModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.NewChangeActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建问题component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = NewChangeModule.class)
public interface NewChangeComponent {
    void inject(NewChangeActivity activity);
}
