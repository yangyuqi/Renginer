package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.RWUTApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/16.
 * Application module
 */

@Module
public class ApplicationModule {
    private final RWUTApplication mApplication;

    public ApplicationModule(RWUTApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }
}
