package com.qxmagic.railwayuserterminal.ui.iview;

import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件我的留言界面
 */
public interface IEventWordsView extends IBaseView {
    void getEventWords(EventDetailBean wordsBean);
}
