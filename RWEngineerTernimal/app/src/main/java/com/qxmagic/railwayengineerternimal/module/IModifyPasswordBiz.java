package com.qxmagic.railwayengineerternimal.module;

/**
 * Created by Christian on 2017/3/20 0020.
 * 修改密码业务接口
 */
public interface IModifyPasswordBiz {
    /**
     * 修改密码
     *
     * @param originalPassword 原始密码
     * @param newPassword      新密码
     */
    void modifyPassword(String originalPassword, String newPassword, ModifyPasswordListener listener);
}
