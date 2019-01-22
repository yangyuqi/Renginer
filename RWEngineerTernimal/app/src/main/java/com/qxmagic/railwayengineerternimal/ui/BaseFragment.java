package com.qxmagic.railwayengineerternimal.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.ApplicationComponent;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by xjf on 2017/3/16.
 * fragment 基类
 */

public abstract class BaseFragment<T extends IBasePresenter> extends RxFragment implements IBaseView {

    protected Context mContext;

    @Inject
    protected T mPresenter;


    // 缓存Fragment view
    protected View mRootView;

    private boolean mIsMulti = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutResId(), null);
            ButterKnife.bind(this, mRootView);
            initInjector();
            initViews();
//            initSwipeRefresh();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && isVisible() && mRootView != null && !mIsMulti) {
            mIsMulti = true;
            updateViews();
        } else {
            super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showNetError() {
        showToast("请求失败，请检查您的网络");
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    protected abstract int getLayoutResId();

    /**
     * Dagger 注入
     */
    protected abstract void initInjector();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 更新视图控件
     */
    protected abstract void updateViews();

//    protected abstract void dealArguments(Bundle arg);

    /**
     * <一句话功能简述>
     * 显示软件盘
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected void showSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 关闭软键盘
     *
     * @param editText 当前输入框
     */
    protected void hideSoftInputFromWindow(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    /**
     * 显示snack bar信息
     *
     * @param view     显示的view
     * @param msg      显示的内容
     * @param action   响应动作
     * @param listener 响应监听 可为null
     */
    protected void showSnackBar(View view, String msg, String action,
                                View.OnClickListener listener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction(action, listener)
                .show();
    }

    /**
     * 显示toast弹窗信息
     *
     * @param msg 显示的内容
     */
    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取 ApplicationComponent
     *
     * @return ApplicationComponent
     */
    protected ApplicationComponent getAppComponent() {
        return RWETApplication.getAppComponent();
    }
}
