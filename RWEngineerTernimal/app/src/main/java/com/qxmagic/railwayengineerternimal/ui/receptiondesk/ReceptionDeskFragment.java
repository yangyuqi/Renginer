package com.qxmagic.railwayengineerternimal.ui.receptiondesk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerReceptionComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.ReceptionDeskModule;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseFragment;
import com.qxmagic.railwayengineerternimal.ui.MainActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IReceptionDeskView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.EventActivity;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.question.QuestionActivity;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Christian on 2017/3/24 0024.
 * 服务台fragment
 */
public class ReceptionDeskFragment extends BaseFragment<IBasePresenter> implements IReceptionDeskView {
    @BindView(R.id.reception_desk_event_count)
    TextView mEventCount;
    @BindView(R.id.reception_desk_request_count)
    TextView mRequestCount;
    @BindView(R.id.knowledge_request_layout)
    RelativeLayout knowledgeRequestLayout;
    @BindView(R.id.depley_request_layout)
    RelativeLayout depleyRequestLayout;
    Unbinder unbinder;

    public putDataInterface anInterface ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        anInterface = (MainActivity)activity;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_reception_desk;
    }

    @Override
    protected void initInjector() {
        DaggerReceptionComponent.builder().applicationComponent(getAppComponent()).
                receptionDeskModule(new ReceptionDeskModule(this, mContext)).
                build().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getData();
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initFragmentTitle(mRootView, "工程师", 0, 0, null, null, true, true);
    }

    @Override
    protected void updateViews() {
    }

    @OnClick({R.id.reception_desk_event_layout, R.id.reception_desk_request_layout,R.id.knowledge_request_layout,R.id.depley_request_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reception_desk_event_layout:
                startActivity(new Intent(mContext, EventActivity.class));
                break;
            case R.id.reception_desk_request_layout:
                startActivity(new Intent(mContext, QuestionActivity.class));
                break;
            case R.id.depley_request_layout :
                anInterface.putData(2);
                break;

            case R.id.knowledge_request_layout :
                anInterface.putData(1);
                break;

            default:
                break;
        }
    }

    @Override
    public void getEventCount(String count) {
        mEventCount.setText(count);
    }

    @Override
    public void getQuestionCount(String count) {
        mRequestCount.setText(count);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface putDataInterface{
        void putData(int data);
    }
}
