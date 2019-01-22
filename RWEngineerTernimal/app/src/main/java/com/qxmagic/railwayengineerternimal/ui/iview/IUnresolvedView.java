package com.qxmagic.railwayengineerternimal.ui.iview;

/**
 * Created by Christian on 2017/9/12 0012.
 * 未解决事件回调
 */

public interface IUnresolvedView extends IEventView {
    void dealSuccess();

    void dealFail(String errMsg);
}
