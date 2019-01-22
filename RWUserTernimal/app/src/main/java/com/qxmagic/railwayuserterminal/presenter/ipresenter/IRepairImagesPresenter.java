package com.qxmagic.railwayuserterminal.presenter.ipresenter;

import com.qxmagic.railwayuserterminal.logic.model.ImageBean;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/23 0023.
 * 报修图片presenter接口
 */
public interface IRepairImagesPresenter extends IBasePresenter {
    /**
     * 上传报修图片
     *
     * @param imagesList 图片对象集合
     */
    void uploadRepairImages(ArrayList<String> imagesList);
}
