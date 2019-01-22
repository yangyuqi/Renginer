package com.qxmagic.railwayuserterminal.presenter.spresenter;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.presenter.IBasePresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IEventVIew;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import rx.Subscriber;

/**
 * Created by Christian on 2017/3/17 0017.
 * 事件presenter
 */

public class EventPresenter implements IBasePresenter {

    private IEventVIew mView;

    public EventPresenter(IEventVIew mView) {
        this.mView = mView;
    }

    @Override
    public void getData() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd E", Locale.CHINA);
        mView.getCurrentTime(format.format(date));
        RetrofitService.getEventCount(RequestUtil.getCommonParams()).
                compose(mView.<CommonResp<ArrayList<String>>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ArrayList<String>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        mView.showNetError();
                    }

                    @Override
                    public void onNext(CommonResp<ArrayList<String>> commonResp) {
                        if (commonResp != null) {
                            ArrayList<String> counts = commonResp.commonBean;
                            if (!ListUtil.isEmpty(counts)) {
                                if (counts.size() > 0) {
                                    mView.getResolvedCount(counts.get(0));
                                }
                                if (counts.size() > 1) {
                                    mView.getUnresolvedCount(counts.get(1));
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void getMoreData() {

    }
}
