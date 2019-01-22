package com.qxmagic.railwayuserterminal.ui.mine;

import android.os.Bundle;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IBaseView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.widget.ProgressWebView;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.webView)
    ProgressWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this,"关于我们",0,false,true);
//        initActionBar("关于我们");
        mWebView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mWebView) {
            mWebView.destroyWebview();
        }
    }
}
