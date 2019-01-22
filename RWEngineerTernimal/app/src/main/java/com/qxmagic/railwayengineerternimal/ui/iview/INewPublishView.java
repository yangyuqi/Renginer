package com.qxmagic.railwayengineerternimal.ui.iview;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建发布回调
 */

public interface INewPublishView extends IBaseView {
    void checkTitle(String result);

    void uploadAttachFail(String errMsg);

    void uploadAttachSuccess(String filePath);

    void publishSuccess();

    void publishFail(String errMsg);

    void getApproveList(ArrayList<String> list);

    void getQuestionList(ArrayList<String> list);

    void getChangeList(ArrayList<String> list);

    void getKnowLedgeList(ArrayList<String> list);
}
