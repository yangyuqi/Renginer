package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationListModule;
import com.qxmagic.railwayengineerternimal.injector.modules.KnowledgeBaseModule;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationListActivity;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.KnowledgeBaseFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/24 0024.
 * 知识库component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = KnowledgeBaseModule.class)
public interface KnowledgeBaseComponent {
    void inject(KnowledgeBaseFragment fragment);
}
