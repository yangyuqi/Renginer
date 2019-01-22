package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IQuestionDetailView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.Util;
import com.qxmagic.railwayengineerternimal.widget.CommonView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/29 0029.
 * 问题详情
 */

public class QuestionDetailActivity extends BaseActivity<IBasePresenter> implements IQuestionDetailView {
    @BindView(R.id.question_detail_content)
    LinearLayout mQuestionContent;
    @BindView(R.id.change_detail_enclosure_name)
    TextView mEnclosureText;

    //    private String mRequestId;
    private QuestionDetailBean detailBean;

    private String[] keys = {"单号：", "标题：", "组织：", "发起人：", "来源：", "服务：", "服务子项：", "状态：", "产品："};
    private String[] keys1 = {"紧急程度：", "问题分类：", "描述：", "原因：", "解决方法：", "是否加入知识库："};
    private String[] keys2 = {"开始日期：", "最后修改：", "指派日期：", "TTO超时数："};
    private String[] keys3 = {"维修团队：", "维修工程师："};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
//        initInjector();
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            detailBean = (QuestionDetailBean) bundle.getSerializable("detailBean");
        } else {
            detailBean = new QuestionDetailBean();
        }
    }

    @Override
    public void getQuestionDetail(QuestionDetailBean detailBean) {
        if (null == detailBean) {
            return;
        }
        mQuestionContent.removeAllViews();

        CommonView view = new CommonView(mContext);
        String[] values = {detailBean.id, detailBean.questionTitle, detailBean.organization,
                detailBean.sponsor, detailBean.questionSource, detailBean.serviceType, detailBean.serviceSubType, detailBean.questionState,
                detailBean.production};
        ArrayList<CommonDetailBean> list = _initList(values, keys);
        view.setList(list);
        String[] images = detailBean.images;
        if (null != images && images.length != 0) {
            ArrayList<String> imageList = new ArrayList<>();
            Collections.addAll(imageList, images);
            if (!ListUtil.isEmpty(imageList)) {
                view.setShowImagesLayout(true);
                view.setImagesType("问题图片");
                view.setImagesList(imageList);
            }
        }
        view.init();
        mQuestionContent.addView(view);
        _addLine();

        CommonView view1 = new CommonView(mContext);
        String[] values1 = {detailBean.urgencyLevel, detailBean.questionType, detailBean.description,
                detailBean.questionReason, detailBean.questionResolvent, detailBean.isAddToKnowledge};
        ArrayList<CommonDetailBean> list1 = _initList(values1, keys1);
        view1.setList(list1);
        view1.setShowImagesLayout(false);
        view1.init();
        mQuestionContent.addView(view1);
        _addLine();

        CommonView view2 = new CommonView(mContext);
        String[] values2 = {detailBean.startTime, detailBean.lastChangeTime, detailBean.appointmentTime, "耗时0秒"};
        ArrayList<CommonDetailBean> list4 = _initList(values2, keys2);
        view2.setList(list4);
        view2.setShowImagesLayout(false);
        view2.init();
        mQuestionContent.addView(view2);
        _addLine();

        CommonView view3 = new CommonView(mContext);
        String[] values3 = {detailBean.dealquestionTeam, detailBean.dealMan};
        ArrayList<CommonDetailBean> list3 = _initList(values3, keys3);
        view3.setList(list3);
        view3.setShowImagesLayout(false);
        view3.init();
        mQuestionContent.addView(view3);
        _addLine();

        mEnclosureText.setText(detailBean.annexPath);
    }


    private void _addLine() {
        View seqerateView = new View(mContext);
        seqerateView.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        seqerateView.setLayoutParams(params_1);
        mQuestionContent.addView(seqerateView);
        View seqerateView1 = new View(mContext);
        seqerateView1.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        params_2.setMargins(0, (int) (Util.dpToPixel(mContext, 10f)), 0, 0);
        seqerateView.setLayoutParams(params_2);
        mQuestionContent.addView(seqerateView1);
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
        return R.layout.activity_question_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "问题详情", 0, false, true);
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
        getQuestionDetail(detailBean);
    }

    @Override
    protected void initInjector() {
//        DaggerQuestionDetailComponent.builder().applicationComponent(
//                getAppComponent()).questionDetailModule(new QuestionDetailModule(this, mRequestId)).
//                build().inject(this);
    }
}
