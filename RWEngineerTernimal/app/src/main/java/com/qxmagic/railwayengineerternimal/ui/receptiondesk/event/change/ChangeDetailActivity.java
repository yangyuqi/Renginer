package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.CommonDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IChangeDetailView;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.Util;
import com.qxmagic.railwayengineerternimal.widget.CommonView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * Created by Christian on 2017/3/29 0029.
 * 变更详情
 */

public class ChangeDetailActivity extends BaseActivity<IBasePresenter> implements IChangeDetailView {
    @BindView(R.id.change_detail_content)
    LinearLayout mQuestionContent;
    @BindView(R.id.change_detail_enclosure_name)
    TextView mEnclosureText;
//    private String mRequestId;

    private ChangeDetailBean detailBean;

    private String[] keys = {"标题：", "组织：", "发起人：", "描述："};
    private String[] keys1 = {"变更类型：", "审批人："};
    private String[] keys2 = {"关联事件", "父级变更："};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initInjector();
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (null != bundle) {
            detailBean = (ChangeDetailBean) bundle.getSerializable("detailBean");
        } else {
            detailBean = new ChangeDetailBean();
        }
    }

    @Override
    public void getChangeDetail(ChangeDetailBean detailBean) {
        if (null == detailBean) {
            return;
        }
        mQuestionContent.removeAllViews();

        CommonView view = new CommonView(mContext);
        String[] values = {detailBean.changeTitle, detailBean.organization,
                detailBean.sponsor, detailBean.description};
        ArrayList<CommonDetailBean> list = _initList(values, keys);
        view.setList(list);
        String[] images = detailBean.images;
        if (null != images && images.length != 0) {
            ArrayList<String> imageList = new ArrayList<>();
            Collections.addAll(imageList, images);
            if (!ListUtil.isEmpty(imageList)) {
                view.setShowImagesLayout(true);
                view.setImagesType("变更图片");
                view.setImagesList(imageList);
            }
        }
        view.init();
        mQuestionContent.addView(view);
        _addLine();

        CommonView view1 = new CommonView(mContext);
        String[] values1 = {detailBean.changeType, detailBean.sponsor};
        ArrayList<CommonDetailBean> list1 = _initList(values1, keys1);
        view1.setList(list1);
        view1.setShowImagesLayout(false);
        view1.init();
        mQuestionContent.addView(view1);
        _addLine();

        CommonView view2 = new CommonView(mContext);
        String[] values2 = {detailBean.relatedEvent, detailBean.parentChange};
        ArrayList<CommonDetailBean> list2 = _initList(values2, keys2);
        view2.setList(list2);
        view2.setShowImagesLayout(false);
        view2.init();
        mQuestionContent.addView(view2);
        _addLine();

//        CommonView view3 = new CommonView(mContext);
//        String[] values3 = {detailBean.startTime, detailBean.lastChangeTime, detailBean.appointTime, detailBean.endTime};
//        ArrayList<CommonDetailBean> list3 = _initList(values3, keys3);
//        view3.setList(list3);
//        view3.setShowImagesLayout(false);
//        view3.init();
//        mQuestionContent.addView(view3);
//        _addLine();

    }


    private void _addLine() {
        View seqerateView = new View(mContext);
        seqerateView.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        seqerateView.setLayoutParams(params_1);
        mQuestionContent.addView(seqerateView);
        View seqerateView1 = new View(mContext);
        seqerateView1.setBackgroundColor(getResources().getColor(R.color.gray_95a5a5_color));
        LinearLayout.LayoutParams params_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (Util.dpToPixel(mContext, 0.5f)));
        params_2.setMargins(0, (int) (Util.dpToPixel(mContext, 10f)), 0, 0);
        seqerateView.setLayoutParams(params_2);
        mQuestionContent.addView(seqerateView1);

        mEnclosureText.setText(detailBean.attach);
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
        return R.layout.activity_change_detail;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "变更详情", 0, false, true);
    }

    @Override
    protected void updateViews() {
//        mPresenter.getData();
        getChangeDetail(detailBean);
    }

    @Override
    protected void initInjector() {
//        DaggerChangeDetailComponent.builder().applicationComponent(
//                getAppComponent()).changeDetailModule(new ChangeDetailModule(this, mRequestId)).
//                build().inject(this);
    }

}
