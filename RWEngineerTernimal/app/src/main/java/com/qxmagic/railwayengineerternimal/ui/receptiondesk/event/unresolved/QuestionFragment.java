package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerQuestionComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.QuestionModule;
import com.qxmagic.railwayengineerternimal.library.recyclerview.HRecyclerView;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnLoadMoreListener;
import com.qxmagic.railwayengineerternimal.library.recyclerview.OnRefreshListener;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.iview.IQuestionView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.QuestionAdapter;
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
 * 问题fragment
 */
public class QuestionFragment extends BaseFragment<IBasePresenter> implements IQuestionView {
    @BindView(R.id.question_recyclerView)
    HRecyclerView mRecyclerView;
    private LoadMoreFooterView loadMoreFooterView;

    @Inject
    QuestionAdapter mAdapter;

    private ArrayList<QuestionDetailBean> mList = new ArrayList<>();

    private String mRequestId;
    private EventDetailBean detailBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            detailBean = (EventDetailBean) arg.getSerializable("eventBean");
            if (null != detailBean) {
                mRequestId = detailBean.faultType;
            } /*else {
                detailBean = new EventDetailBean();
            }*/
        }
    }

    @Override
    public void getQuestionList(List<QuestionDetailBean> questionBeanList) {
        mRecyclerView.setRefreshing(false);
        mList.clear();
        if (!ListUtil.isEmpty(questionBeanList)) {
            mList.addAll(questionBeanList);
        } else {
            showToast("暂无数据");
        }
        mAdapter.setList(mList);
    }

    @Override
    public void getMoreQuestionList(List<QuestionDetailBean> questionBeanList) {
        if (!ListUtil.isEmpty(questionBeanList)) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            mList.addAll(questionBeanList);
        } else {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
        }
        mAdapter.setList(mList);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_question;
    }

    @Override
    protected void initInjector() {
        DaggerQuestionComponent.builder().questionModule(new QuestionModule(mContext, this, mRequestId)).
                build().inject(this);
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
                Intent intent = new Intent(mContext, QuestionDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailBean", mList.get(pos));
//                intent.putExtra("mRequestId", ((QuestionDetailBean) item).id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.setRefreshing(true);
                mPresenter.getData();
            }
        });
        if (TextUtils.isEmpty(mRequestId)) {
            loadMoreFooterView=new LoadMoreFooterView(mContext);
            mRecyclerView.setLoadMoreFooterView(loadMoreFooterView);
//            loadMoreFooterView = (LoadMoreFooterView) mRecyclerView.getLoadMoreFooterView();
            mRecyclerView.setLoadMoreEnabled(true);
            mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                    mPresenter.getMoreData();
                }
            });
        }
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @OnClick(R.id.add_to_knowledge_confirm)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_to_knowledge_confirm:
                if (null == RWETApplication.getInstance().mLoginUser) {
                    LoginUtil.startLogin(mContext);
                    return;
                }
                Intent intent = new Intent(mContext, NewQuestionActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2000) {
            mPresenter.getData();
        }
    }
}
