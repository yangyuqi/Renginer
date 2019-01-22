package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerChangeComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ChangeModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IChangeView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ChangeAdapter;
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
 * 变更fragment
 */

public class ChangeFragment extends BaseFragment<IBasePresenter> implements IChangeView {
    @BindView(R.id.change_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    ChangeAdapter mAdapter;

    private ArrayList<ChangeDetailBean> mList = new ArrayList<>();

    private String title;
    private EventDetailBean detailBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            detailBean= (EventDetailBean) arg.getSerializable("eventBean");
            if(null!=detailBean){
                title = detailBean.faultType;
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_change;
    }

    @Override
    protected void initInjector() {
        DaggerChangeComponent.builder().changeModule(new ChangeModule(mContext, this, title)).build().inject(this);
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
                Intent intent = new Intent(mContext, ChangeDetailActivity.class);
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
//        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
//                mPresenter.getMoreData();
//            }
//        });
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @OnClick(R.id.new_change_confirm)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_change_confirm:
                if (null == RWETApplication.getInstance().mLoginUser) {
                    LoginUtil.startLogin(mContext);
                    return;
                }
                Intent intent=new Intent(mContext, NewChangeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("eventBean",detailBean);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2000);
                break;
            default:
                break;
        }
    }

    @Override
    public void getChangeList(List<ChangeDetailBean> changeBeanList) {
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(changeBeanList)) {
            mList.addAll(changeBeanList);
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreChangeList(List<ChangeDetailBean> changeBeanList) {
        if (!ListUtil.isEmpty(changeBeanList)) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            mList.addAll(changeBeanList);
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
