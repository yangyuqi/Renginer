package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventWordsView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

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
        //通过事件id请求事件详情并返回
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("uuid", mRequestId);
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
                if(resp!=null){
                    mView.getEventWords(resp);
                }
            }
        });
    }

    @Override
    public void getMoreData() {

    }
}
