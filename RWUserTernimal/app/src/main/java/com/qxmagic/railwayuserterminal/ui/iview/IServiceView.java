package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.logic.model.ServiceBean;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/17 0017.
 * 服务界面合同接口
 */
public interface IServiceView extends IBaseView {
    /**
     * 获得合同列表消息
     *
     * @param mList 合同列表
     */
    void getServiceList(ArrayList<ContractDetailBean> list);

    void getMoreServiceList(ArrayList<ContractDetailBean> list);

    /**
     * 获得通知消息 可能会用
     *
     * @param bean 通知消息
     */
    void loadNotificationInfo(ArrayList<ContractDetailBean> list);
}
