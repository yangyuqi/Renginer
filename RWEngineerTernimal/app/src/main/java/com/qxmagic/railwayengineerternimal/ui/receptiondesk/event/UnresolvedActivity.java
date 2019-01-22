package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerUnresolvedComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.UnresolvedModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IUnResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IUnresolvedView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.UnresovledAdapter;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.widget.CommonDialog;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 未解决事件界面
 */
public class UnresolvedActivity extends BaseActivity<IUnResolvedPresenter> implements IUnresolvedView {
    @BindView(R.id.unresolved_editText)
    EditText mSearchEdit;
    @BindView(R.id.unresolved_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    UnresovledAdapter mAdapter;

    private ArrayList<EventDetailBean> mList = new ArrayList<>();

    private ArrayList<EventDetailBean> mSearchList = new ArrayList<>();

    private CommonDialog dialog;
    private int currentPosition = -1;

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
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.event_unresolved), R.mipmap.common_back_icon, 0, false, true, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackBar(view, "编辑未解决事件", "", null);
            }
        });
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
                currentPosition = pos;
                if ("已指派".equals(mList.get(pos).eventState)) {
                    showDealDialog(pos);
                } else {
                    dealSuccess();
                }

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

    private void showDealDialog(final int pos) {
        dialog = new CommonDialog(mContext, "提示", "是否受理 " + mList.get(pos).id + "事件", new CommonDialog.OnButtonClickListener() {
            @Override
            public void onCancelClick() {
                currentPosition = -1;
            }

            @Override
            public void onConfirmClick() {
                mPresenter.dealEvent(mList.get(pos).id);
            }
        });
        dialog.setTextGone(false, false);
        dialog.setCanceledOnTouchOutside(false);
        if (!dialog.isShowing()) {
            dialog.showMiddleDialog();
        }
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
    public void getEventList(List<EventDetailBean> eventBeanList) {
        mRecyclerView.setRefreshing(false);
        //获得未解决事件集合
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
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        if (!ListUtil.isEmpty(eventList)) {
            mList.addAll(eventList);
        } else {
//            showToast("已无更多数据");
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

    @OnClick(R.id.unresolved_search_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unresolved_search_btn:
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

    @Override
    public void dealSuccess() {
        if (-1 == currentPosition) {
            return;
        }
        Intent intent = new Intent(mContext, UnresolvedDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("eventBean", mList.get(currentPosition));
        intent.putExtras(bundle);
        startActivityForResult(intent, 1000);

        EventDetailBean b = mList.get(currentPosition);
        b.eventState = "未解决";
        mAdapter.setList(mList);
    }

    @Override
    public void dealFail(String errMsg) {
        showToast(errMsg);
    }
}
