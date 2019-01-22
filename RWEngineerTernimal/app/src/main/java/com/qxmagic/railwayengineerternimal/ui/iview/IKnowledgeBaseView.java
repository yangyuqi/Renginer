package com.qxmagic.railwayengineerternimal.ui.iview;

import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 知识库回调接口
 */
public interface IKnowledgeBaseView extends IBaseView {
    void getKnowledgeList(List<KnowledgeBean> knowledgeList);
    void getMoreKnowledgeList(List<KnowledgeBean> knowledgeList);
}
