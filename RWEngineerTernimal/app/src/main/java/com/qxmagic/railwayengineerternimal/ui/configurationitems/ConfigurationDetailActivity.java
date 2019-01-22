package com.qxmagic.railwayengineerternimal.ui.configurationitems;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.adapter.ConfigurationDetailAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项详情
 */

public class ConfigurationDetailActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.configuration_detail_recyclerView)
    RecyclerView mRecyclerView;
    private ConfigurationBean mBean;

    private ConfigurationDetailAdapter mAdapter;

    private ArrayList<ConfigurationBean> mList = new ArrayList<>();
    private String[] keys = {"配置项名称：", "所属组织：","DB服务器： ", "状态：", "业务优先级：", "所在位置：", "品牌：", "型号：", "机架单元：",
            "序列号：", "资产标号：", "开始使用时间：", "采购时间：", "过保日期：", "描述："};
    private ArrayList<String> values = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
                mBean = (ConfigurationBean) bundle.getSerializable("configurationBean");
            } else {
                mBean = new ConfigurationBean();
            }
        }
        _initList();
        mAdapter = new ConfigurationDetailAdapter(mList, mContext);
    }

    private void _initList() {
        values.add(mBean.configurationName);
        values.add(mBean.organization);
        values.add(mBean.dbServer);
        values.add(mBean.state);
        values.add(mBean.priority);
        values.add(mBean.location);
        values.add(mBean.brand);
        values.add(mBean.configurationModel);
        values.add(mBean.frameUnit);
        values.add(mBean.serialNumber);
        values.add(mBean.assetLabel);
        values.add(mBean.startUseTime);
        values.add(mBean.purchasingTime);
        values.add(mBean.insuredDate);
        values.add(mBean.description);

        for (int i = 0; i < keys.length; i++) {
            ConfigurationBean bean = new ConfigurationBean();
            bean.key = keys[i];
            bean.value = values.get(i);
            mList.add(bean);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_configuration_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "配置详情", R.mipmap.common_back_icon, false, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {
    }
}
