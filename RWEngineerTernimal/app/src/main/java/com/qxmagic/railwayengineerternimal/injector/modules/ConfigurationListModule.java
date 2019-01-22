package com.qxmagic.railwayengineerternimal.injector.modules;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ConfigurationListPresenter;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationListActivity;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.adapter.ConfigurationListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 配置项列表module
 */
@Module
public class ConfigurationListModule {
    private final ConfigurationListActivity mView;
    private final Context mContext;

    public ConfigurationListModule(ConfigurationListActivity mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @ActivityScope
    @Provides
    public IBasePresenter provideConfigurationPresenter() {
        return new ConfigurationListPresenter(mView, mContext);
    }

    @ActivityScope
    @Provides
    public ConfigurationListAdapter provideAdapter() {
        return new ConfigurationListAdapter(new ArrayList<ConfigurationBean>(), mContext);
    }

}
