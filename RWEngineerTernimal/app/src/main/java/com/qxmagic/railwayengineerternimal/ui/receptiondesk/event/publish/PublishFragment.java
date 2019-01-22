package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerPublishComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.PublishModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IPublishView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.PublishAdapter;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.LoginUtil;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Christian on 2017/3/29 0029.
 * 变发布fragment
 */

public class PublishFragment extends BaseFragment<IBasePresenter> implements IPublishView {
    @BindView(R.id.publish_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    PublishAdapter mAdapter;

    private ArrayList<PublishDetailBean> mList = new ArrayList<>();

    private String title;
    private EventDetailBean detailBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            detailBean = (EventDetailBean) arg.getSerializable("eventBean");
            if (null != detailBean) {
                title = detailBean.faultType;
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_publish;
    }

    @Override
    protected void initInjector() {
        DaggerPublishComponent.builder().publishModule(new PublishModule(mContext, this, title)).build().inject(this);
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                Intent intent = new Intent(mContext, PublishDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailBean", mList.get(pos));
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
//                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
//                mPresenter.getMoreData();
            }
        });
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @OnClick(R.id.new_publish_confirm)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_publish_confirm:
                if (null == RWETApplication.getInstance().mLoginUser) {
                    LoginUtil.startLogin(mContext);
                    return;
                }
                Intent intent = new Intent(mContext, NewPublishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("eventBean", detailBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2000);
                break;
            default:
                break;
        }
    }


    @Override
    public void getPublishList(List<PublishDetailBean> publishBeanList) {
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(publishBeanList)) {
            mList.addAll(publishBeanList);
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMorePublishList(List<PublishDetailBean> publishBeanList) {
        if (!ListUtil.isEmpty(publishBeanList)) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            mList.addAll(publishBeanList);
        } else {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2000) {
            mPresenter.getData();
        }
    }
}
