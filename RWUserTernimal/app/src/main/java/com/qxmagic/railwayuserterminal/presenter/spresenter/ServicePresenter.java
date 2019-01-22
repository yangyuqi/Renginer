package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IServicePresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IServiceView;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/17 0017.
 * 服务presenter
 */

public class ServicePresenter implements IServicePresenter {
    private Context mContext;
    private IServiceView mView;
    private int page=1;

    public ServicePresenter(IServiceView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        //请求合同数据并返回
        page=1;
        RetrofitService.getContracts(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<CommonResp<ContractDetailBean>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ContractDetailBean>>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.showNetError();
//                mView.getServiceList(null);
            }

            @Override
            public void onNext(CommonResp<ContractDetailBean> resp) {
                if(resp!=null && null!=resp.commonBean){
                    mView.getServiceList(resp.commonBean.contracts);
                }else{
                    mView.getServiceList(null);
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
        RetrofitService.getContracts(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
            }
        }).compose(mView.<CommonResp<ContractDetailBean>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ContractDetailBean>>() {
                    @Override
                    public void onCompleted() {
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ProgressUtil.dismissProgressDialog();
                        Logger.e(e.toString());
                        mView.getMoreServiceList(null);
                    }

                    @Override
                    public void onNext(CommonResp<ContractDetailBean> resp) {
                        if(resp!=null && null!=resp.commonBean){
                            mView.getMoreServiceList(resp.commonBean.contracts);
                        }else{
                            mView.getMoreServiceList(null);
                        }
                    }
                });
    }

    @Override
    public void getNotiBean() {
        //获得通知对象
        RetrofitService.getOverdueContracts(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext,"获取数据中...",false);
            }
        }).compose(mView.<CommonResp<ContractDetailBean>>bindToLife()).subscribe(new Subscriber<CommonResp<ContractDetailBean>>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.loadNotificationInfo(null);
            }

            @Override
            public void onNext(CommonResp<ContractDetailBean> resp) {
                if(resp!=null){
                    if(null!=resp.commonBean){
                        mView.loadNotificationInfo(resp.commonBean.contracts);
                    }else{
                        mView.loadNotificationInfo(null);
                    }
                }
            }
        });
    }
}
