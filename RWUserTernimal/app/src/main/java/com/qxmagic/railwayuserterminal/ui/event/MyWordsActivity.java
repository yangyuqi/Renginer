package com.qxmagic.railwayuserterminal.ui.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.injector.components.DaggerMyWordsComponent;
import com.qxmagic.railwayuserterminal.injector.modules.MyWordsModule;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IMyWordsPresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IMyWordsView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件留言界面
 */

public class MyWordsActivity extends BaseActivity<IMyWordsPresenter> implements IMyWordsView {
    //    @BindView(R.id.msg_title)
//    EditText mTitle;
    @BindView(R.id.msg_content)
    EditText mContent;
    @BindView(R.id.msg_ratingBar)
    RatingBar mRatingBar;

        private String eventId;
//    private EventDetailBean detailBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventId = getIntent().getStringExtra("eventId");
//        Bundle bundle = getIntent().getExtras();
//        if (null != bundle) {
//            detailBean = (EventDetailBean) bundle.getSerializable("detailBean");
//            if (null == detailBean) {
//                detailBean = new EventDetailBean();
//            }
//        }
        initInjector();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_leavingamsg;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "我的留言", R.mipmap.common_back_icon, 0, false, true, null, null);
//        hideSoftInputFromWindow(mTitle);
        hideSoftInputFromWindow(mContent);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {
        DaggerMyWordsComponent.builder().applicationComponent(getAppComponent()).myWordsModule(new MyWordsModule(this, mContext, eventId)).build().inject(this);
    }

    @Override
    public void leavingamsgResult(String result) {
        //是否留言成功
        if ("ok".equals(result)) {
            showToast("留言成功");
            Intent intent = new Intent();
            intent.putExtra("rating", mRatingBar.getRating());
            intent.putExtra("content", mContent.getText().toString().trim());
            setResult(RESULT_OK, intent);
            finish();
        } else if ("-1".equals(result)) {
            showToast("评论失败，请检查您的网络");
        } else {
            showToast("评论失败");
        }
    }

    @OnClick(R.id.confirm_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
//                String title = mTitle.getText().toString().trim();
                String content = mContent.getText().toString().trim();
                String evaluate = String.valueOf(mRatingBar.getRating());
                mPresenter.leavingAMsg(content, evaluate);
                break;
            default:
                break;
        }

    }
}
