package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationModule;
import com.qxmagic.railwayengineerternimal.injector.modules.ReceptionDeskModule;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.ReceptionDeskFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/24 0024.
 * 配置项component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = ConfigurationModule.class)
public interface ConfigurationComponent {
    void inject(ConfigurationFragment fragment);
}
