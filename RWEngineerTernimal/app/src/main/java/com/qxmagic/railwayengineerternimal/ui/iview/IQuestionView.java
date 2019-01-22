package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 问题列表回调
 */
public interface IQuestionView extends IBaseView {
    /**
     * @param questionBeanList 问题集合集合
     */
    void getQuestionList(List<QuestionDetailBean> questionBeanList);
    void getMoreQuestionList(List<QuestionDetailBean> questionBeanList);
}
