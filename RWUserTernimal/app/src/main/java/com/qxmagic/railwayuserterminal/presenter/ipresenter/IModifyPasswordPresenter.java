package com.qxmagic.railwayuserterminal.presenter.ipresenter;

import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码Presenter
 */

public interface IModifyPasswordPresenter extends IBasePresenter {
    /**
     * 修改密码
     *
     * @param originalPassword 原始密码
     * @param newPassword      新密码
     */
    void modifyPassword(String originalPassword, String newPassword);
}
