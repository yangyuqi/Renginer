package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 事件详情接口
 */

public interface IEventDetailView extends IBaseView {
    void getEventDetail(EventDetailBean detailBean);

    void solveSuccess();

    void solveFail(String errMsg);

    void reassignSuccess();

    void reassignFail(String errMsg);
}
