package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.QuestionPresenter;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter.QuestionAdapter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionFragment;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题module
 */
@Module
public class QuestionModule {
    private final Context mContext;
    private final QuestionFragment mView;
    private final String mRequestId;

    public QuestionModule(Context mContext, QuestionFragment mView, String mRequestId) {
        this.mContext = mContext;
        this.mView = mView;
        this.mRequestId = mRequestId;
    }

    @FragmentScope
    @Provides
    public IBasePresenter providePresenter() {
        return new QuestionPresenter(mContext, mView, mRequestId);
    }

    @FragmentScope
    @Provides
    public QuestionAdapter provideAdapter() {
        return new QuestionAdapter(new ArrayList<QuestionDetailBean>(), mContext);
    }
}
