package com.qxmagic.railwayuserterminal.ui.login;

import android.os.Bundle;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

public class ForgetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "忘记密码", R.mipmap.common_back_icon, false, true);

    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }
}
