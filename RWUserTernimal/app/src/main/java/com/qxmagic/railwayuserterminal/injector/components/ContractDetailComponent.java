package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.injector.modules.ContractDetailModule;
import com.qxmagic.railwayuserterminal.ui.service.ContractDetailActivity;

import dagger.Component;

/**
 * Created by Christian on 2017/3/20 0020.
 * 合同详情component
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ContractDetailModule.class)
public interface ContractDetailComponent {
    void inject(ContractDetailActivity activity);
}
