package com.qxmagic.railwayuserterminal.injector.components;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.injector.modules.MineModule;
import com.qxmagic.railwayuserterminal.ui.mine.MineFragment;

import dagger.Component;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的component
 */
@FragmentScope
@Component(dependencies = ApplicationComponent.class, modules = MineModule.class)
public interface MineComponent {
    void infect(MineFragment fragment);
}
