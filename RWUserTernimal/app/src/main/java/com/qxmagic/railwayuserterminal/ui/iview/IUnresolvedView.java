package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 事件回调
 */
public interface IUnresolvedView extends IBaseView {
    /**
     * @param eventList 事件集合
     */
    void getEventList(List<EventDetailBean> eventList);

    void getMoreEventList(List<EventDetailBean> eventList);
}
