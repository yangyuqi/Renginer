package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.AddKnowledgeModule;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.AddKnowledgeActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/29 0029.
 * 添加知识库component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = AddKnowledgeModule.class)
public interface AddKnowledgeComponent {
    void inject(AddKnowledgeActivity activity);
}
