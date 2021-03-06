package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.api.HttpAddressProperties;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerResolvedDetailComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ResolvedDetailModule;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventDetailView;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.Util;
import com.qxmagic.railwayengineerternimal.widget.CommonView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/21 0021.
 * 已解决事件详情fragment
 */

public class EventDetailFragment extends BaseFragment<IBasePresenter> implements IEventDetailView {
    @BindView(R.id.resolved_event_detail_content)
    LinearLayout mResolvedContent;
//    @BindView(R.id.detail_content_layout)
//    LinearLayout mContentLayout;
//    @BindView(R.id.event_repair_images)
//    RecyclerView mRecyclerView;
//
//    @Inject
//    BaseRecyclerViewAdapter mAdapter;

    private String[] keys = {"事件单号：","组织：", "发起人：", "状态：", "标题：", "描述："};
    private String[] keys1 = {"服务：", "服务子项：","紧急程度：", "关联问题：", "关联变更："};
//    private String[] keys2 = {"请求类型：", "影响：", "紧急程度：", "优先级："};
    private String[] keys3 = {"维修团队：", "维修工程师："};
    private String[] keys4 = {"开始日期：", "最后修改：", "指派日期："};
    private String[] keys5 = {"父级请求：", "父级事件：", "父级问题：", "父级变更："};
    private String[] keys6 = {"TTO超时：", "TTO超时数："};

    private String mRequestId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            mRequestId = arg.getString("eventId", "请求id");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_resolved_detail;
    }

    @Override
    protected void initInjector() {
        DaggerResolvedDetailComponent.builder().resolvedDetailModule(new ResolvedDetailModule(mContext, this, mRequestId)).build().inject(this);
    }

    @Override
    protected void initViews() {
//        mAdapter = new RepairImagesAdapter(new ArrayList<String>(), mContext);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    public void getEventDetail(EventDetailBean detailBean) {
        if (null == detailBean) {
            showToast("服务器炸了 傻狗");
            return;
        }
        mResolvedContent.removeAllViews();

        CommonView view = new CommonView(mContext);
        String[] values = {detailBean.id,detailBean.organization, detailBean.appointedPerson, detailBean.eventState, detailBean.faultType, detailBean.description};
        ArrayList<CommonDetailBean> list = _initList(values, keys);
        view.setList(list);
        ArrayList<String> imageList = new ArrayList<>();
        if (!ListUtil.isEmpty(detailBean.images)) {
            for (String url : detailBean.images) {
                imageList.add(HttpAddressProperties.BASE_URL + url);
            }
            if (!ListUtil.isEmpty(imageList)) {
                view.setShowImagesLayout(true);
                view.setImagesType("报修图片");
                view.setImagesList(imageList);
            }
        }
        view.init();
        mResolvedContent.addView(view);
        _addLine();

        CommonView view1 = new CommonView(mContext);
        String[] values1 = {detailBean.serviceType, detailBean.serviceSubType, detailBean.urgencyLevel,detailBean.hotSign, detailBean.hotReason};
        ArrayList<CommonDetailBean> list1 = _initList(values1, keys1);
        view1.setList(list1);
        view1.setShowImagesLayout(false);
        view1.init();
        mResolvedContent.addView(view1);
        _addLine();

//        CommonView view2 = new CommonView(mContext);
//        String[] values2 = {detailBean.requestType, detailBean.effect, detailBean.urgencyLevel, detailBean.priority};
//        ArrayList<CommonDetailBean> list2 = _initList(values2, keys2);
//        view2.setList(list2);
//        view2.setShowImagesLayout(false);
//        view2.init();
//        mResolvedContent.addView(view2);
//        _addLine();

        CommonView view3 = new CommonView(mContext);
        String[] values3 = {detailBean.dealEventTeam, detailBean.dealMan};
        ArrayList<CommonDetailBean> list3 = _initList(values3, keys3);
        view3.setList(list3);
        view3.setShowImagesLayout(false);
        view3.init();
        mResolvedContent.addView(view3);
        _addLine();

        CommonView view4 = new CommonView(mContext);
        String[] values4 = {detailBean.startTime, detailBean.lastChangeTime, detailBean.appointTime};
        ArrayList<CommonDetailBean> list4 = _initList(values4, keys4);
        view4.setList(list4);
        view4.setShowImagesLayout(false);
        view4.init();
        mResolvedContent.addView(view4);
        _addLine();

        CommonView view5 = new CommonView(mContext);
        String[] values5 = {detailBean.parentRequest, detailBean.parentEvent, detailBean.parentQuestion, detailBean.parentChange};
        ArrayList<CommonDetailBean> list5 = _initList(values5, keys5);
        view5.setList(list5);
        view5.setShowImagesLayout(false);
        view5.init();
        mResolvedContent.addView(view5);
        _addLine();

        CommonView view6 = new CommonView(mContext);
        String[] values6 = {"否", "耗时0秒"};
        ArrayList<CommonDetailBean> list6 = _initList(values6, keys6);
        view6.setList(list6);
        view6.setShowImagesLayout(false);
        view6.init();
        mResolvedContent.addView(view6);
        _addLine();
    }

    @Override
    public void solveSuccess() {

    }

    @Override
    public void solveFail(String errMsg) {

    }

    @Override
    public void reassignSuccess() {

    }

    @Override
    public void reassignFail(String errMsg) {

    }

    private void _addLine() {
        View seqerateView = new View(mContext);
        seqerateView.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        seqerateView.setLayoutParams(params_1);
        mResolvedContent.addView(seqerateView);
        View seqerateView1 = new View(mContext);
        seqerateView1.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 1.5f)));
        params_2.setMargins(0, (int) (Util.dpToPixel(mContext, 10f)), 0, 0);
        seqerateView.setLayoutParams(params_2);
        mResolvedContent.addView(seqerateView1);
    }

    private ArrayList<CommonDetailBean> _initList(String[] values, String[] keys) {
        ArrayList<CommonDetailBean> list = new ArrayList<>();
        for (int i = 0; i < keys.length; i++) {
            CommonDetailBean bean = new CommonDetailBean();
            bean.key = keys[i];
            if (values.length > i && null != values[i]) {
                bean.value = values[i];
            }
            list.add(bean);
        }
        return list;
    }
}
