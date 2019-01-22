package com.qxmagic.railwayengineerternimal.presenter.ipresenter;

import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

import java.util.HashMap;

/**
 * Created by Christian on 2017/3/29 0029.
 * 新建问题presenter接口
 */
public interface INewVariousPresenter extends IBasePresenter {

    /**
     * 新建问题的方法
     *
     * @param params 请求参数
     */
    void newVarious(HashMap<String, String> params);

    void uploadAttachment(String filePath);

    void checkTitle(String title);

    void getOgList();

    void getDataByOrgName(String orgName);

    void getDealManByTeam(String team);
}
