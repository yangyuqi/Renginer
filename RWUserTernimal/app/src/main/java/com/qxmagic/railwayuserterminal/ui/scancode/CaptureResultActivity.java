package com.qxmagic.railwayuserterminal.ui.scancode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 扫码结果webView加载类
 *
 * @author houfangfang
 * @version [版本号, 2015-9-22]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CaptureResultActivity extends BaseActivity<IBasePresenter> implements
        OnClickListener {
    /**
     * 内容富文本
     */
    private String contentStr;

    /**
     * 内容webview
     */
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTranslucentStatusBarByRed();

        setContentView(getLayoutResId());

//        setTitlePaddingStatusBar(R.id.zhlz_scan_result_layout);

//        getBundle();

        initViews();
//        bindListener();

//        checkUrl();
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onClick(View view) {

    }

//    /** <一句话功能简述>
//     * <功能详细描述> [参数说明]
//     * 判断是否网址url
//     * @return void [返回类型说明]
//     * @exception throws [违例类型] [违例说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void checkUrl()
//    {
//        if (!StringUtil.isEmpty(contentStr))
//        {
//            if (contentStr.startsWith("http://")
//                    || contentStr.startsWith("https://"))
//            {
//                ProgressUtil.showProgressDialog(mContext,
//                        getString(R.string.loading));
//                mWebView.loadUrl(contentStr);
//            }
//            else
//            {
//                initData();
//            }
//        }
//    }
//
//    /**
//     * 更新UI数据
//     * <一句话功能简述>
//     * <功能详细描述> [参数说明]
//     * @return void [返回类型说明]
//     * @exception throws [违例类型] [违例说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private void initData()
//    {
//        String data = getHeadHtml()
//                + "<body onload=\"window.scrollTo(0,0); \""
//                + "style=\"padding:0;margin:0;\"><div style=\"padding-left:10px;padding-right:10px;\">"
//                + "<div style=\"color:#585858;font-size:18px;padding-left:2px;padding-top:5px;line-height:30px;\">"
//                + contentStr + "</div></div>" + "</body></html>";
//        mWebView.loadDataWithBaseURL("file:///android_asset/",
//                data,
//                "text/html",
//                "UTF-8",
//                "");
//    }
//
//    /** <一句话功能简述>
//     * 头html
//     * <功能详细描述>
//     * @return [参数说明]
//     * @return String [返回类型说明]
//     * @exception throws [违例类型] [违例说明]
//     * @see [类、类#方法、类#成员]
//     */
//    private String getHeadHtml()
//    {
//        StringBuffer bodyHtmlBuffer = new StringBuffer();
//        bodyHtmlBuffer.append("<html>");
//        bodyHtmlBuffer.append("<head>");
//        bodyHtmlBuffer.append("<meta charset=\"utf-8\" />");
//        bodyHtmlBuffer.append("<script>");
//        bodyHtmlBuffer.append("function reply(obj){var position = obj; window.JS.reply(position);}");
//        bodyHtmlBuffer.append("function userInfo(obj){var userid = obj; window.JS.userInfo(userid);}");
//        bodyHtmlBuffer.append("function commentList(){ window.JS.commentList();}");
//        bodyHtmlBuffer.append("</script>");
//        bodyHtmlBuffer.append("</head>");
//        return bodyHtmlBuffer.toString();
//    }
//
//    @SuppressLint("SetJavaScriptEnabled")
//    @Override
//    public void initViews()
//    {
//        CommonTitleUtil.initCommonTitle(this, "扫码结果", 0, false, true);
//
//        mWebView = (WebView) findViewById(R.id.message_detail_webview);
//        //注册webview与js交互的监听,"JS"名字自定义，必须确保js调用的名字与此处一致
//        mWebView.addJavascriptInterface(new WebviewUtil(), "JS");
//        mWebView.getSettings().setRenderPriority(RenderPriority.HIGH);
//        mWebView.clearCache(false);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setSavePassword(false);
//
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
////        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//    }
//
//    @Override
//    protected void updateViews() {
//
//    }
//
//    @Override
//    protected void initInjector() {
//
//    }
//
//    /**
//     * 是否打开网址对话框
//     * @param mContext
//     * @param downClientHandler
//     * @param updateType
//     * @param description
//     * @see [类、类#方法、类#成员]
//     */
//    private void openDialog(final Context mContext, final String message)
//    {
//        String title = "确定打开网址？";
//        //        CommonDialog dialog = new CommonDialog(mContext, title, message,
//        //                new CommonDialog.CancelButton()
//        //                {
//        //                    @Override
//        //                    public void click()
//        //                    {
//        //                    }
//        //                }, new CommonDialog.OkButton()
//        //                {
//        //                    @Override
//        //                    public void click()
//        //                    {
//        //                        intentUrl(message);
//        //                    }
//        //                }, Gravity.LEFT);
//        final CommonDialog dialog = new CommonDialog(mContext, title, message,
//                "取消", "确定", new OnButtonClickListener()
//                {
//                    @Override
//                    public void onConfirmClick()
//                    {
//                        intentUrl(message);
//                    }
//
//                    @Override
//                    public void onCancelClick()
//                    {
//                    }
//                });
//        //点击对话框之外，不关闭对话框
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnKeyListener(GlobalVariable.onKeyListener);
//        dialog.showDialog();
//    }
//
//    private void intentUrl(String url)
//    {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri uri = Uri.parse(url);
//        intent.setData(uri);
//        startActivity(intent);
//    }
//
//    public class WebviewUtil
//    {
//
//        /** <一句话功能简述>
//         * <功能详细描述> [参数说明]
//         * 点击文本跳转
//         * @return void [返回类型说明]
//         * @exception throws [违例类型] [违例说明]
//         * @see [类、类#方法、类#成员]
//         */
//        @JavascriptInterface
//        public void openUrl(String content)
//        {
//            if (!StringUtil.isEmpty(content))
//            {
//                if (content.startsWith("http://")
//                        || content.startsWith("https://"))
//                {
//                    intentUrl(content);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void bindListener()
//    {
//        //取消webview 的长安选中效果
//        mWebView.setOnLongClickListener(new WebView.OnLongClickListener()
//        {
//            @Override
//            public boolean onLongClick(View v)
//            {
//                return true;
//            }
//        });
//        //屏蔽webview禁用 按数字时 打电话功能
//        mWebView.setWebViewClient(new WebViewClient()
//        {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url)
//            {
//                if (url.indexOf("tel:") < 0)
//                {
//                    view.loadUrl(url);
//                }
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url)
//            {
//                super.onPageFinished(view, url);
//                ProgressUtil.dismissProgressDialog(mContext);
//            }
//        });
//    }
//
//    @Override
//    public int getActivityLayout()
//    {
//        return R.layout.zhlz_scan_detail_activity;
//    }
//
//    public void getBundle()
//    {
//        contentStr = getIntent().getStringExtra("content");
//    }
//
//    @Override
//    public void onClick(View v)
//    {
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack())
//        {
//            mWebView.goBack();// 返回前一个页面
//            return true;
//        }
//        if (mWebView != null)
//        {
//            mWebView.destroy();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
