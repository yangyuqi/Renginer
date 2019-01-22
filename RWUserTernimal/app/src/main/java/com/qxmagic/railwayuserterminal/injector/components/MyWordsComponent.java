package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.MyWordsModule;
import com.qxmagic.railwayuserterminal.ui.event.MyWordsActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件留言component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = MyWordsModule.class)
public interface MyWordsComponent {
    void inject(MyWordsActivity activity);
}
