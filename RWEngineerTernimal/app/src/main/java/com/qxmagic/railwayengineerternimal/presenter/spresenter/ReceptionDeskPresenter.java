package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IReceptionDeskView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/24 0024.
 * 服务台首页presenter
 */
public class ReceptionDeskPresenter implements IBasePresenter {
    private IReceptionDeskView mView;
    private Context mContext;

    public ReceptionDeskPresenter(IReceptionDeskView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        //请求服务台所显示详情并返回
        RetrofitService.getAllEventCount(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在获取数据中...",false);
            }
        }).compose(mView.<CommonResp<String>>bindToLife()).subscribe(new Subscriber<CommonResp<String>>() {
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
            public void onNext(CommonResp<String> commonResp) {
                if(commonResp!=null){
                    mView.getEventCount(commonResp.commonBean);
                }
            }
        });
        RetrofitService.getQuestionCount(RequestUtil.getCommonParams()).
                compose(mView.<CommonResp<String>>bindToLife()).subscribe(new Subscriber<CommonResp<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(CommonResp<String> commonResp) {
                if(commonResp!=null){
                    mView.getQuestionCount(commonResp.commonBean);
                }
            }
        });
    }

    @Override
    public void getMoreData() {

    }
}
