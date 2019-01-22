package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.RepairImagesModule;
import com.qxmagic.railwayuserterminal.ui.event.repair.RepairImagesActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/23 0023.
 * 报修图片component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = RepairImagesModule.class)
public interface RepairImagesComponent {
    void inject(RepairImagesActivity activity);
}
