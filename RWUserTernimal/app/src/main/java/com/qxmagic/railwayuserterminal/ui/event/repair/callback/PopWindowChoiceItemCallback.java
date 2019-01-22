package com.qxmagic.railwayuserterminal.ui.event.repair.callback;

/**
 * Created by Christian on 2017/3/22 0022.
 * popwindow下拉弹窗回掉
 */

public interface PopWindowChoiceItemCallback {
    /**
     * @param position 列表所在位置
     * @param content  当前内容
     */
    void getChoiceItem(int position, String content);
}
