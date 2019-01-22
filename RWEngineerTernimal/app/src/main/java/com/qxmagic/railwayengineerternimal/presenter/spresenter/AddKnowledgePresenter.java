package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IAddKnowledgePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IAddKnowledgeView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/29 0029.
 * 添加知识库的presenter
 */
public class AddKnowledgePresenter implements IAddKnowledgePresenter {
    private IAddKnowledgeView mView;
    private Context mContext;

    public AddKnowledgePresenter(IAddKnowledgeView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void addToKnowledge(HashMap<String, String> params) {
        //发送添加请求并返回结果
        RetrofitService.addToBasic(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                ProgressUtil.dismissProgressDialog();
                mView.getReturnResult("添加知识库失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status=commonResp.status;
                if("1".equals(status)){
                    mView.getReturnResult("okay");
                }else{
                    mView.getReturnResult(commonResp.info);
                }
            }
        });
    }


    @Override
    public void getQuestionList() {
        RetrofitService.getKnowledgeQuestionList(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<ArrayList<String>>bindToLife()).subscribe(new Subscriber<ArrayList<String>>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                ProgressUtil.dismissProgressDialog();
                mView.getReturnResult("获取关联问题列表失败，请检查您的网络");
            }

            @Override
            public void onNext(ArrayList<String> strings) {
                mView.getRelateQuestionList(strings);
            }
        });
    }

    @Override
    public void getType() {
        RetrofitService.getKnowledgeTypeList(RequestUtil.getCommonParams()).
                compose(mView.<ArrayList<String>>bindToLife()).
                subscribe(new Subscriber<ArrayList<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                mView.getReturnResult("获取知识类型列表失败，请检查您的网络");
            }

            @Override
            public void onNext(ArrayList<String> strings) {
                mView.getKnowledgeTypeList(strings);
            }
        });
    }
}
