package com.qxmagic.railwayuserterminal.module.listener;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码结果回掉
 */

public interface ModifyPasswordListener {
    void modifySuccess();

    void modifyFail(String result);
}
