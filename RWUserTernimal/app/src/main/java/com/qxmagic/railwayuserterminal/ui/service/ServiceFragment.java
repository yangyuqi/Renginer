package com.qxmagic.railwayuserterminal.ui.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.injector.components.DaggerServiceComponent;
import com.qxmagic.railwayuserterminal.injector.modules.ServiceModule;
import com.qxmagic.railwayuserterminal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayuserterminal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayuserterminal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IServicePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseFragment;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.iview.IServiceView;
import com.qxmagic.railwayuserterminal.ui.service.adapter.ServiceAdapter;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.widget.VerticalTextview;
import com.qxmagic.railwayuserterminal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/17 0017.
 * 服务fragment
 */

public class ServiceFragment extends BaseFragment<IServicePresenter> implements IServiceView {
    @BindView(R.id.service_noti_text)
    VerticalTextview mNotiContent;
    @BindView(R.id.service_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    ServiceAdapter mAdapter;

    private ArrayList<ContractDetailBean> mList = new ArrayList<>();

    private ArrayList<ContractDetailBean> notiList = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initInjector() {
        DaggerServiceComponent.builder().applicationComponent(getAppComponent()).serviceModule(new ServiceModule(mContext, this)).build().inject(this);
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView, "服务", 0, R.mipmap.common_back_icon, null, null, true, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                if (ListUtil.isEmpty(mList) || mList.size() < pos) {
                    return;
                }
                Intent intent = new Intent(mContext, ContractDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailBean", mList.get(pos));
                intent.putExtras(bundle);
                startActivity(intent);
//                showSnackBar(itemView, ((ServiceBean) item).description, "", null);
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

        mNotiContent.setText(15, 10, Color.RED);
        mNotiContent.setTextStillTime(3000);//设置停留时长间隔
        mNotiContent.setAnimTime(500);//设置进入和退出的时间间隔
        mNotiContent.setOnItemClickListener(new VerticalTextview.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (ListUtil.isEmpty(notiList) || notiList.size() < position) {
                    return;
                }
                Intent intent = new Intent(mContext, ContractDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailBean", notiList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
        mPresenter.getNotiBean();
    }


    @Override
    public void getServiceList(ArrayList<ContractDetailBean> list) {
        mRecyclerView.setRefreshing(false);
        //获得已解决事件集合
        mList.clear();
        if (!ListUtil.isEmpty(list)) {
            mList.addAll(list);
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreServiceList(ArrayList<ContractDetailBean> list) {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        if (!ListUtil.isEmpty(list)) {
            mList.addAll(list);
        } else {
            showToast("已无更多数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void loadNotificationInfo(ArrayList<ContractDetailBean> list) {
        if (ListUtil.isEmpty(list)) {
            return;
        }
        notiList = list;
        ArrayList<String> noList = new ArrayList<>();
        for (ContractDetailBean detailBean : notiList) {
            noList.add(detailBean.title);
        }
        mNotiContent.setTextList(noList);
        mNotiContent.startAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!ListUtil.isEmpty(notiList)) {
            mNotiContent.startAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!ListUtil.isEmpty(notiList)) {
            mNotiContent.stopAutoScroll();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!ListUtil.isEmpty(notiList)) {
            if (hidden) {
                mNotiContent.stopAutoScroll();
            } else {
                mNotiContent.startAutoScroll();
            }
        }

    }
}
