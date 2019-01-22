package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.UserBean;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的界面接口
 */

public interface IMineView extends IBaseView {
    /**
     * 用户登录
     * @param bean 用户信息
     */
    void userLogin(UserBean bean);

    /**
     * 用户退出登录
     */
    void userLogout();
}
