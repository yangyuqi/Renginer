package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerEventWordsComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.EventWordsModule;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventWordsView;

import butterknife.BindView;

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

    private String mRequestId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            mRequestId = arg.getString("eventId");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_resolved_words;
    }

    @Override
    protected void initInjector() {
        DaggerEventWordsComponent.builder().eventWordsModule(new EventWordsModule(this, mContext, mRequestId)).build().inject(this);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    public void getEventWords(EventDetailBean wordsBean) {
        if (null != wordsBean) {
//            mWordsTitle.setText(wordsBean.wordsTitle);
            if (TextUtils.isEmpty(wordsBean.nature)) {
                showToast("暂无评论留言");
            }
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

}
