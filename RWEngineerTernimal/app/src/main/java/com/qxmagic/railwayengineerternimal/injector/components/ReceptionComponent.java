package com.qxmagic.railwayengineerternimal.injector.components;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.injector.modules.ReceptionDeskModule;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.ReceptionDeskFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/24 0024.
 * 服务台首页component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = ReceptionDeskModule.class)
public interface ReceptionComponent {
    void inject(ReceptionDeskFragment fragment);
}
