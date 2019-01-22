package com.qxmagic.railwayengineerternimal.ui.iview;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码回调接口
 */
public interface IModifyPasswordView extends IBaseView {
    void modifySuccess();

    void modifyFail(String errMsg);
}
