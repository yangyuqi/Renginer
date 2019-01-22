package com.qxmagic.railwayengineerternimal.ui.iview;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/29 0029.
 * 添加知识库回调接口
 */
public interface IAddKnowledgeView extends IBaseView {
    void getReturnResult(String result);

    void getRelateQuestionList(ArrayList<String> list);

    void getKnowledgeTypeList(ArrayList<String> list);
}
