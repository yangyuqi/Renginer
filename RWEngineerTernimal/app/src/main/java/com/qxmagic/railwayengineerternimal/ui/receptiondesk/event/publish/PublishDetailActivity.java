package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IPublishDetailView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.widget.CommonView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/29 0029.
 * 发布详情
 */
public class PublishDetailActivity extends BaseActivity<IBasePresenter> implements IPublishDetailView {
    @BindView(R.id.publish_detail_content)
    LinearLayout mPublishContent;
    @BindView(R.id.publish_detail_enclosure_name)
    TextView mEnclosureText;

    @BindView(R.id.publish_relation_event_text)
    TextView mRelationEvents;
    @BindView(R.id.publish_relation_question_text)
    TextView mRelationQuestion;
    @BindView(R.id.publish_relation_change_text)
    TextView mRelationChange;
    @BindView(R.id.publish_relation_knowledge_text)
    TextView mRelationKnowledge;

//    private String mRequestId;

    private PublishDetailBean detailBean;

    private String[] keys = {"发布类型：","组织：", "请求人：", "请求日期：", "优先级：",
            "发布状态：", "标题：", "内容：", "计划开始时间：", "计划结束时间：","审批人：", "审批意见："};
//    private ArrayList<String> mRelationEvents = new ArrayList<>();
//    private ArrayList<String> mRelationQuestion = new ArrayList<>();
//    private ArrayList<String> mRelationChange = new ArrayList<>();
//    private ArrayList<String> mRelationKnowledge = new ArrayList<>();
//    private String mEnclosurePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initInjector();
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            detailBean = (PublishDetailBean) bundle.getSerializable("detailBean");
        } else {
            detailBean = new PublishDetailBean();
        }
    }

    @Override
    public void getPublishDetail(PublishDetailBean detailBean) {
        if (null == detailBean) {
            return;
        }
        mPublishContent.removeAllViews();

        CommonView view = new CommonView(mContext);
        String[] values = {detailBean.publishType,detailBean.organization, detailBean.publishRequestMan, detailBean.publishRequestTime,
                detailBean.priority, detailBean.publishState, detailBean.publishTitle, detailBean.publishContent,
                detailBean.startTime, detailBean.endTime,detailBean.approver,detailBean.publishApprovalOpinion};
        ArrayList<CommonDetailBean> list = _initList(values, keys);
        view.setList(list);
        view.init();
        mPublishContent.addView(view);

//        String[] events = detailBean.publishRelationEvent;
//        Collections.addAll(mRelationEvents, events);
//        String[] question = detailBean.publishRelationQuestion;
//        Collections.addAll(mRelationQuestion, question);
//        String[] change = detailBean.publishRelationChange;
//        Collections.addAll(mRelationChange, change);
//        String[] knowledge = detailBean.publishRelationKnowledge;
//        Collections.addAll(mRelationKnowledge, knowledge);

//        mEnclosurePath = detailBean.publishEnclosure;
        mEnclosureText.setText(detailBean.publishEnclosure);

        mRelationEvents.setText(detailBean.publishRelationEvent);
        mRelationQuestion.setText(detailBean.publishRelationQuestion);
        mRelationChange.setText(detailBean.publishRelationChange);
        mRelationKnowledge.setText(detailBean.publishRelationKnowledge);
    }


    private ArrayList<CommonDetailBean> _initList(String[] values, String[] keys) {
        ArrayList<CommonDetailBean> list = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            CommonDetailBean bean = new CommonDetailBean();
            bean.key = keys[i];
            if (values.length > i && null != values[i]) {
                bean.value = values[i];
            }
            list.add(bean);
        }
        return list;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_publish_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "发布详情", 0, false, true);
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
        getPublishDetail(detailBean);
    }

    @Override
    protected void initInjector() {
//        DaggerPublishDetailComponent.builder().applicationComponent(
//                getAppComponent()).publishDetailModule(new PublishDetailModule(mContext, this, mRequestId)).
//                build().inject(this);
    }

//    @OnClick({R.id.publish_relation_event_pop, R.id.publish_relation_question_pop,
//            R.id.publish_relation_change_pop, R.id.publish_relation_knowledge_pop})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.publish_relation_event_pop:
//                RepairPullDownPop mPop = new RepairPullDownPop(mContext,mRelationEvents,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),null);
//                mPop.showAsDropDown(findViewById(R.id.publish_relation_event_pop));
//                break;
//            case R.id.publish_relation_question_pop:
//                mPop = new RepairPullDownPop(mContext,mRelationQuestion,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),null);
//                mPop.showAsDropDown(findViewById(R.id.publish_relation_question_pop));
//                break;
//            case R.id.publish_relation_change_pop:
//                mPop = new RepairPullDownPop(mContext,mRelationChange,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),null);
//                mPop.showAsDropDown(findViewById(R.id.publish_relation_change_pop));
//                break;
//            case R.id.publish_relation_knowledge_pop:
//                mPop = new RepairPullDownPop(mContext,mRelationKnowledge,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),null);
//                mPop.showAsDropDown(findViewById(R.id.publish_relation_knowledge_pop));
//                break;
//            default:
//                break;
//        }
//    }
}
