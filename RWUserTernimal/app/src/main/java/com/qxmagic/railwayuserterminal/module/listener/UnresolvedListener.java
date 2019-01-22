package com.qxmagic.railwayuserterminal.module.listener;

import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/20 0020.
 * 请求未解决事件监听
 */

public interface UnresolvedListener {
    /**
     * 请求结果
     *
     * @param unresolvedList 结果集合
     */
    void requestResult(ArrayList<EventDetailBean> unresolvedList);
}
