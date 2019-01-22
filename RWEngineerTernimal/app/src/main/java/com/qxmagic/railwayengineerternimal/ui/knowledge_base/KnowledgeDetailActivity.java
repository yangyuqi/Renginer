package com.qxmagic.railwayengineerternimal.ui.knowledge_base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.adapter.KnowledgeDetailAdapter;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/27 0027.
 * 知识详情
 */

public class KnowledgeDetailActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.knowledge_detail_recyclerView)
    RecyclerView mRecyclerView;
    private KnowledgeBean mBean;

    private KnowledgeDetailAdapter mAdapter;

    private ArrayList<CommonDetailBean> mList = new ArrayList<>();
    private String[] keys = {"知识名称：", "创建人：", "创建时间：", "关联问题：", "知识类型：", "知识描述："};
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
                mBean = (KnowledgeBean) bundle.getSerializable("knowledgeBean");
            } else {
                mBean = new KnowledgeBean();
            }
        }
        _initList();
        mAdapter = new KnowledgeDetailAdapter(mList, mContext);
    }

    private void _initList() {
        values.add(mBean.knowledgeTitle);
        values.add(mBean.knowledgeUser);
        values.add(mBean.knowledgeStorageTime);
        values.add(mBean.relateQuestion);
        values.add(mBean.knowledgeType);
        values.add(mBean.repairInformation);

        for (int i = 0; i < keys.length; i++) {
            CommonDetailBean bean = new CommonDetailBean();
            bean.key = keys[i];
            bean.value = values.get(i);
            mList.add(bean);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_knowledge_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "知识详情", R.mipmap.common_back_icon, false, true);
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
