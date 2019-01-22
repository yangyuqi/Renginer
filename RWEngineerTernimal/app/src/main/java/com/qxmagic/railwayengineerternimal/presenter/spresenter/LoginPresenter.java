package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.UserResp;
import com.qxmagic.railwayengineerternimal.logic.model.UserBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.ILoginPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.ILoginView;
import com.qxmagic.railwayengineerternimal.utils.LoginUtil;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/7/24 0024.
 * 登录presenter
 */

public class LoginPresenter implements ILoginPresenter {
    private ILoginView mView;
    private Context mContext;

    public LoginPresenter(ILoginView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void login(final String phone, final String password, boolean remember) {
        RetrofitService.login(phone, password).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在登录中...", false);
            }
        }).compose(mView.<UserResp>bindToLife()).subscribe(new Subscriber<UserResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                ProgressUtil.dismissProgressDialog();
                mView.loginFail("登录失败，请检查您的网络");
            }

            @Override
            public void onNext(UserResp userResp) {
                String status = userResp.status;
                if ("1".equals(status)) {
                    UserBean userBean = userResp.userBean;
                    if ("GCS".equals(userBean.role)) {
                        userBean.password = password;
                        userBean.phone = phone;
                        LoginUtil.saveLoginInfo(mContext, userBean);
                        mView.loginSuccess();
                    } else {
                        mView.loginFail("该账号非工程师端账号，请联系管理员");
                    }
                } else {
                    mView.loginFail(userResp.info);
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }
}
