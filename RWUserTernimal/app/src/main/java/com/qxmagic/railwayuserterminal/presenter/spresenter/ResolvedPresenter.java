package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IUnresolvedView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件presenter
 */

public class ResolvedPresenter implements IResolvedPresenter {
    private final Context mContext;
    private final IUnresolvedView mView;
    private int page=1;

    public ResolvedPresenter(Context mContext, IUnresolvedView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void getData() {
        page=1;
        HashMap<String, String> map = getParams();
        RetrofitService.getResolvedList(map).doOnSubscribe(new Action0() {
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
        RetrofitService.getResolvedList(map).doOnSubscribe(new Action0() {
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

    @Override
    public void deleteEvent(String eventId) {
        //通过事件id删除事件
    }
}
