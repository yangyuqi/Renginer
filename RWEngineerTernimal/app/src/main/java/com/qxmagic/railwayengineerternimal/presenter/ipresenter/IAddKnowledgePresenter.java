package com.qxmagic.railwayengineerternimal.presenter.ipresenter;

import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

import java.util.HashMap;

/**
 * Created by Christian on 2017/3/29 0029.
 * 添加到知识库presenter接口
 */
public interface IAddKnowledgePresenter extends IBasePresenter {

    /**
     * 添加到知识库的方法
     *
     * @param params 请求参数
     */
    void addToKnowledge(HashMap<String, String> params);

    void getQuestionList();

    void getType();
}
