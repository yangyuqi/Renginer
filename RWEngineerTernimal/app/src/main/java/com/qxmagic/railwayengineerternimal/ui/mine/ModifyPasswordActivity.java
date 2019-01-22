package com.qxmagic.railwayengineerternimal.ui.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerModifyPasswordComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ModifyPasswordModule;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IModifyPasswordPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IModifyPasswordView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 修改密码
 */
public class ModifyPasswordActivity extends BaseActivity<IModifyPasswordPresenter> implements IModifyPasswordView {
    @BindView(R.id.modify_original_password_edit)
    EditText mOriginalPassword;
    @BindView(R.id.modify_newpassword_edit)
    EditText mNewPassword;
    @BindView(R.id.modify_confirm_newpassword_edit)
    EditText mConfirmPassword;
    @BindView(R.id.modify_confirm_btn)
    Button mConfirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, getResources().getString(R.string.mine_modify_password), 0, false, true);
//        initActionBar(getResources().getString(R.string.mine_modify_password));
        hideSoftInputFromWindow(mOriginalPassword);
        hideSoftInputFromWindow(mNewPassword);
        hideSoftInputFromWindow(mConfirmPassword);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {
        DaggerModifyPasswordComponent.builder().
                applicationComponent(getAppComponent()).
                modifyPasswordModule(new ModifyPasswordModule(this, mContext)).
                build().inject(this
        );
    }

    @OnClick(R.id.modify_confirm_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.modify_confirm_btn:
                String oldPws = mOriginalPassword.getText().toString().trim();
                String newPwd = mNewPassword.getText().toString().trim();
                String confirmPwd = mConfirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(oldPws)) {
                    showToast("请输入原始密码");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    showToast("请输入新密码");
                    return;
                }
                if (TextUtils.isEmpty(confirmPwd)) {
                    showToast("请输入确认密码");
                    return;
                }
                if (!confirmPwd.equals(newPwd)) {
                    showToast("确认密码与新密码不一致，请重新输入");
                    return;
                }
                if (newPwd.length() > 12 || newPwd.length() < 6) {
                    showToast("密码长度需为6-12");
                    return;
                }
                mPresenter.modifyPassword(oldPws, newPwd);
                break;
            default:
                break;
        }
    }


    @Override
    public void modifySuccess() {
        showToast("修改密码成功");
        finish();
    }

    @Override
    public void modifyFail(String errMsg) {
        showToast(errMsg);
    }
}
