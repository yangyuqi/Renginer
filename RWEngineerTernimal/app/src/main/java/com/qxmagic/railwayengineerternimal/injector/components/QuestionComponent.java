package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.QuestionModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题component
 */
@FragmentScope
@Component(modules = QuestionModule.class)
public interface QuestionComponent {
    void inject(QuestionFragment fragment);
}
