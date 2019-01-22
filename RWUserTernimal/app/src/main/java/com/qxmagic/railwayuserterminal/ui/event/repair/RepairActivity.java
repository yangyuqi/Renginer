package com.qxmagic.railwayuserterminal.ui.event.repair;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.injector.components.DaggerRepairComponent;
import com.qxmagic.railwayuserterminal.injector.modules.RepairModule;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairPresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.event.repair.callback.PopWindowChoiceItemCallback;
import com.qxmagic.railwayuserterminal.ui.event.repair.view.RepairPullDownPop;
import com.qxmagic.railwayuserterminal.ui.iview.IRepairView;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;
import com.qxmagic.railwayuserterminal.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 报修界面
 */
public class RepairActivity extends BaseActivity<IRepairPresenter> implements IRepairView {
    //    private int mOrgPos, mEffPos, mUrgPos, mPRPos, mPEPos, mPPPos, mPCPos;
    @BindView(R.id.repair_organization_text)
    TextView mOrganizationText;
    @BindView(R.id.repair_sponsor_text)
    TextView mSponsorText;
    //    @BindView(R.id.repair_effect_text)
//    TextView mEffectText;
    @BindView(R.id.repair_service_text)
    TextView mServiceText;
    @BindView(R.id.repair_sub_service_text)
    TextView mSubServiceText;
    @BindView(R.id.repair_urgency_level_text)
    TextView mUrgencyText;

//    @BindView(R.id.repair_team_text)
//    TextView mTeamText;
//    @BindView(R.id.repair_deal_man_text)
//    TextView mDealManText;


    @BindView(R.id.repair_parent_request_text)
    TextView mPaRequestText;
    @BindView(R.id.repair_parent_event_text)
    TextView mPaEventText;
    @BindView(R.id.repair_parent_problem_text)
    TextView mPaProblemText;
    @BindView(R.id.repair_parent_change_text)
    TextView mPaChangeText;


    @BindView(R.id.repair_title_edit)
    EditText mTitleEdit;


    @BindView(R.id.repair_description_layout)
    View mDescriptionView;
    @BindView(R.id.repair_images_layout)
    View mImagesView;

//    @BindView(R.id.repair_state_text)
//    TextView mStateText;
//    @BindView(R.id.repair_request_type_text)
//    TextView mRequestTypeText;
//    @BindView(R.id.repair_priority_text)
//    TextView mPriorityText;

    private ArrayList<String> serviceList = new ArrayList<>();
    private ArrayList<String> subServiceList = new ArrayList<>();
    private ArrayList<String> pEventList = new ArrayList<>();
    private ArrayList<String> pRequestList = new ArrayList<>();
    private ArrayList<String> pQuestionList = new ArrayList<>();
    private ArrayList<String> pChangeList = new ArrayList<>();
//    private ArrayList<String> teamList = new ArrayList<>();
//    private ArrayList<String> dealManList = new ArrayList<>();

    private String title;
    private String description;

    /**
     * 上传图片的path集合
     */
    private ArrayList<String> uploadImageList = new ArrayList<>();

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            title = mTitleEdit.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                showToast("请输入标题");
                return;
            }
            if (TextUtils.isEmpty(description)) {
                showToast("请输入描述");
                return;
            }
//            if ("请选择".equals(mServiceText.getText().toString().trim())) {
//                showToast("请选择服务");
//                return;
//            }
            if ("请选择".equals(mUrgencyText.getText().toString().trim())) {
                showToast("请选择紧急程度");
                return;
            }
