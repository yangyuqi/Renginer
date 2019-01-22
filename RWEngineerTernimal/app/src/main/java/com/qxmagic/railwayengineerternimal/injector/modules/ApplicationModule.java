package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.RWETApplication;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/16.
 * Application module
 */

@Module
public class ApplicationModule {
    private final RWETApplication mApplication;

    public ApplicationModule(RWETApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }
}
