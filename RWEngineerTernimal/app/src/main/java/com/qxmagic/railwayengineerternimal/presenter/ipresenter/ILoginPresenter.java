package com.qxmagic.railwayengineerternimal.presenter.ipresenter;

import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/5/23 0023.
 * 登录presenter接口
 */

public interface ILoginPresenter extends IBasePresenter{
    void    login(String phone, String password,boolean remember);

//    void visitorLogin(HashMap<String, String> params);

//    void getVerCode(String phone);

    /**
     * 自动登录
     */
//    void autoLogin();
}
