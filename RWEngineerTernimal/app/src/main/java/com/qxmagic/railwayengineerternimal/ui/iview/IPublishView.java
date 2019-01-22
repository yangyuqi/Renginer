package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 发布列表回调
 */
public interface IPublishView extends IBaseView {
    /**
     * @param publishBeanList 发布集合
     */
    void getPublishList(List<PublishDetailBean> publishBeanList);

    void getMorePublishList(List<PublishDetailBean> publishBeanList);
}
