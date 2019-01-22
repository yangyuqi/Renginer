package com.qxmagic.railwayuserterminal.ui.iview;

/**
 * Created by Christian on 2017/7/24 0024.
 * 登录界面回调
 */

public interface ILoginView extends IBaseView {
    void loginFail(String errMsg);

    void loginSuccess();
}
