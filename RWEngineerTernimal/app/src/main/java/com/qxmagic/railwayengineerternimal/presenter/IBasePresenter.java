package com.qxmagic.railwayengineerternimal.presenter;

/**
 * Created by Christian on 2017/3/16 0016.
 * presenter基类 定义两个可能需要用到的方法
 * 同时也为了在使用BaseActivity时可定义泛型
 */

public interface IBasePresenter {
    /**
     * 获取网络数据，更新界面
     */
    void getData();

    /**
     * 加载更多数据
     */
    void getMoreData();
}
