package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.publish;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerNewPublichComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.NewPublishModule;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.INewPublishView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.callback.PopWindowChoiceItemCallback;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.FileUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;
import com.qxmagic.railwayengineerternimal.utils.StringUtil;
import com.qxmagic.railwayengineerternimal.widget.RepairPullDownPop;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.qxmagic.railwayengineerternimal.utils.Util.canVerticalScroll;

/**
 * Created by Christian on 2017/3/31 0031.
 * 发布界面
 */

public class NewPublishActivity extends BaseActivity<INewVariousPresenter> implements INewPublishView, View.OnTouchListener {
    //    private int mTypePos, mReManPos, mPriorityPos, mApproverPos, mReEventPos, mReQuestionPos, mReChanges, mReKnowledgePos;
    @BindView(R.id.new_publish_org_text)
    TextView mOrgText;
    @BindView(R.id.new_publish_requestMan_text)
    TextView mReManText;
    @BindView(R.id.new_publish_priority_text)
    TextView mPriorityText;
    //    @BindView(R.id.new_publish_effect_text)
//    TextView mEffectText;
//    @BindView(R.id.new_publish_state_text)
//    TextView mStateText;
    @BindView(R.id.new_publish_approver_text)
    TextView mApproveText;
    @BindView(R.id.new_publish_relationEvent_text)
    TextView mReEventText;
    @BindView(R.id.new_publish_relationQuestion_text)
    TextView mReQuestionText;
    @BindView(R.id.new_publish_relationChange_text)
    TextView mReChangeText;
    @BindView(R.id.new_publish_relationKnowledge_text)
    TextView mReKnowledgeText;

    @BindView(R.id.new_publish_title_edit)
    EditText mTitleEdit;
    @BindView(R.id.new_publish_content_edit)
    EditText mContentEdit;
//    @BindView(R.id.new_publish_opinion_edit)
//    EditText mOpinionEdit;

    @BindView(R.id.new_publish_requestDate_text)
    TextView mReDateEdit;
    @BindView(R.id.new_publish_planStartDate_text)
    TextView mStDateEdit;
    @BindView(R.id.new_publish_planEndDate_text)
    TextView mEndDateEdit;
//    @BindView(R.id.new_publish_approvalDate_edit)
//    TextView mApDateEdit;

//    @BindView(R.id.new_publish_requestDate_img)
//    ImageView mReImg;
//    @BindView(R.id.new_publish_planStartDate_img)
//    ImageView mStImg;
//    @BindView(R.id.new_publish_planEndDate_img)
//    ImageView mEndImg;
//    @BindView(R.id.new_publish_approvalDate_img)
//    ImageView mApImg;

    @BindView(R.id.new_publish_attach_name)
    TextView mAttachName;


    private EventDetailBean detailBean;

    //    private ArrayList<String> relateQuestionList=new ArrayList<>();
//    private ArrayList<String> relateChangeList=new ArrayList<>();
    private ArrayList<String> relateKnowledgeList = new ArrayList<>();
    private ArrayList<String> approveList = new ArrayList<>();

    /**
     * 选择文件返回码
     */
    private static final int SELECT_FILE = 5000;

    /**
     * 附件路径
     */
    private String attachPath = "";

    /**
     * 附件名称
     */
    private String fileName = "";

