package com.qxmagic.railwayuserterminal.presenter.spresenter;

import com.qxmagic.railwayuserterminal.logic.model.ImageBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairImagesPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IRepairImagesView;

import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/23 0023.
 * 报修图片presenter
 */
public class RepairImagesPresenter implements IRepairImagesPresenter {
    private IRepairImagesView mView;

    public RepairImagesPresenter(IRepairImagesView mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void uploadRepairImages(ArrayList<String> imagesList) {
        //TODO 上传图片还是保存（在报修界面点击了提交时在上传）
        mView.imagesUploadSuccess("上传成功");
    }
}
