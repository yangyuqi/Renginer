package com.qxmagic.railwayengineerternimal.injector.modules;

import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.ActivityScope;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IAddKnowledgePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.AddKnowledgePresenter;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.AddKnowledgeActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/29 0029.
 * 添加知识库module
 */
@Module
public class AddKnowledgeModule {
    private final AddKnowledgeActivity mView;
    private final Context mContext;

    public AddKnowledgeModule(AddKnowledgeActivity mView,Context mContext) {
        this.mView = mView;
        this.mContext=mContext;
    }

    @ActivityScope
    @Provides
    public IAddKnowledgePresenter providePresenter() {
        return new AddKnowledgePresenter(mView,mContext);
    }
}
