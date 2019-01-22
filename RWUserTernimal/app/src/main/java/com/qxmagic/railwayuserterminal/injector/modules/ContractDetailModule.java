package com.qxmagic.railwayuserterminal.injector.modules;

import com.qxmagic.railwayuserterminal.injector.ActivityScope;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.ContractDetailPresenter;
import com.qxmagic.railwayuserterminal.ui.service.ContractDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/20 0020.
 * 合同详情module
 */
@Module
public class ContractDetailModule {
    private final ContractDetailActivity mView;
    private final String mContractId;

    public ContractDetailModule(ContractDetailActivity mView, String mContractId) {
        this.mView = mView;
        this.mContractId = mContractId;
    }

    @ActivityScope
    @Provides
    public IBasePresenter provideDetailPresenter() {
        return new ContractDetailPresenter(mView, mContractId);
    }
}
