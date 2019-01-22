package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.NewQuestionModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.NewQuestionActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建问题component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,modules = NewQuestionModule.class)
public interface NewQuestionComponent {
    void inject(NewQuestionActivity activity);
}
