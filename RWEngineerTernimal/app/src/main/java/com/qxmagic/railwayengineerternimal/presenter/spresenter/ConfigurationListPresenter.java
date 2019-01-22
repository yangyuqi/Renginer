package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IConfigurationListView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项列表presenter
 */
public class ConfigurationListPresenter implements IBasePresenter {
    private IConfigurationListView mView;
    private Context mContext;
    private int page = 1;

    public ConfigurationListPresenter(IConfigurationListView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        //请求配置项数量
        page = 1;
        RetrofitService.getConfigurationdList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<CommonResp<ConfigurationBean>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ConfigurationBean>>() {
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
                    public void onNext(CommonResp<ConfigurationBean> resp) {
                        if (resp != null) {
                            mView.getConfigurationList(resp.commonBean.commonList);
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
        RetrofitService.getConfigurationdList(getParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<CommonResp<ConfigurationBean>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ConfigurationBean>>() {
                    @Override
                    public void onCompleted() {
//                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        ProgressUtil.dismissProgressDialog();
                        Logger.e(e.toString());
                        mView.getMoreConfigurationList(null);
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(CommonResp<ConfigurationBean> resp) {
                        if (resp != null) {
                            mView.getMoreConfigurationList(resp.commonBean.commonList);
                        } else {
                            mView.getMoreConfigurationList(null);
                        }
                    }
                });
    }
}
