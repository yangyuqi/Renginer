package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;


import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IModifyPasswordPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.ModifyPasswordPresenter;
import com.qxmagic.railwayengineerternimal.ui.mine.ModifyPasswordActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码module
 */
@Module
public class ModifyPasswordModule {
    private final ModifyPasswordActivity mView;
    private final Context mContext;

    public ModifyPasswordModule(ModifyPasswordActivity mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @ActivityScope
    @Provides
    public IModifyPasswordPresenter provideModifyPresenter() {
        return new ModifyPasswordPresenter(mView, mContext);
    }
}