//            if("请选择".equals(mTeamText.getText().toString().trim())){
//                showToast("请选择处理团队");
//                return;
//            }
//            if("请选择".equals(mDealManText.getText().toString().trim())){
//                showToast("请选择指派人");
//                return;
//            }
            mPresenter.checkTitle(title);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
        updateViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_repair;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "报修", "提交", false, false, listener);
        hideSoftInputFromWindow(mTitleEdit);
        if (RWUTApplication.getInstance().mLoginUser != null) {
            mOrganizationText.setText(RWUTApplication.getInstance().mLoginUser.orgName);
            mSponsorText.setText(RWUTApplication.getInstance().mLoginUser.sponsor);
        }
    }

    @Override
    protected void updateViews() {
        mPresenter.getDataByOrg();
    }

    @Override
    protected void initInjector() {
        DaggerRepairComponent.builder().applicationComponent(
                getAppComponent()).repairModule(new RepairModule(this, mContext)).
                build().inject(this);
    }

    @Override
    public void repairResult(String result) {
        //报修的结果
        if ("-1".equals(result)) {
            showToast("获取数据失败，请检查您的网络");
        } else if ("-2".equals(result)) {
            showToast("获取指派人失败");
        } else if ("0".equals(result)) {
            showToast("该标题已存在，请重新输入");
        } else if ("1".equals(result)) {
            if (ListUtil.isEmpty(uploadImageList))
                sendRepair("");
            else
                mPresenter.uploadAttachment(uploadImageList);
        } else if ("2".equals(result)) {
            showToast("报修失败");
        } else if ("3".equals(result)) {
            showToast("报修成功，已添加至未解决事件当中");
            setResult(RESULT_OK);
            finish();
        }
        Logger.i(result);
    }

    @Override
    public void uploadAttachFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void uploadAttachSuccess(String filePath) {
        sendRepair(filePath);
    }

    private boolean sendRepair(String filePath) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("orgName", RWUTApplication.getInstance().mLoginUser.orgName);
        params.put("sponsor", RWUTApplication.getInstance().mLoginUser.sponsor);
        params.put("title", title);
        params.put("description", description);
        params.put("source", "客户");
        params.put("state", "新建");
        params.put("service", "请选择".equals(mServiceText.getText().toString().trim()) ? "" : mServiceText.getText().toString().trim());
        params.put("serviceSubkey", "请选择".equals(mSubServiceText.getText().toString().trim()) ? "" : mSubServiceText.getText().toString().trim());
        params.put("urgencyLevel", mUrgencyText.getText().toString().trim());
