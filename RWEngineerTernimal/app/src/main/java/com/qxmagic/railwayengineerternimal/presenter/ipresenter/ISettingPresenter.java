package com.qxmagic.railwayengineerternimal.presenter.ipresenter;

import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/4/12 0012.
 * 设置presenter接口
 */
public interface ISettingPresenter extends IBasePresenter {
    void getCurrentVersion();

    void getCacheSize();

    void checkUpdate();

    void clearCache();

}
