package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.QuestionDetailModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionDetailActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题详情component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class ,modules = QuestionDetailModule.class)
public interface QuestionDetailComponent {
    void inject(QuestionDetailActivity activity);
}
