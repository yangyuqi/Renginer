package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IQuestionView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题presenter
 */

public class QuestionPresenter implements IBasePresenter {
    private final Context mContext;
    private final IQuestionView mView;
    /**
     * 如果从 服务台处 跳转进来则请求id为空 从关联问题出跳转则有值
     * 为空时 请求问人问题列表
     */
    private final String mRequestId;
    private int page = 1;

    public QuestionPresenter(Context mContext, IQuestionView mView, String mRequestId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRequestId = mRequestId;
    }

    @Override
    public void getData() {
        //发送请求获得未解决事件集合并返回
        page = 1;
        if (!TextUtils.isEmpty(mRequestId)) {
            RetrofitService.getRelateQuestionList(getParams()).doOnSubscribe(new Action0() {
                @Override
                public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
                }
            }).compose(mView.<ArrayList<QuestionDetailBean>>bindToLife()).subscribe(new Subscriber<ArrayList<QuestionDetailBean>>() {
                @Override
                public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                    Logger.e(e.toString());
//                    mView.getQuestionList(null);
                    mView.showNetError();
                }

                @Override
                public void onNext(ArrayList<QuestionDetailBean> resp) {
                    if (resp != null) {
                        mView.getQuestionList(resp);
                    }
                }
            });
        } else {
            RetrofitService.getQuestionList(getParams()).doOnSubscribe(new Action0() {
                @Override
                public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
                }
            }).compose(mView.<CommonResp<QuestionDetailBean>>bindToLife()).subscribe(new Subscriber<CommonResp<QuestionDetailBean>>() {
                @Override
                public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                    Logger.e(e.toString());
                    mView.getQuestionList(null);
                    mView.showNetError();
                }

                @Override
                public void onNext(CommonResp<QuestionDetailBean> resp) {
                    if (resp != null && resp.commonBean!=null) {
                        mView.getQuestionList(resp.commonBean.commonList);
                    }
                }
            });
        }
    }

    @NonNull
    private HashMap<String, String> getParams() {
        HashMap<String, String> map = RequestUtil.getCommonParams();
        map.put("page", String.valueOf(page));
        map.put("rows", "10");
        if (!TextUtils.isEmpty(mRequestId)) {
            map.put("title", mRequestId);
        }
        return map;
    }

    @Override
    public void getMoreData() {
        //发送请求获得未解决事件集合并返回
        page++;
        if (!TextUtils.isEmpty(mRequestId)) {
            RetrofitService.getRelateQuestionList(getParams()).doOnSubscribe(new Action0() {
                @Override
                public void call() {
                    ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
                }
            }).compose(mView.<ArrayList<QuestionDetailBean>>bindToLife()).subscribe(new Subscriber<ArrayList<QuestionDetailBean>>() {
                @Override
                public void onCompleted() {
                    ProgressUtil.dismissProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    ProgressUtil.dismissProgressDialog();
                    Logger.e(e.toString());
                    mView.getMoreQuestionList(null);
                }

                @Override
                public void onNext(ArrayList<QuestionDetailBean> resp) {
                    if (resp != null) {
                        mView.getMoreQuestionList(resp);
                    }
                }
            });
        } else {
            RetrofitService.getQuestionList(getParams()).doOnSubscribe(new Action0() {
                @Override
                public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
                }
            }).compose(mView.<CommonResp<QuestionDetailBean>>bindToLife()).subscribe(new Subscriber<CommonResp<QuestionDetailBean>>() {
                @Override
                public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                    Logger.e(e.toString());
                    mView.getMoreQuestionList(null);
                }

                @Override
                public void onNext(CommonResp<QuestionDetailBean> resp) {
                    if (resp != null && resp.commonBean!=null) {
                        mView.getMoreQuestionList(resp.commonBean.commonList);
                    }
                }
            });
        }
    }

}
