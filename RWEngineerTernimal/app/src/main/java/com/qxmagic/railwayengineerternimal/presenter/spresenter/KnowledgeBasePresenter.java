package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IKnowledgeBaseView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项列表presenter
 */
public class KnowledgeBasePresenter implements IBasePresenter {
    private IKnowledgeBaseView mView;
    private Context mContext;
    private int page = 1;

    public KnowledgeBasePresenter(IKnowledgeBaseView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        page = 1;
        RetrofitService.getKnowledgeList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<CommonResp<KnowledgeBean>>bindToLife()).subscribe(new Subscriber<CommonResp<KnowledgeBean>>() {
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
            public void onNext(CommonResp<KnowledgeBean> resp) {
                if (resp != null) {
                    mView.getKnowledgeList(resp.commonBean.commonList);
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
        page ++;
        RetrofitService.getKnowledgeList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<CommonResp<KnowledgeBean>>bindToLife()).subscribe(new Subscriber<CommonResp<KnowledgeBean>>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.getMoreKnowledgeList(null);
                mView.showNetError();
            }

            @Override
            public void onNext(CommonResp<KnowledgeBean> resp) {
                if (resp != null) {
                    mView.getMoreKnowledgeList(resp.commonBean.commonList);
                }else{
                    mView.getMoreKnowledgeList(null);
                }
            }
        });
    }
}