//        params.put("appointTeam",mTeamText.getText().toString().trim());
//        params.put("appointPerson",mDealManText.getText().toString().trim());
        params.put("parentRequest", "请选择".equals(mPaRequestText.getText().toString().trim()) ? "" : mPaRequestText.getText().toString().trim());
        params.put("parentEvent", "请选择".equals(mPaEventText.getText().toString().trim()) ? "" : mPaEventText.getText().toString().trim());
        params.put("parentProblem", "请选择".equals(mPaProblemText.getText().toString().trim()) ? "" : mPaProblemText.getText().toString().trim());
        params.put("parentChange", "请选择".equals(mPaChangeText.getText().toString().trim()) ? "" : mPaChangeText.getText().toString().trim());
        params.put("str2", filePath);
        mPresenter.repair(params);
        return false;
    }

    @Override
    public void getServiceList(ArrayList<String> serviceList) {
        this.serviceList.clear();
        if (!ListUtil.isEmpty(serviceList)) {
            this.serviceList.addAll(serviceList);
        }
    }

    @Override
    public void getSubServiceList(ArrayList<String> subServiceList) {
        this.subServiceList.clear();
        if (!ListUtil.isEmpty(subServiceList)) {
            this.subServiceList.addAll(subServiceList);
        }
    }

    @Override
    public void getpRequestList(ArrayList<String> pRequestList) {
        this.pRequestList.clear();
        if (!ListUtil.isEmpty(pRequestList)) {
            this.pRequestList.addAll(pRequestList);
        }
    }

    @Override
    public void getpEventList(ArrayList<String> pEventList) {
        this.pEventList.clear();
        if (!ListUtil.isEmpty(pEventList)) {
            this.pEventList.addAll(pEventList);
        }
    }

    @Override
    public void getpQuestiontList(ArrayList<String> pQuestionList) {
        this.pQuestionList.clear();
        if (!ListUtil.isEmpty(pQuestionList)) {
            this.pQuestionList.addAll(pQuestionList);
        }
    }

    @Override
    public void getpChangeList(ArrayList<String> pChangeList) {
        this.pChangeList.clear();
        if (!ListUtil.isEmpty(pChangeList)) {
            this.pChangeList.addAll(pChangeList);
        }
    }

    @Override
    public void getTeamList(ArrayList<String> teamList) {
//        this.teamList.clear();
//        if(!ListUtil.isEmpty(teamList)){
//            this.teamList.addAll(teamList);
//        }
    }

    @Override
    public void getDealManList(ArrayList<String> dealManList) {
//        this.dealManList.clear();
//        if(!ListUtil.isEmpty(dealManList)){
//            this.dealManList.addAll(dealManList);
//        }
    }

    @OnClick({R.id.repair_service_pop, R.id.repair_sub_service_pop,
//            R.id.repair_team_pop, R.id.repair_deal_man_pop,
            R.id.repair_urgency_level_pop, R.id.repair_parent_request_pop,
            R.id.repair_parent_event_pop, R.id.repair_parent_problem_pop,
            R.id.repair_parent_change_pop, R.id.repair_description_layout,
            R.id.repair_images_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.repair_service_pop:
                RepairPullDownPop mPop = new RepairPullDownPop(mContext, serviceList,
                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
                        new PopWindowChoiceItemCallback() {
                            @Override

                            public void getChoiceItem(int position, String content) {
                                mServiceText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.repair_service_pop));
                break;
            case R.id.repair_sub_service_pop:
                mPop = new RepairPullDownPop(mContext, subServiceList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override

                            public void getChoiceItem(int position, String content) {
                                mSubServiceText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.repair_sub_service_pop));
                break;
//            case R.id.repair_team_pop:
//                mPop = new RepairPullDownPop(mContext, teamList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//
//                            public void getChoiceItem(int position, String content) {
//                                mTeamText.setText(content);
//                                mPresenter.getDealManList(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.repair_team_pop));
//                break;
//            case R.id.repair_deal_man_pop:
//                mPop = new RepairPullDownPop(mContext, dealManList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//
//                            public void getChoiceItem(int position, String content) {
//                                mDealManText.setText(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.repair_deal_man_pop));
//                break;

            case R.id.repair_urgency_level_pop:
                mPop = getPop(R.array.urgency_level,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                mUrgencyText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.repair_urgency_level_pop));
                break;
            case R.id.repair_parent_request_pop:
                mPop = new RepairPullDownPop(mContext, pRequestList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                mPaRequestText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.repair_parent_request_pop));
                break;
            case R.id.repair_parent_event_pop:
                mPop = new RepairPullDownPop(mContext, pEventList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                mPaEventText.setText(content);
                            }
                        });
                mPop.showWindowUp(findViewById(R.id.repair_parent_event_pop));
                break;
            case R.id.repair_parent_problem_pop:
                mPop = new RepairPullDownPop(mContext, pQuestionList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                mPaProblemText.setText(content);
                            }
                        });
                mPop.showWindowUp(findViewById(R.id.repair_parent_problem_pop));
                break;
            case R.id.repair_parent_change_pop:
                mPop = new RepairPullDownPop(mContext, pChangeList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
                                mPaChangeText.setText(content);
                            }
                        });
                mPop.showWindowUp(findViewById(R.id.repair_parent_change_pop));
                break;
            case R.id.repair_description_layout:
                //跳转至描述界面
                Intent intent = new Intent(mContext, RepairDescriptionActivity.class);
                intent.putExtra("description", description);
                startActivityForResult(intent, 1000);
                break;
            case R.id.repair_images_layout:
                //跳转至图片选择界面
                Intent intent1 = new Intent(mContext, RepairImagesActivity.class);
                intent1.putStringArrayListExtra("imagesList", uploadImageList);
                startActivityForResult(intent1, 2000);
                break;
            default:
                break;
        }
    }

    /**
     * 创建popup window
     *
     * @param arrayId  资源字符串文件id
     * @param callback item点击回调
     * @return 报修的各种下拉框
     */
    private RepairPullDownPop getPop(int arrayId, int width, PopWindowChoiceItemCallback callback) {
        ArrayList<String> mList = new ArrayList<>();
        String[] mArray = getResources().getStringArray(arrayId);
        Collections.addAll(mList, mArray);
        return new RepairPullDownPop(mContext, mList, width, callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            if (requestCode == 1000) {
                showToast("描述已保存");
                description = data.getStringExtra("description");
            } else if (requestCode == 2000) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    uploadImageList = bundle.getStringArrayList("imagesList");
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferencesUtil.saveSharedPreferences(mContext, SharedPreferencesUtil.REPAIR_DESCRIPTION, "");
    }


}