    private String title, description, priority, requestTime, planStartTime, planEndTime, approve, relateKnowledge;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealIntent();
        initInjector();
        initViews();
        _bindListener();
        updateViews();
    }

    private void dealIntent() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            detailBean = (EventDetailBean) bundle.getSerializable("eventBean");
        } else {
            detailBean = new EventDetailBean();
        }
    }

    private void _bindListener() {
        mReDateEdit.setOnTouchListener(this);
        mStDateEdit.setOnTouchListener(this);
        mEndDateEdit.setOnTouchListener(this);
        mTitleEdit.requestFocus();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_publish;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "新建发布", 0, false, true);
        mOrgText.setText(RWETApplication.getInstance().mLoginUser.orgName);
        mReManText.setText(RWETApplication.getInstance().mLoginUser.sponsor);
        mReEventText.setText(detailBean.faultType);
        mReQuestionText.setText(detailBean.hotSign);
        mReChangeText.setText(detailBean.hotReason);
        hideSoftInputFromWindow(mTitleEdit);
        hideSoftInputFromWindow(mContentEdit);
        mContentEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.new_publish_content_edit && canVerticalScroll(mContentEdit))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });
    }

    @Override
    protected void updateViews() {
//        mPresenter.getDataByOrgName(detailBean.faultType);
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerNewPublichComponent.builder().applicationComponent(getAppComponent()).
                newPublishModule(new NewPublishModule(this, mContext)).
                build().inject(this);
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

    @OnClick({R.id.new_publish_priority_pop, R.id.new_publish_approver_pop,
           /* R.id.new_publish_relationQuestion_pop,
            R.id.new_publish_relationChange_pop,*/ R.id.new_publish_relationKnowledge_pop,
            R.id.new_publish_select_file_text, R.id.new_publish_confirm,
            R.id.new_publish_requestDate_img, R.id.new_publish_planStartDate_img,
            R.id.new_publish_planEndDate_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_publish_priority_pop:
                RepairPullDownPop mPop = getPop(R.array.urgency_level,
                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mPriorityPos = position;
                                priority = content;
                                if (TextUtils.isEmpty(content))
                                    mPriorityText.setText("请选择");
                                else
                                    mPriorityText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_publish_priority_pop));
//                mPop.setSelectedPosition(mPriorityPos);
                break;
            case R.id.new_publish_approver_pop:
                mPop = new RepairPullDownPop(mContext, approveList,
                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mApproverPos = position;
                                approve = content;
                                if (TextUtils.isEmpty(content))
                                    mApproveText.setText("请选择");
                                else
                                    mApproveText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_publish_approver_pop));
//                mPop.setSelectedPosition(mApproverPos);
                break;
//            case R.id.new_publish_relationQuestion_pop:
//                mPop =  new RepairPullDownPop(mContext, relateQuestionList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mReQuestionPos = position;
//                                mReQuestionText.setText(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.new_publish_relationQuestion_pop));
////                mPop.setSelectedPosition(mReQuestionPos);
//                break;
//            case R.id.new_publish_relationChange_pop:
//                mPop =  new RepairPullDownPop(mContext, relateChangeList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mReChanges = position;
//                                mReChangeText.setText(content);
//                            }
//                        });
//                mPop.showWindowUp(findViewById(R.id.new_publish_relationChange_pop));
////                mPop.setSelectedPosition(mReChanges);
//                break;
            case R.id.new_publish_relationKnowledge_pop:
                mPop = new RepairPullDownPop(mContext, relateKnowledgeList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mReKnowledgePos = position;
                                relateKnowledge = content;
                                if (TextUtils.isEmpty(content))
                                    mReKnowledgeText.setText("请选择");
                                else
                                    mReKnowledgeText.setText(content);
                            }
                        });
                mPop.showWindowUp(findViewById(R.id.new_publish_relationKnowledge_pop));
