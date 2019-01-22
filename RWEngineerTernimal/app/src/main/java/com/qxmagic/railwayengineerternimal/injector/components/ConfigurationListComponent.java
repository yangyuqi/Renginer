package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationListModule;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationModule;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationFragment;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationListActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/24 0024.
 * 配置项列表component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ConfigurationListModule.class)
public interface ConfigurationListComponent {
    void inject(ConfigurationListActivity activity);
}
