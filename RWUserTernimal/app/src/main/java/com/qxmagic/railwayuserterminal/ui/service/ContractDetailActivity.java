package com.qxmagic.railwayuserterminal.ui.service;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IContractDetailView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

import butterknife.BindView;

/**
 * 合同详情页
 */
public class ContractDetailActivity extends BaseActivity<IBasePresenter> implements IContractDetailView {
    @BindView(R.id.contract_detail_content)
    TextView mContractDetail;
    @BindView(R.id.contract_detail_title)
    TextView mContractTitle;
    private ContractDetailBean mDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initInjector();
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        if (null != intent) {
            Bundle bundle = intent.getExtras();
            if (null != bundle) {
                mDetailBean = (ContractDetailBean) bundle.getSerializable("detailBean");
            } else {
                mDetailBean = new ContractDetailBean();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_contract_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, mDetailBean.description, 0, false, true);
//        initActionBar(mDetailBean.description);
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
        getContractDetail(mDetailBean);
    }

    @Override
    protected void initInjector() {
//        DaggerContractDetailComponent.builder().
//                applicationComponent(getAppComponent()).
//                contractDetailModule(new ContractDetailModule(this, mDetailBean.id)).
//                build().inject(this);
    }

    @Override
    public void getContractDetail(ContractDetailBean detailBean) {
        //请求成功之后的回调
        String title = "客户合同：" + detailBean.title;
        mContractTitle.setText(title);
        String sb = "名称：" + detailBean.title + "\n付款周期: " + detailBean.payCycle + "\n客户名称: " + detailBean.clientName +
                "\n货币单位: " + detailBean.moneyUnit + "\n描述: " + detailBean.description +
                "\n供应商: " + detailBean.supplier + "\n状态: " + detailBean.state +
                "\n开始日期：" + detailBean.startTime + "\n结束日期: " + detailBean.endTime +
                "\n合同类型：" + detailBean.contractType + "\n合同额: " + detailBean.contractAmount +
                "\n货币类型: " + detailBean.currencyType;
        mContractDetail.setText(sb);
    }
}
