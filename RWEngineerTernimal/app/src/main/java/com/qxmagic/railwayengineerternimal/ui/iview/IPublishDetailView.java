package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 发布详情接口
 */

public interface IPublishDetailView extends IBaseView {
    void getPublishDetail(PublishDetailBean detailBean);
}
