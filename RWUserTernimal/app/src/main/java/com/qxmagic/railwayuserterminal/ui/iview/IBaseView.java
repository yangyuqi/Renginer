package com.qxmagic.railwayuserterminal.ui.iview;


import com.trello.rxlifecycle.LifecycleTransformer;

/**
 * Created by Christian on 2017/3/16.
 * BaseView 公共接口
 */

public interface IBaseView {
    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载动画
     */
    void hideLoading();

    /**
     * 网络错误时，显示加载错误
     * TODO 暂且不定义 具体做法和现实界面 仅提供回掉
     *
     */
    void showNetError();

    /**
     * 绑定生命周期
     *
     * @param <T> Activity or Fragment or VIew
     * @return 返回自身
     */
    <T> LifecycleTransformer<T> bindToLife();
}
