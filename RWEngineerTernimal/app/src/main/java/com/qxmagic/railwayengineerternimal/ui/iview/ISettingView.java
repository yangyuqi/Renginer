package com.qxmagic.railwayengineerternimal.ui.iview;


import com.qxmagic.railwayengineerternimal.logic.model.VersionItem;

/**
 * Created by Christian on 2017/4/12 0012.
 * 设置回调界面
 */

public interface ISettingView extends IBaseView {
    void getCacheSize(String cacheSize);

    void clearCacheSuccess();

    void getCurrentVersion(String currentVersion);

    void getNewVersion(VersionItem versionItem);

    void noNewVersion(String checkHint);
}
