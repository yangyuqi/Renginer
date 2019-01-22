package com.qxmagic.railwayuserterminal.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.logic.model.MineBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IMinePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseFragment;
import com.qxmagic.railwayuserterminal.ui.MainActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IMineView;
import com.qxmagic.railwayuserterminal.ui.login.LoginActivity;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ImageLoader;
import com.qxmagic.railwayuserterminal.utils.LoginUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/17 0017.
 * 我的界面
 */

public class MineFragment extends BaseFragment<IMinePresenter> implements IMineView {
    @BindView(R.id.mine_username)
    TextView mUsername;
    @BindView(R.id.mine_password)
    TextView mPassword;
    @BindView(R.id.mine_portrait)
    ImageView mPortrait;
    @BindView(R.id.mine_logout_text)
    TextView mLogout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initInjector() {
//        DaggerMineComponent.builder().applicationComponent(getAppComponent()).mineModule(new MineModule(this)).build().infect(this);
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView, "我的", 0, R.mipmap.common_back_icon, null, null, true, true);
        if (RWUTApplication.getInstance().mLoginUser != null) {
            mUsername.setText(RWUTApplication.getInstance().mLoginUser.orgName);
            mPassword.setText(RWUTApplication.getInstance().mLoginUser.sponsor);
        }
    }

    @Override
    protected void updateViews() {
//        mPresenter.login("Christian", "123456");
    }

    @Override
    public void userLogin(MineBean bean) {
        if (null != bean) {
            mUsername.setText(bean.username);
            mPassword.setText(bean.password);
            ImageLoader.loadCenterCrop(mContext, bean.portraitUrl, mPortrait, R.mipmap.mine_portrait_icon);
        }
    }

    @Override
    public void userLogout() {
        showSnackBar(mLogout, "点击了注销,更改页面布局", "", null);
        mLogout.setText("click me...");
    }

    @OnClick({R.id.mine_logout_text, R.id.mine_modify_password, R.id.mine_about_us, R.id.mine_setting})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.mine_logout_text:
                LoginUtil.clearLoginInfo(mContext);
                intent.setClass(mContext, LoginActivity.class);
                startActivity(intent);
                ((MainActivity)mContext).finish();
                break;
            case R.id.mine_modify_password:
                intent.setClass(mContext, ModifyPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_about_us:
                intent.setClass(mContext, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                intent.setClass(mContext, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
