package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.api.HttpAddressProperties;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerUnresolvedEventDetailComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.UnresolvedEventDetailModule;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.IEventOperationPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.iview.IEventDetailView;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.Util;
import com.qxmagic.railwayengineerternimal.widget.CommonDialog;
import com.qxmagic.railwayengineerternimal.widget.CommonView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Christian on 2017/3/29 0029.
 * 未解决事件详情
 */

public class UnresolvedEventDetailFragment extends BaseFragment<IEventOperationPresenter> implements IEventDetailView {
    @BindView(R.id.unresolved_event_detail_content)
    LinearLayout mUnresolvedContent;

    private String[] keys = {"事件单号：", "组织：", "发起人：", "状态：", "标题：", "描述："};
    private String[] keys1 = {"服务：", "服务子项：", "紧急程度：", "关联问题：", "关联变更："};
    //    private String[] keys2 = {"请求类型：", "影响：", "紧急程度：", "优先级："};
    private String[] keys3 = {"维修团队：", "维修工程师："};
    private String[] keys4 = {"开始日期：", "最后修改：", "指派日期："};
    private String[] keys5 = {"父级请求：", "父级事件：", "父级问题：", "父级变更："};
    private String[] keys6 = {"TTO超时：", "TTO超时数："};

    private String mRequestId;
    private String type;
    private EventDetailBean detailBean;

    private CommonDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (null != arg) {
            mRequestId = arg.getString("eventId");
            type = arg.getString("type");
        }
    }

    @Override
    public void getEventDetail(EventDetailBean detailBean) {
        if (null == detailBean) {
            return;
        }
        this.detailBean = detailBean;
        mUnresolvedContent.removeAllViews();

        CommonView view = new CommonView(mContext);
        String[] values = {detailBean.id, detailBean.organization, detailBean.appointedPerson, detailBean.eventState, detailBean.faultType, detailBean.description};
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
        mUnresolvedContent.addView(view);
        _addLine();

        CommonView view1 = new CommonView(mContext);
        String[] values1 = {detailBean.serviceType, detailBean.serviceSubType, detailBean.urgencyLevel, detailBean.hotSign, detailBean.hotReason};
        ArrayList<CommonDetailBean> list1 = _initList(values1, keys1);
        view1.setList(list1);
        view1.setShowImagesLayout(false);
        view1.init();
        mUnresolvedContent.addView(view1);
        _addLine();

//        CommonView view2 = new CommonView(mContext);
//        String[] values2 = {detailBean.requestType, detailBean.effect, detailBean.urgencyLevel, detailBean.priority};
//        ArrayList<CommonDetailBean> list2 = _initList(values2, keys2);
//        view2.setList(list2);
//        view2.setShowImagesLayout(false);
//        view2.init();
//        mUnresolvedContent.addView(view2);
//        _addLine();

        CommonView view3 = new CommonView(mContext);
        String[] values3 = {detailBean.dealEventTeam, detailBean.dealMan};
        ArrayList<CommonDetailBean> list3 = _initList(values3, keys3);
        view3.setList(list3);
        view3.setShowImagesLayout(false);
        view3.init();
        mUnresolvedContent.addView(view3);
        _addLine();

        CommonView view4 = new CommonView(mContext);
        String[] values4 = {detailBean.startTime, detailBean.lastChangeTime, detailBean.appointTime};
        ArrayList<CommonDetailBean> list4 = _initList(values4, keys4);
        view4.setList(list4);
        view4.setShowImagesLayout(false);
        view4.init();
        mUnresolvedContent.addView(view4);
        _addLine();

        CommonView view5 = new CommonView(mContext);
        String[] values5 = {detailBean.parentRequest, detailBean.parentEvent, detailBean.parentQuestion, detailBean.parentChange};
        ArrayList<CommonDetailBean> list5 = _initList(values5, keys5);
        view5.setList(list5);
        view5.setShowImagesLayout(false);
        view5.init();
        mUnresolvedContent.addView(view5);
        _addLine();

        CommonView view6 = new CommonView(mContext);
        String[] values6 = {"否", "耗时0秒"};
        ArrayList<CommonDetailBean> list6 = _initList(values6, keys6);
        view6.setList(list6);
        view6.setShowImagesLayout(false);
        view6.init();
        mUnresolvedContent.addView(view6);
        _addLine();
    }

    @Override
    public void solveSuccess() {
//        Intent intent = new Intent(mContext, UnToResolvedActivity.class);
//        intent.putExtra("请求id", "请求id");
//        startActivity(intent);
//        getActivity().setResult(RESULT_OK);
//        getActivity().finish();
    }

    @Override
    public void solveFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void reassignSuccess() {
        showToast("重新指派成功");
        getActivity().setResult(RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void reassignFail(String errMsg) {
        showToast(errMsg);
    }

    private void _addLine() {
        View seqerateView = new View(mContext);
        seqerateView.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        seqerateView.setLayoutParams(params_1);
        mUnresolvedContent.addView(seqerateView);
        View seqerateView1 = new View(mContext);
        seqerateView1.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 1.5f)));
        params_2.setMargins(0, (int) (Util.dpToPixel(mContext, 10f)), 0, 0);
        seqerateView.setLayoutParams(params_2);
        mUnresolvedContent.addView(seqerateView1);
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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_unresolved_event_detail_layout;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerUnresolvedEventDetailComponent.builder().unresolvedEventDetailModule(new UnresolvedEventDetailModule(this, mContext, mRequestId)).build().inject(this);
    }

    @OnClick({R.id.unresolved_resolved_text, R.id.unresolved_reassign_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unresolved_resolved_text:
//                mPresenter.solveEvent();
                dialog = new CommonDialog(mContext, "提示", "是否确定已解决当前事件？", new CommonDialog.OnButtonClickListener() {
                    @Override
                    public void onCancelClick() {

                    }

                    @Override
                    public void onConfirmClick() {
                        Intent intent = new Intent(mContext, UnToResolvedActivity.class);
                        intent.putExtra("eventId", mRequestId);
                        intent.putExtra("relateQuestion", detailBean.hotSign);
                        startActivityForResult(intent, 1000);
                    }
                });
                dialog.setTextGone(false, false);
                dialog.setCanceledOnTouchOutside(false);
                if (!dialog.isShowing()) {
                    dialog.showMiddleDialog();
                }
                break;
            case R.id.unresolved_reassign_text:
                mPresenter.reassign();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 1000 == requestCode) {
            getActivity().setResult(RESULT_OK);
            getActivity().finish();
        }
    }
}
