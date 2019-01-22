package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IUnresolvedView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未解决事件presenter
 */

public class UnresolvedPresenter implements IBasePresenter {
    private final IUnresolvedView mView;
    private final Context mContext;
    private int page=1;

    public UnresolvedPresenter(IUnresolvedView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        page=1;
        HashMap<String, String> map = getParams();
        RetrofitService.getUnResolvedList(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<ArrayList<EventDetailBean>>bindToLife()).
                subscribe(new Subscriber<ArrayList<EventDetailBean>>() {
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
                    public void onNext(ArrayList<EventDetailBean> resp) {
                        if (null != resp) {
                            mView.getEventList(resp);
                        }else{
                            mView.getEventList(null);
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
        HashMap<String, String> map = getParams();
        RetrofitService.getUnResolvedList(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<ArrayList<EventDetailBean>>bindToLife()).
                subscribe(new Subscriber<ArrayList<EventDetailBean>>() {
                    @Override
                    public void onCompleted() {
//                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        ProgressUtil.dismissProgressDialog();
                        Logger.e(e.toString());
                        mView.getMoreEventList(null);
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(ArrayList<EventDetailBean> resp) {
                        if (null != resp) {
                            mView.getMoreEventList(resp);
                        }else{
                            mView.getMoreEventList(null);
                        }
                    }
                });
    }
}
