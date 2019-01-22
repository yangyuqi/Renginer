package com.qxmagic.railwayengineerternimal.ui.login;

import android.os.Bundle;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;


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
