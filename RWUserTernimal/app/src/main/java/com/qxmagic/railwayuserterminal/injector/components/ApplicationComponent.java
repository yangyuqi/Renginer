package com.qxmagic.railwayuserterminal.injector.components;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Christian on 2017/3/16
 * Application Component 不做注入操作 值提供全局单利数据
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Context getContext();
}
