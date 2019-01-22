package com.qxmagic.railwayuserterminal.ui.event;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.injector.components.DaggerEventComponent;
import com.qxmagic.railwayuserterminal.injector.modules.EventModule;
import com.qxmagic.railwayuserterminal.logic.global.GlobalConstant;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseFragment;
import com.qxmagic.railwayuserterminal.ui.event.repair.RepairActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IEventVIew;
import com.qxmagic.railwayuserterminal.ui.scancode.CaptureActivity;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.LoginUtil;
import com.qxmagic.railwayuserterminal.widget.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/16 0016.
 * 事件fragment
 */
public class EventFragment extends BaseFragment<IBasePresenter> implements IEventVIew {
    @BindView(R.id.event_time_text)
    TextView mCurrentTime;
    @BindView(R.id.event_user_name_text)
    TextView mUserName;
    @BindView(R.id.event_resolved_count)
    TextView mResolvedEventCount;
    @BindView(R.id.event_unresolved_count)
    TextView mUnresolvedEventCount;

    private CommonDialog callPhoneDialog;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EventFragment.this.onClick(view);
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_event;
    }

    @Override
    protected void initInjector() {
        DaggerEventComponent.builder().applicationComponent(getAppComponent()).eventModule(new EventModule(this)).build().inject(this);
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView,
                mContext.getResources().getString(R.string.event_title),
                R.mipmap.event_scanner_icon,
                R.mipmap.event_callphone_icon,
                listener, listener, true, false);
        if (null != RWUTApplication.getInstance().mLoginUser) {
            mUserName.setText(RWUTApplication.getInstance().mLoginUser.sponsor);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    protected void updateViews() {
    }

    @Override
    public void getResolvedCount(String count) {
        mResolvedEventCount.setText(count);
    }

    @Override
    public void getUnresolvedCount(String count) {
        mUnresolvedEventCount.setText(count);
    }

    @Override
    public void getCurrentTime(String currentTime) {
        mCurrentTime.setText(currentTime);
    }

    @OnClick({R.id.event_resolved_layout, R.id.event_unresolved_layout, R.id.event_user_layout, R.id.event_repair_layout})
    public void onClick(View view) {
        if (RWUTApplication.getInstance().mLoginUser == null) {
            LoginUtil.startLogin(mContext);
            return;
        }
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.event_user_layout:
                //跳转至用户界面
//                showSnackBar(view, "跳转至用户界面", "", null);
//                intent.setClass(mContext, MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.event_resolved_layout:
                //跳转至已解决事件界面
                intent.setClass(mContext, ResolvedActivity.class);
                startActivity(intent);
                break;
            case R.id.event_unresolved_layout:
                //跳转至未解决事件界面
                intent.setClass(mContext, UnresolvedActivity.class);
                startActivity(intent);
                break;
            case R.id.event_repair_layout:
                //跳转至报修界面
                intent.setClass(mContext, RepairActivity.class);
                startActivity(intent);
                break;
            case R.id.common_title_left:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions( new String[]{
                                Manifest.permission.CALL_PHONE
                        }, 10);
                    } else {
                        callPhone();
                    }
                }else{
                    callPhone();
                }
                break;
            case R.id.common_title_right:
                //跳转至扫码界面
                intent.setClass(mContext, CaptureActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (10 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            }else{
                showToast("请开启拨打电话的权限");
            }
        }
    }


    private void callPhone() {
        //呼叫保修电话
        callPhoneDialog = new CommonDialog(mContext, "拨打报修电话",
                "是否要拨打报修热线：+" + GlobalConstant.CUSTOMER_SERVICE, "取消", "确定", new CommonDialog.OnButtonClickListener() {
            @Override
            public void onCancelClick() {
                callPhoneDialog.dismiss();
            }

            @Override
            public void onConfirmClick() {
                callPhoneDialog.dismiss();
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + GlobalConstant.CUSTOMER_SERVICE));
                startActivity(intent2);
            }
        });
        callPhoneDialog.setTextGone(false, false);
        callPhoneDialog.setButtonGone(View.VISIBLE, View.VISIBLE);
        callPhoneDialog.setCanceledOnTouchOutside(false);
        if (!callPhoneDialog.isShowing()) {
            callPhoneDialog.showMiddleDialog();
        }
    }
}
