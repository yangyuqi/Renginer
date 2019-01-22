package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonListResp;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IUnResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IUnresolvedView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未解决事件presenter
 */

public class UnresolvedPresenter implements IUnResolvedPresenter {
    private IUnresolvedView mView;
    private final Context mContext;
    private int page = 1;

    public UnresolvedPresenter(IUnresolvedView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        page = 1;
        RetrofitService.getUnResolvedList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<CommonListResp<EventDetailBean>>bindToLife()).subscribe(new Subscriber<CommonListResp<EventDetailBean>>() {
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
            public void onNext(CommonListResp<EventDetailBean> resp) {
                if (resp != null) {
                    mView.getEventList(resp.commonList);
                }
            }
        });
    }

    @NonNull
    private HashMap<String, String> getParams() {
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("page", String.valueOf(page));
        map.put("rows", "10");
        return map;
    }

    @Override
    public void getMoreData() {
        page++;
        RetrofitService.getUnResolvedList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<CommonListResp<EventDetailBean>>bindToLife()).subscribe(new Subscriber<CommonListResp<EventDetailBean>>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                mView.getMoreEventList(null);
                Logger.e(e.toString());
                mView.showNetError();
            }

            @Override
            public void onNext(CommonListResp<EventDetailBean> resp) {
                if (resp != null) {
                    mView.getMoreEventList(resp.commonList);
                } else {
                    mView.getMoreEventList(null);
                }
            }
        });
    }

    @Override
    public void dealEvent(String uuid) {
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("state", "已指派");
        map.put("uuid", uuid);
        RetrofitService.dealCurrentEvent(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在受理中...", false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                mView.dealFail("受理失败，请检查你的您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    mView.dealSuccess();
                } else {
                    mView.dealFail(commonResp.info);
                }
            }
        });
    }
}
