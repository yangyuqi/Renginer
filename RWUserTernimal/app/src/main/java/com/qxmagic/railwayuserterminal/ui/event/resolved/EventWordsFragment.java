package com.qxmagic.railwayuserterminal.ui.event.resolved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.injector.components.DaggerEventWordsComponent;
import com.qxmagic.railwayuserterminal.injector.modules.EventWordsModule;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.BaseFragment;
import com.qxmagic.railwayuserterminal.ui.event.MyWordsActivity;
import com.qxmagic.railwayuserterminal.ui.iview.IEventWordsView;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件留言fragment
 */
public class EventWordsFragment extends BaseFragment<IBasePresenter> implements IEventWordsView {
    //    @BindView(R.id.words_title)
//    TextView mWordsTitle;
    @BindView(R.id.words_content)
    TextView mWordsContent;
    @BindView(R.id.words_ratingBar)
    RatingBar mRatingBar;

    @BindView(R.id.event_leave_msg_layout)
    View mLeaveMsgLayout;

    private EventDetailBean detailBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            detailBean = (EventDetailBean) arg.getSerializable("detailBean");
        } else {
            detailBean = new EventDetailBean();
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_resolved_words;
    }

    @Override
    protected void initInjector() {
        DaggerEventWordsComponent.builder().eventWordsModule(new EventWordsModule(this, mContext, detailBean.id)).build().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
//        getEventWords(detailBean);
    }

    @Override
    public void getEventWords(EventDetailBean wordsBean) {
        if (null == wordsBean) {
            return;
        }
//        mWordsTitle.setText(wordsBean.wordsTitle);
        if (TextUtils.isEmpty(wordsBean.nature)) {
            showToast("您尚未对此次服务进行评价留言");
//            mWordsContent.setText(wordsBean.nature);
            mLeaveMsgLayout.setVisibility(View.VISIBLE);
        } else {
            mLeaveMsgLayout.setVisibility(View.GONE);
            mWordsContent.setText(wordsBean.nature);
            float score;
            try {
                score = Float.parseFloat(wordsBean.effect);
            } catch (Exception e) {
                e.printStackTrace();
                score = 0f;
            }
            mRatingBar.setRating(score);
        }
    }

    @OnClick(R.id.confirm_btn)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_btn:
                //跳转至留言界面
                Intent intent = new Intent(mContext, MyWordsActivity.class);
                intent.putExtra("eventId", detailBean.id);
                startActivityForResult(intent, 1000);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 1000 == requestCode) {
            if (data != null) {
                mWordsContent.setText(data.getStringExtra("content"));
                mRatingBar.setRating(data.getFloatExtra("rating", 0F));
                mLeaveMsgLayout.setVisibility(View.GONE);
            }
        }
    }
}
