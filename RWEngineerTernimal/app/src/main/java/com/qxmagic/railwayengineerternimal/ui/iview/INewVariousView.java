package com.qxmagic.railwayengineerternimal.ui.iview;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建问题回调
 */

public interface INewVariousView extends IBaseView {
    void checkTitle(String result);

    void uploadAttachFail(String errMsg);

    void uploadAttachSuccess(String filePath);

    void publishSuccess();

    void publishFail(String errMsg);

    void getServiceList(ArrayList<String> list);

    void getSubServiceList(ArrayList<String> list);

    void getQuesTypeList(ArrayList<String> list);

    void getRelateEventList(ArrayList<String> list);

    void getRelateChangeList(ArrayList<String> list);

    void getTeamList(ArrayList<String> list);

    void getDealManList(ArrayList<String> list);
}
