package com.qxmagic.railwayuserterminal.presenter.spresenter;

import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.ui.iview.IContractDetailView;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/3/20 0020.
 * 合同详情presenter
 */

public class ContractDetailPresenter implements IBasePresenter {
    private IContractDetailView mView;
    private String mContractId;

    public ContractDetailPresenter(IContractDetailView mView, String mContractId) {
        this.mView = mView;
        this.mContractId = mContractId;
    }

    @Override
    public void getData() {
        //TODO 通过合同id请求合同详情
        ContractDetailBean bean = new ContractDetailBean();
        bean.title = "itop运维服务\n客户名称：南京中铁信息工程有限公司";
        bean.description = "itop项目的所有任务：\n1.安装（操作系统，apache,itop）\n2.调试\n3.运维支持";
        mView.getContractDetail(bean);
    }

    @Override
    public void getMoreData() {

    }
}
