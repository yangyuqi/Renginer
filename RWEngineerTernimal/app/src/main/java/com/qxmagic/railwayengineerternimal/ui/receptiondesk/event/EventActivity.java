package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.reassign.ReassignActivity;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/28 0028.
 * 事件页面
 */

public class EventActivity extends BaseActivity<IBasePresenter> {
    @BindView(R.id.event_reassign_text)
    TextView mReassignCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_event;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "工程师", 0, false, true);
    }

    @Override
    protected void updateViews() {
        //直接请求
        RetrofitService.getUnresolvedEventCount(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在获取数据中..", false);
            }
        }).
                compose(this.<CommonResp<String>>bindToLife()).
                subscribe(new Subscriber<CommonResp<String>>() {
                    @Override
                    public void onCompleted() {
                        ProgressUtil.dismissProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                        ProgressUtil.dismissProgressDialog();
                        mReassignCount.setText("收到0条新的事件");
                    }

                    @Override
                    public void onNext(CommonResp<String> commonResp) {
                        if (commonResp != null) {
                            mReassignCount.setText("收到" + (commonResp.commonBean == null ? "0" : commonResp.commonBean) + "条新的事件");
                        }
                    }
                });
    }

    @Override
    protected void initInjector() {

    }

    @OnClick({R.id.event_already_solved_layout, R.id.event_tobe_solved_layout, R.id.event_reassign_layout})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.event_already_solved_layout:
                //跳转至已解决事件
                intent.setClass(mContext, ResolvedActivity.class);
                startActivity(intent);
                break;
            case R.id.event_tobe_solved_layout:
                intent.setClass(mContext, UnresolvedActivity.class);
                startActivity(intent);
                //跳转至未解决事件
                break;
            case R.id.event_reassign_layout:
                //跳转至重新指派
                intent.setClass(mContext, ReassignActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
