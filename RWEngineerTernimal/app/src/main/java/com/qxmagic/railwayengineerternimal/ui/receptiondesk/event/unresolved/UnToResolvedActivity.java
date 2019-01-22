package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.presenter.IBasePresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.IBaseView;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.AddKnowledgeActivity;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/29 0029.
 * 未解决事件确认完成
 */

public class UnToResolvedActivity extends BaseActivity<IBasePresenter> implements IBaseView {
    @BindView(R.id.add_to_knowledge_layout)
    LinearLayout mAddToKnowledgeView;
    @BindView(R.id.confirm_image)
    ImageView mImageView;
    @BindView(R.id.confirm_content)
    TextView mTextView;

    private String eventId;
    private String relateQuestion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _dealIntent(getIntent());
        initViews();
        updateViews();
    }

    private void _dealIntent(Intent intent) {
        if (null != intent) {
            eventId = intent.getStringExtra("eventId");
            relateQuestion = intent.getStringExtra("relateQuestion");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_resolved_confirm;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "等待确认", 0, false, true);
        mAddToKnowledgeView.setVisibility(View.GONE);
        mImageView.setImageResource(R.mipmap.waiting_icon);
        mTextView.setText(getResources().getString(R.string.waiting_confirm));
    }

    @Override
    protected void updateViews() {
        //直接发送确认请求
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("uuid", eventId);
        params.put("state", "未解决");
        RetrofitService.dealCurrentEvent(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(this.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                showToast("连接服务台失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    CommonTitleUtil.initCommonTitle(UnToResolvedActivity.this, getResources().getString(R.string.already_confirm), 0, false, true);
                    mAddToKnowledgeView.setVisibility(View.VISIBLE);
                    mImageView.setImageResource(R.mipmap.success_icon);
                    mTextView.setText(getResources().getString(R.string.already_confirm));
                    setResult(RESULT_OK);
                } else {
                    showToast("请求失败");
                    finish();
                }
            }
        });
    }

    @Override
    protected void initInjector() {

    }

    @OnClick(R.id.add_to_knowledge)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_to_knowledge:
                Intent in = new Intent(mContext, AddKnowledgeActivity.class);
                in.putExtra("relateQuestion", relateQuestion);
                startActivityForResult(in, 1000);
//                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && 1000 == requestCode) {
            finish();
        }
    }
}
