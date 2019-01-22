package com.qxmagic.railwayengineerternimal.ui.mine;

import android.os.Bundle;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.widget.ProgressWebView;

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
        CommonTitleUtil.initCommonTitle(this, "关于我们", 0, false, true);
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
