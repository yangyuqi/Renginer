package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IEventOperationPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventDetailView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 获得事件详情presenter
 */

public class EventDetailPresenter implements IEventOperationPresenter {
    private IEventDetailView mView;
    private Context mContext;
    private String mEventId;

    public EventDetailPresenter(IEventDetailView mView, Context mContext, String mEventId) {
        this.mView = mView;
        this.mContext = mContext;
        this.mEventId = mEventId;
    }

    @Override
    public void getData() {
        //通过事件id请求事件详情并返回
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("uuid", mEventId);
        RetrofitService.getEventDetail(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<EventDetailBean>bindToLife()).subscribe(new Subscriber<EventDetailBean>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.showNetError();
            }

            @Override
            public void onNext(EventDetailBean resp) {
                mView.getEventDetail(resp);
            }
        });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void solveEvent() {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("uuid", mEventId);
        params.put("state", "已解决");
        RetrofitService.resolveEvent(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
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
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    mView.solveSuccess();
                } else {
                    mView.solveFail(commonResp.info);
                }
            }
        });
    }

    @Override
    public void reassign() {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("uuid", mEventId);
        RetrofitService.reassignEvent(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在重新指派中...", false);
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
                mView.reassignFail("重新指派失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    mView.reassignSuccess();
                } else {
                    mView.reassignFail(commonResp.info);
                }
            }
        });
    }
}
