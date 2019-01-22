package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.injector.modules.ServiceModule;
import com.qxmagic.railwayuserterminal.ui.service.ServiceFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/17 0017.
 * 服务 component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    void inject(ServiceFragment fragment);
}
