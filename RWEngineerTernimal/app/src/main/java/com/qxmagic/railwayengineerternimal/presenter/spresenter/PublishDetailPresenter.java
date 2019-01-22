package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IPublishDetailView;

/**
 * Created by Christian on 2017/3/21 0021.
 * 获得发布详情presenter
 */

public class PublishDetailPresenter implements IBasePresenter {
    private IPublishDetailView mView;
    private String mEventId;
    private Context mContext;

    public PublishDetailPresenter(Context mContext, IPublishDetailView mView, String mEventId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @Override
    public void getData() {
        //通过事件id请求事件详情并返回
//        PublishDetailBean bean = new PublishDetailBean();
//        bean.publishType = "标准";
//        bean.publishRequestMan = "王工";
//        bean.publishRequestTime = "2016-05-24 16:56:00";
//        bean.priority = "危急";
//        bean.effect = "影响多个部门或公司";
//        bean.publishState = "待审批";
//        bean.publishTitle = "安装系统";
//        bean.publishContent = "系统更换";
//        bean.startTime = "2016-12-31";
//        bean.endTime = "2017-01-31";
//        bean.publishApprovalOpinion = "同意";
//
//        String[] events = mContext.getResources().getStringArray(R.array.effect);
//        bean.publishRelationEvent = events;
//        String[] questions = mContext.getResources().getStringArray(R.array.parent_problem);
//        bean.publishRelationQuestion = questions;
//        String[] chagnes = mContext.getResources().getStringArray(R.array.parent_change);
//        bean.publishRelationChange = chagnes;
//        String[] knowledge = mContext.getResources().getStringArray(R.array.organization);
//        bean.publishRelationKnowledge = knowledge;

//        bean.publishEnclosure = "工作报告书.txt\n137.79kb";
//        mView.getPublishDetail(bean);
    }

    @Override
    public void getMoreData() {

    }
}
