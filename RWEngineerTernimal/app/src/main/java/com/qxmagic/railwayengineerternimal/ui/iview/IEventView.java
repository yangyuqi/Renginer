package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 未/已解决事件回调
 */
public interface IEventView extends IBaseView {
    /**
     * @param eventBeanList 未解决事件集合
     */
    void getEventList(List<EventDetailBean> eventBeanList);

    void getMoreEventList(List<EventDetailBean> eventList);
}
