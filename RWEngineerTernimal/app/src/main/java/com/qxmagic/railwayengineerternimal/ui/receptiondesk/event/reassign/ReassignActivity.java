package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.reassign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerReassignComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ReassignModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ReassignAdapter;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/30 0030.
 * 重新指派界面
 */

public class ReassignActivity extends BaseActivity<IResolvedPresenter> implements IEventView {
    @BindView(R.id.reassign_editText)
    EditText mSearchEdit;
    @BindView(R.id.reassign_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    ReassignAdapter mAdapter;


    private ArrayList<EventDetailBean> mList = new ArrayList<>();

    private ArrayList<EventDetailBean> mSearchList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        updateViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reassign;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.event_reassign_event), R.mipmap.common_back_icon, 0, false, true, null, null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                Intent intent = new Intent(mContext, ReassignDetailActivity.class);
                intent.putExtra("eventId", ((EventDetailBean) item).id);
                startActivityForResult(intent, 1000);
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
        hideSoftInputFromWindow(mSearchEdit);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerReassignComponent.builder().applicationComponent(getAppComponent()).
                reassignModule(new ReassignModule(mContext, this)).
                build().inject(this);
    }

    @Override
    public void getEventList(List<EventDetailBean> eventBeanList) {
        //获得重新指派事件集合
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(eventBeanList)) {
            mList.addAll(eventBeanList);
            mSearchList = (ArrayList<EventDetailBean>) mList.clone();
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreEventList(List<EventDetailBean> eventList) {
        if (!ListUtil.isEmpty(eventList)) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            mList.addAll(eventList);
        } else {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

    @OnClick(R.id.reassign_search_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reassign_search_btn:
                //搜索关键字
                String key = mSearchEdit.getText().toString().trim();
                if (ListUtil.isEmpty(mSearchList)) {
                    return;
                }
                if (!TextUtils.isEmpty(key)) {
                    mList.clear();
                    for (EventDetailBean bean : mSearchList) {
                        if (TextUtils.isEmpty(bean.eventType)) {
                            continue;
                        }
                        if (bean.eventType.contains(key)) {
                            mList.add(bean);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } else {
                    mList.clear();
                    mList.addAll(mSearchList);
                    mAdapter.setList(mList);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 1000 == requestCode) {
            mPresenter.getData();
        }
    }
}
