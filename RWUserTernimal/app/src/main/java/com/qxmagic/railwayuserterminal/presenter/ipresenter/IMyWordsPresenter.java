package com.qxmagic.railwayuserterminal.presenter.ipresenter;

import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;

/**
 * Created by Christian on 2017/3/21 0021.
 * 未解决事件留言接口
 */

public interface IMyWordsPresenter extends IBasePresenter {

    /**
     * 对未解决事件进行留言
     *
     * @param eventId  事件id
     * @param title    标题
     * @param content  内容
     * @param evaluate 评级
     */
    void leavingAMsg(/*String eventId, String title,*/ String content, String evaluate);
}
