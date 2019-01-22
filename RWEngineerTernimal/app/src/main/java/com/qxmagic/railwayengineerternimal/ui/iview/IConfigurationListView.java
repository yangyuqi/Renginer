package com.qxmagic.railwayengineerternimal.ui.iview;

import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项列表回调接口
 */
public interface IConfigurationListView extends IBaseView {
    void getConfigurationList(List<ConfigurationBean> configurationList);
    void getMoreConfigurationList(List<ConfigurationBean> configurationList);
}
