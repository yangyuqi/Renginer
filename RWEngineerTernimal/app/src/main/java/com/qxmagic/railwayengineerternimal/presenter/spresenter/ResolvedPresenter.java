package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonListResp;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IResolvedPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件presenter
 */

public class ResolvedPresenter implements IResolvedPresenter {
    private Context mContext;
    private IEventView mView;
    private int page = 1;

    public ResolvedPresenter(Context mContext, IEventView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void getData() {
        //发送请求获得未解决事件集合并返回
        page = 1;
        HashMap<String, String> map = getParams();
        RetrofitService.getResolvedList(map).doOnSubscribe(new Action0() {
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
                    ArrayList<EventDetailBean> list = resp.commonList;
                    mView.getEventList(list);
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
        }).compose(mView.<CommonListResp<EventDetailBean>>bindToLife()).subscribe(new Subscriber<CommonListResp<EventDetailBean>>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.getMoreEventList(null);
                mView.showNetError();
            }

            @Override
            public void onNext(CommonListResp<EventDetailBean> resp) {
                if (resp != null) {
                    ArrayList<EventDetailBean> list = resp.commonList;
                    mView.getMoreEventList(list);
                } else {
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
