package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IMyWordsPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IMyWordsView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件留言presenter
 */

public class MyWordsPresenter implements IMyWordsPresenter {
    private IMyWordsView mView;
    private Context mContext;
//    private EventDetailBean detailBean;
    private String eventId;

    public MyWordsPresenter(IMyWordsView mView, Context mContext,String eventId /*EventDetailBean detailBean*/) {
        this.mView = mView;
        this.mContext = mContext;
        this.eventId = eventId;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void leavingAMsg(final String content, String evaluate) {
        //发送留言请求 并返回结果
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("nature", content);
        params.put("effect", evaluate);
        params.put("uuid", eventId);
//        params.put("orgName", detailBean.organization);
//        params.put("sponsor", detailBean.appointedPerson);
//        params.put("title", detailBean.faultType);
//        params.put("description", detailBean.description);
//        params.put("source", detailBean.source);
//        params.put("state", detailBean.eventState);
//        params.put("service", detailBean.serviceType);
//        params.put("serviceSubkey", detailBean.serviceSubType);
//        params.put("urgencyLevel", detailBean.urgencyLevel);
//        params.put("appointTeam", detailBean.dealEventTeam);
//        params.put("appointPerson", detailBean.dealMan);
//        params.put("parentRequest", detailBean.parentRequest);
//        params.put("parentEvent", detailBean.parentEvent);
//        params.put("parentProblem", detailBean.parentQuestion);
//        params.put("parentChange", detailBean.parentChange);
//        params.put("str2", detailBean.images);
        RetrofitService.leavingAMessage(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在评论中...", false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.leavingamsgResult("-1");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                if (commonResp != null) {
                    String status = commonResp.status;
                    if ("1".equals(status)) {
                        mView.leavingamsgResult("ok");
                    } else {
                        mView.leavingamsgResult("fail");
                    }
                }
            }
        });

    }
}
