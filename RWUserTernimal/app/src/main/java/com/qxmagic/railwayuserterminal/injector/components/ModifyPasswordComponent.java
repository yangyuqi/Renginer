package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.ModifyPasswordModule;
import com.qxmagic.railwayuserterminal.ui.mine.ModifyPasswordActivity;

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
