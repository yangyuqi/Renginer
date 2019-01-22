package com.qxmagic.railwayengineerternimal.injector.modules;


import android.content.Context;

import com.qxmagic.railwayengineerternimal.injector.FragmentScope;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.presenter.spresenter.KnowledgeBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.KnowledgeBaseFragment;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.adapter.KnowledgeBaseAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Christian on 2017/3/17 0017.
 * 配置项列表module
 */
@Module
public class KnowledgeBaseModule {
    private final KnowledgeBaseFragment mView;
    private final Context mContext;

    public KnowledgeBaseModule(KnowledgeBaseFragment mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @FragmentScope
    @Provides
    public IBasePresenter provideConfigurationPresenter() {
        return new KnowledgeBasePresenter(mView, mContext);
    }

    @FragmentScope
    @Provides
    public KnowledgeBaseAdapter provideAdapter() {
        return new KnowledgeBaseAdapter(new ArrayList<KnowledgeBean>(), mContext);
    }

}
