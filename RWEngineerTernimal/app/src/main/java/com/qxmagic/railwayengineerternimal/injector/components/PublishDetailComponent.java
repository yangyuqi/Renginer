package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.PublishDetailModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish.PublishDetailActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布详情component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = PublishDetailModule.class)
public interface PublishDetailComponent {
    void inject(PublishDetailActivity activity);
}
