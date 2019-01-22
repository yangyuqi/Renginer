package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerResolvedComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ResolvedModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.ResovledAdapter;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已解决事件界面
 */
public class ResolvedActivity extends BaseActivity<IResolvedPresenter> implements IEventView {
    @BindView(R.id.resolved_checkbox_layout)
    View mCheckBoxLayout;
    @BindView(R.id.resolved_checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.resolved_selected_event_count)
    TextView mCount;

    @BindView(R.id.resolved_editText)
    EditText mSearchEdit;

    @BindView(R.id.resolved_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @BindView(R.id.resolved_bottom_layout)
    View mBottomLayout;

    @Inject
    ResovledAdapter mAdapter;

    private ArrayList<EventDetailBean> mSearchList = new ArrayList<>();

    private boolean isEditModel;
    private ArrayList<EventDetailBean> mList = new ArrayList<>();
    private boolean isSelectedAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        bindListener();
        updateViews();
    }

    private void bindListener() {
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (isSelectedAll) {
                    for (EventDetailBean bean : mList) {
                        bean.check = false;
                    }
                    mAdapter.setList(mList);
                    mCount.setText("已选择的事件(0/" + mList.size() + ")");
                    isSelectedAll = false;
                } else {
                    for (EventDetailBean bean : mList) {
                        bean.check = true;
                    }
                    mAdapter.setList(mList);
                    mCount.setText("已选择的事件(" + mList.size() + "/" + mList.size() + ")");
                    isSelectedAll = true;
                }

            }
        });

        mAdapter.setListener(new ResovledAdapter.CheckBoxChangeListener() {
            @Override
            public void boxStateChange() {
                int count = 0;
                for (EventDetailBean bean : mList) {
                    if (bean.check) {
                        count++;
                    }
                }
                mCount.setText("已选择的事件(" + count + "/" + mList.size() + ")");
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_resolved;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.event_resolved), R.mipmap.common_back_icon, 0, false, true, null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ListUtil.isEmpty(mList)) {
                    showToast("事件列表为空");
                    return;
                }
                if (!isEditModel) {
                    mCheckBoxLayout.setVisibility(View.VISIBLE);
                    mBottomLayout.setVisibility(View.VISIBLE);
                    mCount.setText("已选择的事件(0/" + mList.size() + ")");
                    mAdapter.setShowCheckBox(true);
                    isEditModel = true;
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                if (isEditModel) {
                    mList.get(pos).check = true;
                    mAdapter.notifyDataSetChanged();
                } else {
                    Intent intent = new Intent(mContext, ResolvedDetailActivity.class);
                    intent.putExtra("eventId", mList.get(pos).id);
                    startActivity(intent);
                }
            }
        });
        loadMoreFooterView= (LoadMoreFooterView) mRecyclerView.getLoadMoreFooterView();
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

    @OnClick({R.id.resolved_delete_btn, R.id.resolved_cancel_btn, R.id.resolved_search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resolved_delete_btn:
                ArrayList<EventDetailBean> deleteList = new ArrayList<>();
                for (EventDetailBean bean : mList) {
                    if (bean.check) {
                        deleteList.add(bean);
                    }
                }
                if (ListUtil.isEmpty(deleteList)) {
                    showToast("未选中任何事件");
                    return;
                } else {
                    showToast("是否要弹出删除对话框?");
                    for (int i = mList.size() - 1; i >= 0; i--) {
                        //TODO 删除操作 添加进度条
                        EventDetailBean bean = mList.get(i);
                        if (bean.check) {
                            //直接进行删除操作，删除完成后再次请求数据
//                            mPresenter.deleteEvent(bean.id);
                            mList.remove(i);
                        }
                    }
//                    mPresenter.getData();
                    mAdapter.setList(mList);
                }

                break;
            case R.id.resolved_cancel_btn:
                hideEditModel();
                break;
            case R.id.resolved_search_btn:
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

    private void hideEditModel() {
        isEditModel = false;
        isSelectedAll = false;
        for (EventDetailBean b : mList) {
            b.check = false;
        }
        mAdapter.setShowCheckBox(false);
        mAdapter.setList(mList);
        mCheckBoxLayout.setVisibility(View.GONE);
        mBottomLayout.setVisibility(View.GONE);
    }


    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerResolvedComponent.builder().applicationComponent(getAppComponent()).resolvedModule(new ResolvedModule(mContext, this)).build().inject(this);
    }


    @Override
    public void onBackPressed() {
        if (isEditModel) {
            hideEditModel();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void getEventList(List<EventDetailBean> eventBeanList) {
        //获得已解决事件集合
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
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        if (!ListUtil.isEmpty(eventList)) {
            mList.addAll(eventList);
        } else {
//            showToast("已无更多数据");
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }
}
