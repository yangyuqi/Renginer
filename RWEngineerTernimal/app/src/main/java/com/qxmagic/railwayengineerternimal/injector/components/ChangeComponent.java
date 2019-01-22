package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ChangeModule;
import com.qxmagic.railwayengineerternimal.injector.modules.QuestionModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change.ChangeFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 变更component
 */
@FragmentScope
@Component(modules = ChangeModule.class)
public interface ChangeComponent {
    void inject(ChangeFragment fragment);
}
