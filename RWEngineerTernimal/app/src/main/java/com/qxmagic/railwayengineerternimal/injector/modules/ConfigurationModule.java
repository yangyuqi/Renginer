package com.qxmagic.railwayengineerternimal.injector.modules;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ConfigurationPresenter;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 配置项module
 */
@Module
public class ConfigurationModule {
    private final ConfigurationFragment mView;
    private final Context mContext;

    public ConfigurationModule(ConfigurationFragment mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @FragmentScope
    @Provides
    public IBasePresenter provideConfigurationPresenter() {
        return new ConfigurationPresenter(mView, mContext);
    }
}
