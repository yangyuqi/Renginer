package com.qxmagic.railwayengineerternimal.presenter.ipresenter;


import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的presenter
 */
public interface IMinePresenter extends IBasePresenter {
    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password);

    /**
     * 注销登出
     */
    void logout();
}
