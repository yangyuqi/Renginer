package com.qxmagic.railwayuserterminal.ui.iview;

/**
 * Created by Christian on 2017/3/17 0017.
 * 事件见面接口
 */

public interface IEventVIew extends IBaseView {

    void getResolvedCount(String count);
    void getUnresolvedCount(String count);
    void getCurrentTime(String currentTime);
}
