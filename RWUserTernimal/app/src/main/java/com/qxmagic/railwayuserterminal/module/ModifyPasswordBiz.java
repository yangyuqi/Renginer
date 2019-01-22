package com.qxmagic.railwayuserterminal.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.module.IBiz.IModifyPasswordBiz;
import com.qxmagic.railwayuserterminal.module.listener.ModifyPasswordListener;
import com.qxmagic.railwayuserterminal.ui.iview.IModifyPasswordView;
import com.qxmagic.railwayuserterminal.utils.LoginUtil;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.util.HashMap;

import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码业务处理类
 */
public class ModifyPasswordBiz implements IModifyPasswordBiz {
    private Context mContext;
    private IModifyPasswordView mView;

    public ModifyPasswordBiz(Context mContext, IModifyPasswordView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void modifyPassword(String originalPassword, final String newPassword, @NonNull final ModifyPasswordListener listener) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("pwd", originalPassword);
        params.put("pwd1", newPassword);
        RetrofitService.modifyPassword(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
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
                listener.modifyFail("修改失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    LoginUtil.updateUserPassword(mContext, newPassword);
                    listener.modifySuccess();
                } else {
                    listener.modifyFail("修改失败");
                }
            }
        });
    }

}
