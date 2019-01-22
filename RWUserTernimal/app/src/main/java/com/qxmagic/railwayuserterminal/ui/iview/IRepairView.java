package com.qxmagic.railwayuserterminal.ui.iview;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/22 0022.
 * 报修回调接口
 */
public interface IRepairView extends IBaseView {
    /**
     * @param result 报修结果 -2:获取指派人失败 -1:网络错误 0：标题存在 1：标题不存在 2：报修失败 3：报修成功
     */
    void repairResult(String result);

    void uploadAttachFail(String errMsg);

    void uploadAttachSuccess(String filePath);

    void getServiceList(ArrayList<String> serviceList);

    void getSubServiceList(ArrayList<String> subServiceList);

    void getpRequestList(ArrayList<String> pRequestList);

    void getpEventList(ArrayList<String> pEventList);

    void getpQuestiontList(ArrayList<String> pQuestionList);

    void getpChangeList(ArrayList<String> pChangeList);

    void getTeamList(ArrayList<String> teamList);

    void getDealManList(ArrayList<String> dealManList);
}
