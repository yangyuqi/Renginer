package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IEventDetailView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 获得事件详情presenter
 */

public class EventDetailPresenter implements IBasePresenter {
    private IEventDetailView mView;
    private Context mContext;
    private String mEventId;

    public EventDetailPresenter(IEventDetailView mView, Context mContext, String mEventId) {
        this.mView = mView;
        this.mEventId = mEventId;
        this.mContext = mContext;
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
        }).compose(mView.<CommonResp<EventDetailBean>>bindToLife()).subscribe(new Subscriber<CommonResp<EventDetailBean>>() {
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
            public void onNext(CommonResp<EventDetailBean> resp) {
                if (resp != null) {
                    String status = resp.status;
                    if ("1".equals(status)) {
                        mView.getEventDetail(resp.commonBean);
                    } else {
                        mView.getEventDetail(null);
                    }
                }

            }
        });
    }

    @Override
    public void getMoreData() {

    }
}
