package com.qxmagic.railwayengineerternimal.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.api.HttpAddressProperties;
import com.qxmagic.railwayengineerternimal.api.download.DownloadIntentService;
import com.qxmagic.railwayengineerternimal.logic.model.VersionItem;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.ISettingPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.SettingPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.ISettingView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.FileHelper;
import com.qxmagic.railwayengineerternimal.widget.CommonDialog;
import com.suke.widget.SwitchButton;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity<ISettingPresenter> implements ISettingView {
    @BindView(R.id.setting_msg_push_switch)
    SwitchButton mSwitchBtn;
    @BindView(R.id.cache_size)
    TextView mCacheSize;
    @BindView(R.id.setting_current_version)
    TextView mCurrentVersion;
    private CommonDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        _bindListener();
        updateViews();
    }

    private void _bindListener() {
        mSwitchBtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                showSnackBar(view, isChecked ? "接受消息推送" : "不接收推送消息", "", null);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this,getResources().getString(R.string.mine_setting),0,false,true);
//        initActionBar(getResources().getString(R.string.mine_setting));
    }

    @Override
    protected void updateViews() {
        mPresenter.getCurrentVersion();
        mPresenter.getCacheSize();
    }

    @Override
    protected void initInjector() {
        mPresenter = new SettingPresenter(this, mContext);
    }

    @Override
    public void getCacheSize(String cacheSize) {
        mCacheSize.setText(cacheSize);
    }

    @Override
    public void clearCacheSuccess() {
        mDialog.dismiss();
        showToast("清除缓存成功");
        mCacheSize.setText("0 KB");
    }

    @Override
    public void getCurrentVersion(String currentVersion) {
        mCurrentVersion.setText(currentVersion);
    }

    @Override
    public void getNewVersion(final VersionItem versionItem) {
        CommonDialog mUpdateDialog = new CommonDialog(mContext, "更新提示", "是否升级到" + versionItem.versionName + "版本？\n" + versionItem.description,
                "取消", "升级", new CommonDialog.OnButtonClickListener() {
            @Override
            public void onCancelClick() {
                RWETApplication.getInstance().isInstalling = false;
            }

            @Override
            public void onConfirmClick() {
                Intent intent = new Intent(SettingActivity.this, DownloadIntentService.class);
                intent.putExtra("apkUrl", HttpAddressProperties.BASE_URL + versionItem.apkUrl);
                startService(intent);
                RWETApplication.getInstance().isInstalling = true;
            }
        });
        mUpdateDialog.setTextGone(false, false);
        mUpdateDialog.setCanceledOnTouchOutside(true);
        if (!mUpdateDialog.isShowing()) {
            mUpdateDialog.showMiddleDialog();
        }
    }

    @Override
    public void noNewVersion(String checkHint) {
        showToast(checkHint);
    }

    @OnClick({R.id.setting_clear_cache, R.id.setting_check_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_clear_cache:
                if ("0 KB".equals(mCacheSize.getText().toString().trim())) {
                    showToast("暂无缓存");
                } else {
                    // 清除缓存
                    mDialog = new CommonDialog(mContext, "提示", "当前缓存：" +
                            FileHelper.getCacheSize(mContext) + "\n清除缓存，请确定？", "取消", "确定",
                            new CommonDialog.OnButtonClickListener() {
                                @Override
                                public void onConfirmClick() {
                                    mPresenter.clearCache();
                                }

                                @Override
                                public void onCancelClick() {
                                    mDialog.dismiss();
                                }
                            });
                    mDialog.setTextGone(false, false);
                    mDialog.setCanceledOnTouchOutside(true);
//                    mDialog.cancelBackKey();
                    if (!mDialog.isShowing()) {
                        mDialog.showMiddleDialog();
                    }
                }
                break;
            case R.id.setting_check_update:
                if (!RWETApplication.getInstance().isInstalling) {
                    mPresenter.checkUpdate();
                } else {
                    showToast("更新包正在下载中");
                }
                break;
            default:
                break;
        }
    }
}
