package com.qxmagic.railwayuserterminal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayuserterminal.injector.FragmentScope;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IServicePresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.ServicePresenter;
import com.qxmagic.railwayuserterminal.ui.service.ServiceFragment;
import com.qxmagic.railwayuserterminal.ui.service.adapter.ServiceAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * Service module
 */
@Module
public class ServiceModule {
    private final Context mContext;
    private final ServiceFragment mView;

    public ServiceModule(Context mContext, ServiceFragment mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @FragmentScope
    @Provides
    public IServicePresenter provideServicePresenter() {
        return new ServicePresenter(mView, mContext);
    }

    @FragmentScope
    @Provides
    public ServiceAdapter provideServiceAdapter() {
        return new ServiceAdapter(new ArrayList<ContractDetailBean>(), mContext);
    }

}
