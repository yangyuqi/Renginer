package com.qxmagic.railwayuserterminal.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.initalize.InitManager;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.ILoginPresenter;
import com.qxmagic.railwayuserterminal.presenter.spresenter.LoginPresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.MainActivity;
import com.qxmagic.railwayuserterminal.ui.iview.ILoginView;
import com.qxmagic.railwayuserterminal.utils.FileHelper;
import com.qxmagic.railwayuserterminal.utils.LoginUtil;
import com.qxmagic.railwayuserterminal.utils.SharedPreferencesUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity<ILoginPresenter> implements ILoginView {
    @BindView(R.id.login_username_edit)
    EditText mUsernameEdit;
    @BindView(R.id.login_password_edit)
    EditText mPasswordEdit;
//    @BindView(R.id.login_remember_password_box)
//    CheckBox mCheckBox;
//    @BindView(R.id.forget_password_btn)
//    TextView mForgetPasswordText;

    private String loginName;

    private String password;

    /**
     * 是否能自动登录
     */
    private boolean canAutoLogin;

    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _requestPermission();
        initInjector();
//        FileHelper.makeDir();
        InitManager.getInstance().initClient(mContext);
        initViews();
        canAutoLogin = LoginUtil.isCanAutoLogin(mContext);
        updateViews();
    }

    /**
     * 请求权限
     */
    private void _requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 10);
            } else {
                FileHelper.makeDir();
            }
        }else {
            FileHelper.makeDir();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (10 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FileHelper.makeDir();
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
//        mForgetPasswordText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        loginName = SharedPreferencesUtil.getSharedPreferences(mContext, SharedPreferencesUtil.UserInfoPreference.TEL_PHONE, "");
        password = SharedPreferencesUtil.getSharedPreferences(mContext, SharedPreferencesUtil.UserInfoPreference.PASSWORD, "");
        mUsernameEdit.setText(loginName);
        if(!TextUtils.isEmpty(loginName)){
            mUsernameEdit.setSelection(loginName.length());
        }
        mPasswordEdit.setText(password);
        hideSoftInputFromWindow(mUsernameEdit);
        hideSoftInputFromWindow(mPasswordEdit);
    }

    @Override
    protected void updateViews() {
        if (canAutoLogin) {
            mPresenter.login(loginName, password, false);
        }
    }

    @Override
    protected void initInjector() {
        mPresenter = new LoginPresenter(this, mContext);
    }

    @OnClick({/*R.id.forget_password_btn,*/ R.id.login_btn})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.forget_password_btn:
//                startActivity(new Intent(mContext, ForgetPasswordActivity.class));
//                break;
            case R.id.login_btn:
                loginName = mUsernameEdit.getText().toString().trim();
                password = mPasswordEdit.getText().toString().trim();
//                isRemember = mCheckBox.isChecked();
                if (TextUtils.isEmpty(loginName)) {
                    showToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                mPresenter.login(loginName, password, false);
            default:
                break;
        }
    }

    @Override
    public void loginFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
//            System.exit(0);
        }
    }
}
