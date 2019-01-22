package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IQuestionDetailView;

/**
 * Created by Christian on 2017/3/21 0021.
 * 获得问题详情presenter
 */

public class QuestionDetailPresenter implements IBasePresenter {
    private IQuestionDetailView mView;
    private String mEventId;

    public QuestionDetailPresenter(IQuestionDetailView mView, String mEventId) {
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @Override
    public void getData() {
        //通过事件id请求事件详情并返回
        QuestionDetailBean bean = new QuestionDetailBean();
        bean.id = "p003031";
        bean.questionTitle = "巡检发现服务器故障";
        bean.organization = "南京中铁信息工程有限公司";
        bean.appointedPerson = "宫震伟";
        bean.questionState = "关闭";
        bean.priority = "危急";
        bean.serviceType = "南京油运服务目录";
        bean.serviceSubType = "ITSS-04020201-IBM小型机服务";
        bean.questionSource = "保密";
        bean.production = "保密";
        bean.questionType = "产品老化";
        bean.images = new String[]{"http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg"};

        bean.effect = "部门";
        bean.urgencyLevel = "危急";
        bean.description = "巡检过程中发现一台服务器出现故障";
        bean.questionReason = "巡检过程中发现一台服务器出现故障";
        bean.questionResolvent = "巡检过程中发现一台服务器出现故障";
        bean.isAddToKnowledge = "是";

        bean.startTime = "2016-05-24 16:56:00";
        bean.lastChangeTime = "2016-05-24 16:56:00";
        bean.appointTime = "2016-05-24 16:56:00";
        bean.endTime = "2016-05-24 16:56:00";

        bean.dealquestionTeam = "硬件维护团队";
        bean.dealMan = "刘嘉鹏";

        mView.getQuestionDetail(bean);
    }

    @Override
    public void getMoreData() {

    }
}
