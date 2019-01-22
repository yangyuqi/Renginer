package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 变更详情接口
 */

public interface IChangeDetailView extends IBaseView {
    void getChangeDetail(ChangeDetailBean detailBean);
}
