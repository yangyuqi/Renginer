package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IPublishView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布presenter
 */

public class PublishPresenter implements IBasePresenter {
    private final Context mContext;
    private final IPublishView mView;
    private final String mRequestId;
    private int page = 1;

    public PublishPresenter(Context mContext, IPublishView mView, String mRequestId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRequestId = mRequestId;
    }

    @Override
    public void getData() {
        page = 1;
        RetrofitService.getRelatePublishList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<ArrayList<PublishDetailBean>>bindToLife()).subscribe(new Subscriber<ArrayList<PublishDetailBean>>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
//                mView.getPublishList(null);
                mView.showNetError();
            }

            @Override
            public void onNext(ArrayList<PublishDetailBean> resp) {
                if(resp!=null){
                    mView.getPublishList(resp);
                }
            }
        });
    }

    @NonNull
    private HashMap<String, String> getParams() {
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("page", String.valueOf(page));
        map.put("rows", "10");
        map.put("title", mRequestId);
        return map;
    }

    @Override
    public void getMoreData() {
        page++;
        RetrofitService.getRelatePublishList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<ArrayList<PublishDetailBean>>bindToLife()).subscribe(new Subscriber<ArrayList<PublishDetailBean>>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.getMorePublishList(null);
                mView.showNetError();
            }

            @Override
            public void onNext(ArrayList<PublishDetailBean> resp) {
                if(resp!=null){
                    mView.getMorePublishList(resp);
                }
            }
        });
    }

}
