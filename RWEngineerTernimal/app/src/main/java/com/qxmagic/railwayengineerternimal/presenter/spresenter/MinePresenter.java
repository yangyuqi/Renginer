package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import com.qxmagic.railwayengineerternimal.logic.model.UserBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IMinePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IMineView;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的presenter
 */

public class MinePresenter implements IMinePresenter {
    private IMineView mView;
    /**
     * 应该是要密码账号进入登录的 暂且这么改 后期再改
     */
    private String mUsername;
    private String mPassword;

    public MinePresenter(IMineView mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void login(String username, String password) {
        //使用用户名密码登陆 登录成功之后 将用户信息返回
        UserBean bean = new UserBean();
        bean.userPortrait = "";
        bean.username = "中铁老大爷";
        bean.password = "admin";
        mView.userLogin(bean);
    }

    @Override
    public void logout() {
        //调用登出接口 登出接口应该是也要账号密码的
        mView.userLogout();
    }
}
