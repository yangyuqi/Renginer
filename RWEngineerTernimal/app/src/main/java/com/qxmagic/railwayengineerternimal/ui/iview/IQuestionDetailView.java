package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 问题详情接口
 */

public interface IQuestionDetailView extends IBaseView {
    void getQuestionDetail(QuestionDetailBean detailBean);
}
