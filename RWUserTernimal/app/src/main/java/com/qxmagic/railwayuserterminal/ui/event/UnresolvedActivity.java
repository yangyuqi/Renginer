package com.qxmagic.railwayuserterminal.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.injector.components.DaggerUnresolvedComponent;
import com.qxmagic.railwayuserterminal.injector.modules.UnresolvedModule;
import com.qxmagic.railwayuserterminal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayuserterminal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayuserterminal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.event.adapter.UnresovledAdapter;
import com.qxmagic.railwayuserterminal.ui.iview.IUnresolvedView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 未解决事件界面
 */
public class UnresolvedActivity extends BaseActivity<IBasePresenter> implements IUnresolvedView {
    @BindView(R.id.unresolved_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    private ArrayList<EventDetailBean> mList = new ArrayList<>();

    @Inject
    UnresovledAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        updateViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_unresolved;
    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.event_unresolved), R.mipmap.common_back_icon, R.mipmap.event_edit_icon, false, true, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar(view, "编辑未解决事件", "", null);
            }
        });
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                if(ListUtil.isEmpty(mList) || mList.size()<pos){
                    return;
                }
                Intent intent = new Intent(mContext, UnresolvedDetailActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("detailBean",mList.get(pos));
//                intent.putExtras(bundle);
                intent.putExtra("eventId", ((EventDetailBean) item).id);
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
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerUnresolvedComponent.builder().
                applicationComponent(getAppComponent()).
                unresolvedModule(new UnresolvedModule(mContext, this)).
                build().inject(this);
    }

    @Override
    public void getEventList(List<EventDetailBean> unresolvedEventBeanList) {
        //获得未解决事件集合
        mRecyclerView.setRefreshing(false);
        //获得已解决事件集合
        mList.clear();
        if (!ListUtil.isEmpty(unresolvedEventBeanList)) {
            mList.addAll(unresolvedEventBeanList);
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
}
