package com.qxmagic.railwayengineerternimal;

import android.app.Application;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.injector.components.ApplicationComponent;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerApplicationComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ApplicationModule;
import com.qxmagic.railwayengineerternimal.logic.model.UserBean;
import com.qxmagic.railwayengineerternimal.utils.UEHandler;

/**
 * Created by Christian on 2017/3/24 0024.
 * application实例，用于完成一些初始化操作
 */

public class RWETApplication extends Application {
    private static RWETApplication instance;

    private static ApplicationComponent mComponent;

    public UserBean mLoginUser;

    /**
     * 异常处理类
     */
    private UEHandler ueHandler;

    /**
     * 标记应用是否正在下载
     */
    public boolean isInstalling;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        _initLog();
        _initInjector();
        //初始化网络请求
        RetrofitService.init();
    }

    /**
     * 初始化日打印
     */
    private void _initLog() {
        ueHandler = new UEHandler();
        Thread.setDefaultUncaughtExceptionHandler(ueHandler);
        if (BuildConfig.DEBUG) {
            Logger.init("LogTAG");
        }
    }

    /**
     * 初始化注射器
     * Application中仅是提供一些全局单例数据，不做注入操作
     */
    private void _initInjector() {
        mComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public static RWETApplication getInstance() {
        return instance;
    }

    public static ApplicationComponent getAppComponent() {
        return mComponent;
    }

}