//                mPop.setSelectedPosition(mReKnowledgePos);
                break;
            case R.id.new_publish_requestDate_img:
                //弹出日期对话框
                showDatePicker(mReDateEdit);
                break;
            case R.id.new_publish_planStartDate_img:
                //弹出日期对话框
                showDatePicker(mStDateEdit);
                break;
            case R.id.new_publish_planEndDate_img:
                //弹出日期对话框
                showDatePicker(mEndDateEdit);
                break;
            case R.id.new_publish_select_file_text:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(NewPublishActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 10);
                    } else {
                        //上传附件
                        selectFile();
                    }
                }else {
                    //上传附件
                    selectFile();
                }
                break;
            case R.id.new_publish_confirm:
                checkNull();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (10 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //上传附件
                selectFile();
            }
        }
    }

    private void showDatePicker(final TextView editText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                int month = monthOfYear + 1;
                editText.setText(year + "-" + month + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "选择文件"),
                    SELECT_FILE);
        } catch (ActivityNotFoundException ex) {
            showToast("请安装文件管理器");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && SELECT_FILE == requestCode) {
            if (data != null) {
                Uri uri = data.getData();
                //根据版本号不同使用不同的Action. 4.4系统 ，通过不同的方法获取
                attachPath = FileUtil.getImageAbsolutePath(this, uri);
                if (!StringUtil.isEmpty(attachPath)) {
                    String fileSuffixName = "";
                    int dotIndex = attachPath.lastIndexOf(".");
                    fileName = attachPath;
                    try {
                        //截取文件名
                        String[] array = attachPath.split("/");
                        int length = array.length;
                        fileName = array[length - 1];
                        //截取后缀名
                        String[] array1 = attachPath.split("[.]");
                        fileSuffixName = array1[array1.length - 1];
                    } catch (Exception e) {
                        Logger.e(e.toString());
                    }
                    if (dotIndex < 0) {
                        showToast("请选择正确的格式文件");
                        return;
                    }
                    //只能按下back键才消失进度条
//                        ProgressUtil.showUploadProgressDialog(this, "正在上传附件...");
//                        ProgressUtil.isUploadCancel = false;
//                        mFileText.setText(fileName);
                    mAttachName.setText(fileName);
                }
            }
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (view.getId()) {
                case R.id.new_publish_requestDate_text:
                    showDatePicker(mReDateEdit);
                    break;
                case R.id.new_publish_planStartDate_text:
                    showDatePicker(mStDateEdit);
                    break;
                case R.id.new_publish_planEndDate_text:
                    showDatePicker(mEndDateEdit);
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void checkTitle(String result) {
        //发送新建请求
        if ("okay".equals(result)) {
            if (TextUtils.isEmpty(attachPath)) {
                publish("");
            } else {
                mPresenter.uploadAttachment(attachPath);
            }
        } else {
            showToast(result);
        }
    }

    @Override
    public void uploadAttachFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void uploadAttachSuccess(String filePath) {
        publish(filePath);
    }

    private void checkNull() {
        title = mTitleEdit.getText().toString().trim();
        description = mContentEdit.getText().toString().trim();
        requestTime = mReDateEdit.getText().toString().trim();
        planStartTime = mStDateEdit.getText().toString().trim();
        planEndTime = mEndDateEdit.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请选择标题");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            showToast("请选择内容");
            return;
        }
        if (TextUtils.isEmpty(requestTime)) {
            showToast("请选择请求日期");
            return;
        }
        if (TextUtils.isEmpty(priority)) {
            showToast("请选择优先级");
            return;
        }
        if (TextUtils.isEmpty(approve)) {
            showToast("请选择审批人");
            return;
        }
        if (TextUtils.isEmpty(planStartTime)) {
            showToast("请选择计划开始日期");
            return;
        }
        if (TextUtils.isEmpty(planEndTime)) {
            showToast("请选择计划结束日期");
            return;
        }
        mPresenter.checkTitle(title);
    }

    private void publish(String attach) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("pubType", "新建");
        params.put("effect", RWETApplication.getInstance().mLoginUser.orgName);
        params.put("petioner", RWETApplication.getInstance().mLoginUser.sponsor);
        params.put("petionerTime", requestTime);
        params.put("priority", priority);
        params.put("pubState", "待审批");
        params.put("title", title);
        params.put("context", description);
        if (attach.contains("\\")) {
            attach = attach.replace("\\", "/");
        }
        params.put("annex", attach);
        params.put("startTime", planStartTime);
        params.put("endTime", planEndTime);
        params.put("approver", approve);
        params.put("relatedEvents", TextUtils.isEmpty(detailBean.faultType) ? "" : detailBean.faultType);
        params.put("relatedProblem", detailBean.hotSign == null ? "" : detailBean.hotSign);
        params.put("relatedChange", detailBean.hotReason == null ? "" : detailBean.hotReason);
        params.put("relatedKnowledge", TextUtils.isEmpty(relateKnowledge) ? "" : relateKnowledge);
        mPresenter.newVarious(params);
    }


    @Override
    public void publishSuccess() {
        showToast("新建发布成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void publishFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void getApproveList(ArrayList<String> list) {
        approveList.clear();
        if (!ListUtil.isEmpty(list)) {
            approveList.addAll(list);
        }
    }

    @Override
    public void getQuestionList(ArrayList<String> list) {
//        relateQuestionList.clear();
//        if(!ListUtil.isEmpty(list)){
//            relateQuestionList.addAll(list);
//        }
    }

    @Override
    public void getChangeList(ArrayList<String> list) {
//        relateChangeList.clear();
//        if(!ListUtil.isEmpty(list)){
//            relateChangeList.addAll(list);
//        }
    }

    @Override
    public void getKnowLedgeList(ArrayList<String> list) {
        relateKnowledgeList.clear();
        if (!ListUtil.isEmpty(list)) {
            relateKnowledgeList.addAll(list);
        }
    }
}
