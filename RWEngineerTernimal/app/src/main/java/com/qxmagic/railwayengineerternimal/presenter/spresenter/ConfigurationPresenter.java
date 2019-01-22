package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IConfigurationView;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项presenter
 */
public class ConfigurationPresenter implements IBasePresenter {
    private IConfigurationView mView;
    private Context mContext;

    public ConfigurationPresenter(IConfigurationView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        //请求配置项数量
        RetrofitService.getConfigurationCount(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
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
                if (commonResp != null) {
                    mView.getConfigurationCount(commonResp.commonBean);
                }
            }
        });
    }

    @Override
    public void getMoreData() {

    }
}
