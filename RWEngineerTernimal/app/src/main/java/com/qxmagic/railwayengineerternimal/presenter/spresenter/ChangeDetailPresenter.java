package com.qxmagic.railwayengineerternimal.presenter.spresenter;


import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.IChangeDetailView;

/**
 * Created by Christian on 2017/3/21 0021.
 * 获得变更详情presenter
 */

public class ChangeDetailPresenter implements IBasePresenter {
    private IChangeDetailView mView;
    private String mEventId;

    public ChangeDetailPresenter(IChangeDetailView mView, String mEventId) {
        this.mView = mView;
        this.mEventId = mEventId;
    }

    @Override
    public void getData() {
        //通过事件id请求事件详情并返回
        ChangeDetailBean bean = new ChangeDetailBean();
        bean.changeNumber = "p003031";
        bean.changeTitle = "更换服务器电源";
        bean.organization = "南京中铁信息工程有限公司";
        bean.changeState = "关闭";
        bean.priority = "危急";
        bean.description = "巡检过程中发现一台服务器出现故障";
        bean.changeSource = "保密";
        bean.changeType = "产品老化";
        bean.images = new String[]{"http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg",
                "http://static.ailiuzhou.cn/ailiuzhou/headportrait/2016-11/fdeed8a0-ad39-4c87-a7d1-aa754794f5da.jpg"};

        bean.changeReason = "保密";
        bean.effect = "暂无";
        bean.machineHalt = "需停机";
        bean.rejectPlan = "无影响";
        bean.parentChange = "未定义";

        bean.startTime = "2016-05-24 16:56:00";
        bean.lastChangeTime = "2016-05-24 16:56:00";
        bean.appointTime = "2016-05-24 16:56:00";
        bean.endTime = "2016-05-24 16:56:00";

        bean.dealchangeTeam = "南京油运维保服务团队";
        bean.dealMan = "蔡文";

        mView.getChangeDetail(bean);
    }

    @Override
    public void getMoreData() {

    }
}
