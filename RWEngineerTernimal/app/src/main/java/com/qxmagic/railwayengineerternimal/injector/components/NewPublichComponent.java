package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.NewChangeModule;
import com.qxmagic.railwayengineerternimal.injector.modules.NewPublishModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.NewChangeActivity;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.NewPublishActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建发布component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = NewPublishModule.class)
public interface NewPublichComponent {
    void inject(NewPublishActivity activity);
}
