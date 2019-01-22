package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 事件详情接口
 */

public interface IEventDetailView extends IBaseView {
    void getEventDetail(EventDetailBean detailBean);
}
