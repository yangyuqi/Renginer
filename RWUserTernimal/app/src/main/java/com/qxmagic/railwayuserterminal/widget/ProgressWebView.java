package com.qxmagic.railwayuserterminal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.utils.Util;


/**
 * Created by Christian on 2017/3/13. 顶部带进度条的webView
 */

public class ProgressWebView extends RelativeLayout {
    /**
     * 上下文
     */
    private Context context;

    /**
     * webView设置类
     */
    private WebSettings mWebSettings;

    /**
     * webView 改用可监听滑动的webView
     */
//    private ScrollWebView mWebView;
    private WebView mWebView;

    /**
     * 水平进度条
     */
    private ProgressBar progressBar = null;

    /**
     * 水平进度条高度 dip
     */
    public static int DEFAULT_BAR_HEIGHT = 2;

    /**
     * 加载监听
     */
    private OnWebViewLoadListener mListener;

    public void setLoadListener(OnWebViewLoadListener mListener) {
        this.mListener = mListener;
    }

    public ProgressWebView(Context context) {
        super(context);
        this.context = context;
        createView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView(context);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        createView(context);
    }

    /**
     * 初始化 <一句话功能简述> <功能详细描述>
     *
     * @param context [参数说明]O
     * @see [类、类#方法、类#成员]
     */
    @SuppressLint({"SetJavaScriptEnabled", "InflateParams"})
    private void createView(Context context) {

        this.context = context;
        mWebView = new WebView(context);
        this.addView(mWebView,
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

        progressBar = (ProgressBar) LayoutInflater.from(context)
                .inflate(R.layout.webview_progressbar_horizontal, null);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        this.addView(progressBar,
                LayoutParams.MATCH_PARENT,
                Util.dpToPixel(context, DEFAULT_BAR_HEIGHT));
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        // //解决view add webview 导致获取焦点失败，软键盘弹出失败的问题
        // mWebView.requestFocusFromTouch();
    }

    private boolean isLoadUrl = false;
    /**
     * 设置页面跳转等状况
     */
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!isLoadUrl) {
                isLoadUrl = true;
                loadUrl(url);
            }
            super.onPageStarted(view, url, favicon);
        }

        // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
//        {
//            String url=request.getUrl().toString();
//            if (url.startsWith("tel:"))
//            {
//                if (null != context)
//                {
//                    Intent intent = new Intent(Intent.ACTION_VIEW,
//                            Uri.parse(url));
//                    context.startActivity(intent);
//                }
//            }
//            else if ((url.startsWith("http") || url.startsWith("https")))
//            {
//                    isLoadUrl = false;
//                    loadUrl(url);
//            }
//            return true;
//        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:")) {
                if (null != context) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    context.startActivity(intent);
                }
            } else if ((url.startsWith("http") || url.startsWith("https"))) {
                isLoadUrl = false;
                loadUrl(url);
            }
            return true;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed(); // 接受所有网站的证书
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // 加载出错时直接finish
            // -2：网络断开 -10加载出错
            if (errorCode == -10) {
                if (null != context) {
                    Toast.makeText(context, "加载失败...", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            if (null != mListener) {
                mListener.onReceivedError(view,
                        errorCode,
                        description,
                        failingUrl);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.requestFocus();
            super.onPageFinished(view, url);
            if (null != mListener) {
                mListener.onPageFinished(view, url);
            }
            Logger.i("webView" + "load--success");
        }
    };

    /**
     * 设置进度的监听处理
     */
    private WebChromeClient webChromeClient = new WebChromeClient() {
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
            // 载入进度改变而触发
            if (progress == 100) {
                if (null != progressBar) {
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (null != progressBar) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(progress);
                }
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            // if (null != mListener)
            // {
            // mListener.onReceivedTitle(view, title);
            // }
        }
    };

    /**
     * 加载网页
     *
     * @param url [参数说明]
     * @see [类、类#方法、类#成员]
     */
    public void loadUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("javascript")) {
            mWebView.loadUrl(url);
            return;
        }
        Logger.i("---load url----" + url);

        mWebView.loadUrl(url);// 载入网页
    }

    public void goBack() {
        mWebView.goBack();
    }

    /**
     * 下载监听类 <一句话功能简述> <功能详细描述>
     *
     * @author guoac
     * @version [版本号, 2015-1-24]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent,
                                    String contentDisposition, String mimetype, long contentLength) {
            if (null != context && !TextUtils.isEmpty(url)) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        }
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    /**
     * 重新加载 <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void reload() {
        if (null != mWebView) {
            mWebView.reload();
        }
    }

    /**
     * 销毁webview
     */
    public void destroyWebview() {
        if (null != mWebView) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    public WebSettings getWebSettings() {
        return mWebSettings;
    }

    public interface OnWebViewLoadListener {
        void onReceivedError(WebView view, int errorCode, String description,
                             String failingUrl);

        void onPageFinished(WebView view, String url);

        // void onReceivedTitle(WebView view, String title);
    }

    public interface OnWebViewScrollStateChangeListener {
        void pageEnd();

        void pageTop();

        void scrollChange();
    }

}
