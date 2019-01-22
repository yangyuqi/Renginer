package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IEventWordsView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件presenter
 */

public class EventWordsPresenter implements IBasePresenter {
    private IEventWordsView mView;
    private Context mContext;
    private String mRequestId;

    public EventWordsPresenter(IEventWordsView mView, Context mContext, String mRequestId) {
        this.mView = mView;
        this.mContext=mContext;
        this.mRequestId = mRequestId;
    }

    @Override
    public void getData() {
        //通过请求id获得用户留言对象
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("uuid", mRequestId);
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
                        mView.getEventWords(resp.commonBean);
                    } else {
                        mView.getEventWords(null);
                    }
                }

            }
        });
    }

    @Override
    public void getMoreData() {

    }
}
