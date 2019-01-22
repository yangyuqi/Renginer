package com.qxmagic.railwayuserterminal.presenter.ipresenter;

import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Christian on 2017/3/22 0022.
 * 报修presenter接口
 */
public interface IRepairPresenter extends IBasePresenter {

    void checkTitle(String title);

    void uploadAttachment(ArrayList<String> fileList);

    /**
     * 进行报修
     *
     * @param params 报修所需参数
     */
    void repair(HashMap<String, String> params);

    void getDataByOrg();

    void getDealManList(String teamName);
}
