package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.logic.global.GlobalVariable;
import com.qxmagic.railwayuserterminal.logic.model.VersionItem;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.ISettingPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.ISettingView;
import com.qxmagic.railwayuserterminal.ui.mine.SettingActivity;
import com.qxmagic.railwayuserterminal.utils.FileHelper;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Christian on 2017/4/12 0012.
 * 设置presenter
 */

public class SettingPresenter implements ISettingPresenter {
    private ISettingView mView;
    private Context mContext;

    public SettingPresenter(ISettingView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getCurrentVersion() {
        String currentVersion;
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(),
                    0);
            String version = info.versionName;
            currentVersion = "版本：" + version;
        } catch (Exception e) {
            Logger.e("获取当前版本" + e.toString());
            currentVersion = "版本：1.0.0";
        }
        mView.getCurrentVersion(currentVersion);
    }

    @Override
    public void getCacheSize() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                String cacheSize = FileHelper.getAllFileInDirSize(GlobalConstant.FILE_CACHE_DIR);
                String cacheSize = FileHelper.getCacheSize(mContext);
                subscriber.onNext(cacheSize);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).compose(mView.<String>bindToLife()).
                subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mView.getCacheSize(s);
                    }
                });
    }

    @Override
    public void checkUpdate() {
//        mView.noNewVersion("已是最新版本");
        HashMap<String, String> map = new HashMap<>();
        map.put("code", GlobalVariable.versionCode + "");
        /**
         * 1:Android  2:IOS
         */
        map.put("type", "1");
        RetrofitService.checkUpdate(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                mView.showLoading();
            }
        }).compose(mView.<VersionItem>bindToLife()).subscribe(new Subscriber<VersionItem>() {
            @Override
            public void onCompleted() {
                mView.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                mView.hideLoading();
                mView.noNewVersion("网络错误，请稍后重试");
            }

            @Override
            public void onNext(VersionItem commonRespResult) {
                if (null != commonRespResult) {
                    String status = commonRespResult.status;
                    if ("1".equals(status)) {
                        mView.getNewVersion(commonRespResult);
                    } else {
                        mView.noNewVersion("当前版本已经是最新版本");
                    }
                }
            }
        });
    }

    @Override
    public void clearCache() {
        // 删除缓存
        new Thread() {
            @Override
            public void run() {
                try {
                    FileHelper.clearCache(mContext);
                    ((SettingActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.clearCacheSuccess();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }
}
