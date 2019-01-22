package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 新建列表回调
 */
public interface IChangeView extends IBaseView {
    /**
     * @param changeBeanList 新建集合
     */
    void getChangeList(List<ChangeDetailBean> changeBeanList);


    void getMoreChangeList(List<ChangeDetailBean> changeBeanList);
}
