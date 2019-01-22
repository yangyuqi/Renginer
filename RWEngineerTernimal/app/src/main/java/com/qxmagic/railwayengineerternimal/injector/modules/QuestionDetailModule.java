package com.qxmagic.railwayengineerternimal.injector.modules;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.EventDetailPresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.QuestionDetailPresenter;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.QuestionDetailActivity;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved.UnresolvedEventDetailFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题详情module
 */
@Module
public class QuestionDetailModule {
    private final QuestionDetailActivity mView;
    private final String mEventId;

    public QuestionDetailModule(QuestionDetailActivity mView, String mEventId) {
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @ActivityScope
    @Provides
    public IBasePresenter providePresenter() {
        return new QuestionDetailPresenter(mView, mEventId);
    }
}
