package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.module.ModifyPasswordBiz;
import com.qxmagic.railwayengineerternimal.module.ModifyPasswordListener;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IModifyPasswordPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IModifyPasswordView;


/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码presenter
 */
public class ModifyPasswordPresenter implements IModifyPasswordPresenter {
    private final IModifyPasswordView mView;
    private ModifyPasswordBiz biz;

    public ModifyPasswordPresenter(IModifyPasswordView mView, Context context) {
        this.mView = mView;
        biz = new ModifyPasswordBiz(context,mView);
    }

    @Override
    public void getData() {
    }

    @Override
    public void getMoreData() {
    }

    @Override
    public void modifyPassword(String originalPassword, String newPassword) {
        biz.modifyPassword(originalPassword, newPassword, new ModifyPasswordListener() {
            @Override
            public void modifySuccess() {
                mView.modifySuccess();
            }

            @Override
            public void modifyFail(String result) {
                mView.modifyFail(result);
            }
        });
    }
}
