package com.qxmagic.railwayengineerternimal.ui.knowledge_base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerKnowledgeBaseComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.KnowledgeBaseModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IKnowledgeBaseView;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.adapter.KnowledgeBaseAdapter;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.widget.refresh.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/27 0027.
 * 知识库fragment
 */
public class KnowledgeBaseFragment extends BaseFragment<IBasePresenter> implements IKnowledgeBaseView {
    @BindView(R.id.knowledge_base_editText)
    EditText mSearchEdit;
    @BindView(R.id.knowledge_base_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    KnowledgeBaseAdapter mAdapter;

    private ArrayList<KnowledgeBean> mList = new ArrayList<>();

    private ArrayList<KnowledgeBean> mSearchList = new ArrayList<>();

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(mContext, AddKnowledgeActivity.class));
        }
    };

    @Override
    public void getKnowledgeList(List<KnowledgeBean> knowledgeList) {
        //获得知识库列表
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(knowledgeList)) {
            mList.addAll(knowledgeList);
            mSearchList = (ArrayList<KnowledgeBean>) mList.clone();
        }else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreKnowledgeList(List<KnowledgeBean> knowledgeList) {
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        if (!ListUtil.isEmpty(knowledgeList)) {
            mList.addAll(knowledgeList);
        }else {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_knowledge_base;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView, "知识库", R.mipmap.add_knowledge_icon, R.mipmap.common_back_icon, listener, null, false, true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                Intent intent = new Intent(mContext, KnowledgeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("knowledgeBean", (KnowledgeBean) item);
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
        hideSoftInputFromWindow(mSearchEdit);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerKnowledgeBaseComponent.builder().
                applicationComponent(getAppComponent()).knowledgeBaseModule(
                new KnowledgeBaseModule(this, mContext)).
                build().inject(this);
    }

    @OnClick(R.id.knowledge_base_search_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.knowledge_base_search_btn:
                //搜索关键字
                String key = mSearchEdit.getText().toString().trim();
                if (ListUtil.isEmpty(mSearchList)) {
                    return;
                }
                if (!TextUtils.isEmpty(key)) {
                    mList.clear();
                    for (KnowledgeBean bean : mSearchList) {
                        if (TextUtils.isEmpty(bean.knowledgeTitle)) {
                            continue;
                        }
                        if (bean.knowledgeTitle.contains(key)) {
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
}
