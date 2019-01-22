package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.ui.iview.IBaseView;

/**
 * Created by Christian on 2017/3/20 0020.
 * 服务合同详情回调接口
 */

public interface IContractDetailView extends IBaseView {
    /**
     * 获得合同详情
     *
     * @param detailBean 合同详情
     */
    void getContractDetail(ContractDetailBean detailBean);
}
