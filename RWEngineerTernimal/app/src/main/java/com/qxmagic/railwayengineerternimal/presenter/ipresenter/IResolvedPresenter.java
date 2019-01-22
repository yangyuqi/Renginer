package com.qxmagic.railwayengineerternimal.presenter.ipresenter;


import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件presenter接口
 */

public interface IResolvedPresenter extends IBasePresenter {
    /**
     * 删除事件
     * @param eventId 事件id
     */
    void deleteEvent(String eventId);
}
