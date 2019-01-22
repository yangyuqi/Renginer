package com.qxmagic.railwayengineerternimal.injector.components;


import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ModifyPasswordModule;
import com.qxmagic.railwayengineerternimal.ui.mine.ModifyPasswordActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ModifyPasswordModule.class)
public interface ModifyPasswordComponent {
    void inject(ModifyPasswordActivity activity);
}
