package com.qxmagic.railwayengineerternimal.ui.configurationitems;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerConfigurationListComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ConfigurationListModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.adapter.ConfigurationListAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IConfigurationListView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项列表界面
 */
public class ConfigurationListActivity extends BaseActivity<IBasePresenter> implements IConfigurationListView {
    //    @BindView(R.id.configuration_editText)
//    EditText mSearchEdit;
    @BindView(R.id.configuration_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    ConfigurationListAdapter mAdapter;

    private ArrayList<ConfigurationBean> mList = new ArrayList<>();

//    private ArrayList<ConfigurationBean> mSearchList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        updateViews();
//        _bindListener();
    }

//    private void _bindListener() {
//        mSearchEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_configuration_list;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "配置项列表", R.mipmap.common_back_icon, 0, false, true, null, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                Intent intent = new Intent(mContext, ConfigurationDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("configurationBean", (ConfigurationBean) item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        loadMoreFooterView = (LoadMoreFooterView) mRecyclerView.getLoadMoreFooterView();
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setRefreshing(true);
                mPresenter.getData();
            }
        });
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                mPresenter.getMoreData();
            }
        });
//        hideSoftInputFromWindow(mSearchEdit);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerConfigurationListComponent.builder().
                applicationComponent(getAppComponent()).configurationListModule(
                new ConfigurationListModule(this, mContext)).
                build().inject(this);
    }

    @Override
    public void getConfigurationList(List<ConfigurationBean> configurationList) {
        //获得配置项列表
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(configurationList)) {
            mList.addAll(configurationList);
//            mSearchList = (ArrayList<ConfigurationBean>) mList.clone();
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreConfigurationList(List<ConfigurationBean> configurationList) {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        if (!ListUtil.isEmpty(configurationList)) {
            mList.addAll(configurationList);
        } else {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

//    @OnClick(R.id.configuration_search_btn)
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.configuration_search_btn:
//
//                //搜索关键字
//                String key = mSearchEdit.getText().toString().trim();
//                if (ListUtil.isEmpty(mSearchList)) {
//                    return;
//                }
//                if (!TextUtils.isEmpty(key)) {
//                    mList.clear();
//                    for (ConfigurationBean bean : mSearchList) {
//                        if (TextUtils.isEmpty(bean.configurationName)) {
//                            continue;
//                        }
//                        if (bean.configurationName.contains(key)) {
//                            mList.add(bean);
//                        }
//                    }
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    mList.clear();
//                    mList.addAll(mSearchList);
//                    mAdapter.setList(mList);
//                }
//                break;
//            default:
//                break;
//        }
//    }
}
