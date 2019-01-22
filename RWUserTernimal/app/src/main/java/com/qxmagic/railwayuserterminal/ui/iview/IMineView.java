package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.MineBean;
import com.qxmagic.railwayuserterminal.ui.iview.IBaseView;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的界面接口
 */

public interface IMineView extends IBaseView {
    /**
     * 用户登录
     * @param bean 用户信息
     */
    void userLogin(MineBean bean);

    /**
     * 用户退出登录
     */
    void userLogout();
}
