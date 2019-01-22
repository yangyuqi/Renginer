package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.unresolved;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerNewQuestionComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.NewQuestionModule;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.INewVariousView;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.callback.PopWindowChoiceItemCallback;
import com.qxmagic.railwayengineerternimal.utils.CommonTitleUtil;
import com.qxmagic.railwayengineerternimal.utils.FileUtil;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;
import com.qxmagic.railwayengineerternimal.utils.StringUtil;
import com.qxmagic.railwayengineerternimal.widget.RepairPullDownPop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.qxmagic.railwayengineerternimal.utils.Util.canVerticalScroll;

/**
 * Created by Christian on 2017/3/29 0029.
 * 新建问题界面
 */

public class NewQuestionActivity extends BaseActivity<INewVariousPresenter> implements INewVariousView {
    //    private int mOrgPos, mSponPos, mSoPos, mQuTyPos, mEffPos, mUrgPos;
    @BindView(R.id.new_question_organization_text)
    TextView mOrganizationText;
    @BindView(R.id.new_question_sponsor_text)
    TextView mSponText;
    //    @BindView(R.id.new_question_source_text)
//    TextView mSoText;
    @BindView(R.id.new_question_service_text)
    TextView mServiceText;
    @BindView(R.id.new_question_service_pop)
    View mServicePop;
    @BindView(R.id.new_question_service_pop_text)
    TextView mServicePopText;

    @BindView(R.id.new_question_subService_text)
    TextView mSubServiceText;
    @BindView(R.id.new_question_subService_pop)
    View mSubServicePop;
    @BindView(R.id.new_question_subService_pop_text)
    TextView mSubServicePopText;

    @BindView(R.id.new_question_urgency_level_text)
    TextView mUrgencyText;
    @BindView(R.id.new_question_type_text)
    TextView mQuTyText;
    //    @BindView(R.id.new_question_effect_text)
//    TextView mEffectText;
    @BindView(R.id.new_question_relate_event_text)
    TextView mRelateEventText;

    @BindView(R.id.new_question_relate_event_pop)
    View mRelateEventPop;
    @BindView(R.id.new_question_relate_event_pop_text)
    TextView mRelateEventPopText;

    @BindView(R.id.new_question_relate_change_text)
    TextView mRelateChangeText;
    @BindView(R.id.new_question_team_text)
    TextView mTeamText;
    @BindView(R.id.new_question_deal_man_text)
    TextView mDealManText;

    @BindView(R.id.new_question_title_edit)
    EditText mTitleEdit;
    @BindView(R.id.new_question_production_edit)
    EditText mProductionEdit;
    @BindView(R.id.new_question_description_edit)
    EditText mDescriptionEdit;
    @BindView(R.id.new_question_reason_edit)
    EditText mReasonEdit;
    @BindView(R.id.new_question_resolvent_edit)
    EditText mResolventEdit;

//    @BindView(R.id.new_question_images_recyclerView)
//    RecyclerView mImagesRecyclerView;
//    private RepairImagesAdapter mAdapter;

    @BindView(R.id.new_question_select_file_text)
    TextView mFileText;


    @BindView(R.id.new_question_attach_name)
    TextView mAttachName;

//    @BindView(R.id.new_question_attach_recyclerView)
//    RecyclerView mAttachRecyclerView;
//    private ArrayList<String> attachList = new ArrayList<>();
//
//    private AttachAdapter mAttachAdapter;

    private EventDetailBean detailBean;

    private ArrayList<String> serviceList = new ArrayList<>();
    private ArrayList<String> subServiceList = new ArrayList<>();
    private ArrayList<String> quTyList = new ArrayList<>();
    private ArrayList<String> reEventList = new ArrayList<>();
    private ArrayList<String> reChangeList = new ArrayList<>();
    private ArrayList<String> teamList = new ArrayList<>();
    private ArrayList<String> dealManList = new ArrayList<>();

    /**
     * 视频文件后缀名数组
     */
//    public static String[] VIDEO_SUFFIX_ARRAY = {"wmv", "avi", "dat", "asf",
//            "rm", "rmvb", "ram", "mpg", "mpeg", "mp4", "mov", "m4v", "mkv",
//            "flv", "vob", "qt", "divx", "cpk", "fli", "flc", "mod", "dvix",
//            "dv", "ts", "3gp"};
//
//    /**
//     * 视频文件后缀名集合
//     */
//    private ArrayList<String> videoSuffixList = new ArrayList<>();
//
//    /**
//     * 上传图片的id集合
//     */
//    private ArrayList<String> imageIdList = new ArrayList<>();
//
//    /**
//     * 适配器中的path集合
//     */
//    private ArrayList<String> imagePathList = new ArrayList<>();
//
//    /**
//     * 上传图片的path集合
//     */
//    private ArrayList<String> uploadImageList = new ArrayList<>();

    /**
     * 选择文件返回码
     */
    private static final int SELECT_FILE = 5000;

    /**
     * 附件路径
     */
    private String attachPath = "";

    /**
     * 附件id
     */
//    private String attachId = "";

    /**
     * 附件名称
     */
    private String fileName = "";

    /**
     * 图片 类型
     */
//    private static final String TYPE_PHOTO = "image/*";

    /**
     * 图片PATH
     */
//    private String picPath = "";

    /**
     * 调用系统相机 指定输出文件
     */
//    public static File PHOTO_FILE;

    /**
     * 调用系统相机 指定输出文件的 Uri
     */
//    private static Uri PHOTO_URI;

    /**
     * 相册选取图片请求码
     */
//    private final int LOCAL_PHOTO = 1001;

    /**
     * 拍照请求码
     */
//    private final int CAMERA_PHOTO = 1002;

    /**
     * 图片处理压缩
     */
//    private final int COMPRESS_PHOTO = 1003;

//    int size = 1;

    /**
     * 图片选择框
     */
//    private PhotoChooseDialog mPhotoChooseDialog;

    /**
     * 选择框监听事件
     */
//    private View.OnClickListener dialogClickListener = new View.OnClickListener() {
//
//        @Override
//        public void onClick(View v) {
//            mPhotoChooseDialog.dismiss();
//            switch (v.getId()) {
//                case R.id.photo_choose_camera:
//                    cameraPhoto();
//                    break;
//                case R.id.photo_choose_local:
//                    Intent intent = new Intent(mContext,
//                            PicturePickerActivity.class);
//                    int count = PicturePickerActivity.MAX_SIZE - size;
//                    intent.putExtra(PicturePickerActivity.EXTRA_SELECT_COUNT, count);
//                    startActivityForResult(intent,
//                            LOCAL_PHOTO);
////                    selectPhoto();
//                    break;
//                case R.id.photo_choose_cancel:
//                    break;
//                default:
//                    break;
//            }
//        }
//    };

    @BindView(R.id.add_to_knowledge_box)
    RadioGroup radioGroup;
    //    @BindView(R.id.yes_box)
//    RadioButton mYes;
//    @BindView(R.id.no_box)
//    RadioButton mNo;
    private String title, description, production, reason, solution, priority, questionType;
    private String service = "";
    private String subService = "";
    private String relateEvent = "";
    private String relateChange = "";
    private String team;
    private String dealMan;
    //默认为是
    private String addTo = "option1";

    private boolean isEmptyAdd;

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
            if (detailBean != null) {
                isEmptyAdd = false;
            } else {
                isEmptyAdd = true;
                detailBean = new EventDetailBean();
            }
        }
    }

    private void _bindListener() {
        //监听复选框
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.yes_box:
                        addTo = "option1";
                        break;
                    case R.id.no_box:
                        addTo = "no";
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_question;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "新建问题", 0, false, true);
        // 选择相机还是本地
//        mPhotoChooseDialog = new PhotoChooseDialog(mContext,
//                dialogClickListener);
//        //默认添加一张图片
//        imagePathList.clear();
//        imagePathList.add(RepairImagesAdapter.UPLOAD_PICTURE_ADD);
//        //TODO
//        initUploadPathList(imagePathList);
//        mAdapter = new RepairImagesAdapter(imagePathList, mContext);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mImagesRecyclerView.setLayoutManager(linearLayoutManager);
//        mImagesRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View itemView, Object item, int pos) {
//                String bean = imagePathList.get(pos);
//                //添加图片
//                if (RepairImagesAdapter.UPLOAD_PICTURE_ADD.equals(bean)) {
//                    if (size > RepairImagesAdapter.UPLOAD_PICTURE_MAXSIZE) {
//                        showToast("最多只能上传9张照片");
//                        return;
//                    }
//                    if (null != mPhotoChooseDialog) {
//                        mPhotoChooseDialog.showDialog();
//                    }
//                    return;
//                }
//
//                if (uploadImageList != null
//                        && uploadImageList.size() > pos) {
//                    String bean1 = uploadImageList.get(pos);
//                    if (!TextUtils.isEmpty(bean1)) {
//                        File file = new File(bean1);
//                        if (file.exists() || null != Uri.parse(bean1)) {
//                            Intent intent = new Intent(mContext,
//                                    ImagesLocalActivity.class);
//                            intent.putExtra("isDelete", true);
//                            intent.putExtra("index", pos);
//                            intent.putExtra("imageUrlList", uploadImageList);
//                            startActivityForResult(intent, 0);
//                        }
//                    }
//                }
//            }
//        });

        mOrganizationText.setText(RWETApplication.getInstance().mLoginUser.orgName);
        mSponText.setText(RWETApplication.getInstance().mLoginUser.sponsor);
        if (isEmptyAdd) {
            mServiceText.setVisibility(View.GONE);
            mServicePop.setVisibility(View.VISIBLE);
            mSubServiceText.setVisibility(View.GONE);
            mSubServicePop.setVisibility(View.VISIBLE);
            mRelateEventText.setVisibility(View.GONE);
            mRelateEventPop.setVisibility(View.VISIBLE);
        } else {
            mServiceText.setVisibility(View.VISIBLE);
            mServiceText.setText(detailBean.serviceType);
            mServicePop.setVisibility(View.GONE);

            mSubServiceText.setVisibility(View.VISIBLE);
            mSubServiceText.setText(detailBean.serviceSubType);
            mSubServicePop.setVisibility(View.GONE);

            mRelateEventText.setVisibility(View.VISIBLE);
            mRelateEventText.setText(detailBean.faultType);
            mRelateEventPop.setVisibility(View.GONE);
        }

        hideSoftInputFromWindow(mTitleEdit);
        hideSoftInputFromWindow(mProductionEdit);
        hideSoftInputFromWindow(mDescriptionEdit);
        hideSoftInputFromWindow(mReasonEdit);
        hideSoftInputFromWindow(mResolventEdit);

        mDescriptionEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.new_question_description_edit && canVerticalScroll(mDescriptionEdit))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });
        mReasonEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.new_question_reason_edit && canVerticalScroll(mReasonEdit))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });
        mResolventEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.new_question_resolvent_edit && canVerticalScroll(mResolventEdit))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });


//        Collections.addAll(videoSuffixList, VIDEO_SUFFIX_ARRAY);
//
//        mAttachAdapter = new AttachAdapter(attachList, mContext);
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
//        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mAttachRecyclerView.setLayoutManager(linearLayoutManager1);
//        mAttachRecyclerView.setAdapter(mAttachAdapter);
    }

    /**
     * 封装上传的头像list
     */
//    private void initUploadPathList(ArrayList<String> imagePathList) {
//        ArrayList<String> newList = new ArrayList<>();
//        newList.addAll(imagePathList);
//        if (!ListUtil.isEmpty(newList)
//                && newList.contains(RepairImagesAdapter.UPLOAD_PICTURE_ADD)) {
//            newList.remove(RepairImagesAdapter.UPLOAD_PICTURE_ADD);
//        }
//        uploadImageList.clear();
//        uploadImageList.addAll(newList);
//    }


    /**
     * 相机拍照图片
     */
//    private void cameraPhoto() {
//        if (!Util.isHasSDcard()) {
//            showToast("");
//            return;
//        }
//        File file = Util.makeDirs(GlobalConstant.IMAGE_CACHE_DIR);
//        PHOTO_FILE = new File(file, System.currentTimeMillis() + ".jpg");
//        PHOTO_URI = Uri.fromFile(PHOTO_FILE);
//        Intent intent = new Intent(
//                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//        // 设置系统相机拍照后文件的输出位置
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, PHOTO_URI);
//        startActivityForResult(intent, CAMERA_PHOTO);
//    }

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

    @OnClick({/*R.id.new_question_organization_pop, R.id.new_question_sponsor_pop,*/
            /*R.id.new_question_source_pop,*/ R.id.new_question_service_pop, R.id.new_question_subService_pop,
            R.id.new_question_urgency_level_pop, R.id.new_question_type_pop,
            R.id.new_question_relate_event_pop, R.id.new_question_relate_change_pop,
            R.id.new_question_team_pop, R.id.new_question_deal_man_pop,
            R.id.new_question_select_file_text, R.id.new_question_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.new_question_organization_pop:
//                RepairPullDownPop mPop = getPop(R.array.organization,
//                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mOrgPos = position;
//                                mOrganizationText.setText(content);
//                            }
//                        });
//                if (ListUtil.isEmpty(orgList)) {
//                    showToast("获取组织列表失败");
//                    return;
//                }
//                RepairPullDownPop mPop = new RepairPullDownPop(mContext, orgList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mOrgPos = position;
//                                mOrganizationText.setText(content);
//                                mPresenter.getDataByOrgName(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.new_question_organization_pop));
//                mPop.setSelectedPosition(mOrgPos);
//                break;
//            case R.id.new_question_sponsor_pop:
//                if (ListUtil.isEmpty(sponList)) {
//                    showToast("获取发起人列表失败");
//                    return;
//                }
//                mPop = new RepairPullDownPop(mContext, sponList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mOrgPos = position;
//                                mSponText.setText(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.new_question_sponsor_pop));
////                mPop.setSelectedPosition(mSponPos);
//                break;
//            case R.id.new_question_source_pop:
//                if (ListUtil.isEmpty(sourceList)) {
//                    showToast("获取来源列表失败");
//                    return;
//                }
//                mPop = new RepairPullDownPop(mContext, sourceList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//                            public void getChoiceItem(int position, String content) {
////                                mSoPos = position;
//                                mSoText.setText(content);
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.new_question_source_pop));
////                mPop.setSelectedPosition(mSoPos);
//                break;
            case R.id.new_question_service_pop:
                if (ListUtil.isEmpty(serviceList)) {
                    showToast("服务列表为空");
                    return;
                }
                RepairPullDownPop mPop = new RepairPullDownPop(mContext, serviceList,
                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mSoPos = position;
                                service = content;
                                if (TextUtils.isEmpty(content))
                                    mServicePopText.setText("请选择");
                                else
                                    mServicePopText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_service_pop));
//                mPop.setSelectedPosition(mSoPos);
                break;
            case R.id.new_question_subService_pop:
                if (ListUtil.isEmpty(subServiceList)) {
                    showToast("服务子项列表为空");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, subServiceList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mSoPos = position;
                                subService = content;
                                if (TextUtils.isEmpty(content))
                                    mSubServicePopText.setText("请选择");
                                else
                                    mSubServicePopText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_subService_pop));
//                mPop.setSelectedPosition(mSoPos);
                break;
            case R.id.new_question_urgency_level_pop:
                mPop = getPop(R.array.urgency_level,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mUrgPos = position;
                                priority = content;
                                if (TextUtils.isEmpty(content))
                                    mUrgencyText.setText("请选择");
                                else
                                    mUrgencyText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_urgency_level_pop));
//                mPop.setSelectedPosition(mUrgPos);
                break;
            case R.id.new_question_type_pop:
                if (ListUtil.isEmpty(quTyList)) {
                    showToast("获取问题分类列表失败");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, quTyList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mQuTyPos = position;
                                questionType = content;
                                if (TextUtils.isEmpty(content))
                                    mQuTyText.setText("请选择");
                                else
                                    mQuTyText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_type_pop));
//                mPop.setSelectedPosition(mQuTyPos);
                break;
            case R.id.new_question_relate_event_pop:
                if (ListUtil.isEmpty(reEventList)) {
                    showToast("关联事件列表为空");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, reEventList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mQuTyPos = position;
                                relateEvent = content;
                                if (TextUtils.isEmpty(content))
                                    mRelateEventPopText.setText("请选择");
                                else
                                    mRelateEventPopText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_relate_event_pop));
//                mPop.setSelectedPosition(mQuTyPos);
                break;
            case R.id.new_question_relate_change_pop:
                if (ListUtil.isEmpty(reChangeList)) {
                    showToast("关联变更列表为空");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, reChangeList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mQuTyPos = position;
                                relateChange = content;
                                if (TextUtils.isEmpty(content))
                                    mRelateChangeText.setText("请选择");
                                else
                                    mRelateChangeText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_relate_change_pop));
//                mPop.setSelectedPosition(mQuTyPos);
                break;
            case R.id.new_question_team_pop:
                if (ListUtil.isEmpty(teamList)) {
                    showToast("团队列表为空");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, teamList,
                        (int) getResources().getDimension(R.dimen._repair_pop_large_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mQuTyPos = position;
                                team = content;
                                dealMan = "";
                                mDealManText.setText("请选择");
                                if (TextUtils.isEmpty(content)) {
                                    mTeamText.setText("请选择");
                                    dealManList.clear();
                                } else {
                                    mTeamText.setText(content);
                                    mPresenter.getDealManByTeam(content);
                                }
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_team_pop));
//                mPop.setSelectedPosition(mQuTyPos);
                break;
            case R.id.new_question_deal_man_pop:
                if (ListUtil.isEmpty(dealManList)) {
                    showToast("当前团队下办理人列表为空");
                    return;
                }
                mPop = new RepairPullDownPop(mContext, dealManList,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mQuTyPos = position;
                                dealMan = content;
                                if (TextUtils.isEmpty(content))
                                    mDealManText.setText("请选择");
                                else
                                    mDealManText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_question_deal_man_pop));
//                mPop.setSelectedPosition(mQuTyPos);
                break;

            case R.id.new_question_select_file_text:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(NewQuestionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 10);
                    } else {
                        //上传附件
                        selectFile();
                    }
                } else {
                    //上传附件
                    selectFile();
                }
                break;
            case R.id.new_question_confirm:
                //发送新建请求
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

    private void checkNull() {
        title = mTitleEdit.getText().toString().trim();
        description = mDescriptionEdit.getText().toString().trim();
        production = mProductionEdit.getText().toString().trim();
        reason = mReasonEdit.getText().toString().trim();
        solution = mResolventEdit.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            showToast("请输入描述");
            return;
        }
        if (TextUtils.isEmpty(reason)) {
            showToast("请输入原因");
            return;
        }
        if (TextUtils.isEmpty(solution)) {
            showToast("请输入解决方案");
            return;
        }
        if (TextUtils.isEmpty(priority)) {
            showToast("请选择紧急程度");
            return;
        }
        if (TextUtils.isEmpty(questionType)) {
            showToast("请选择问题分类");
            return;
        }
        if (TextUtils.isEmpty(team)) {
            showToast("请选择团队");
            return;
        }
        if (TextUtils.isEmpty(dealMan)) {
            showToast("请选择办理人");
            return;
        }
        mPresenter.checkTitle(title);
    }

    @Override
    protected void updateViews() {
        //该方法用于获得办理人团队列表
        mPresenter.getOgList();
        mPresenter.getDataByOrgName(RWETApplication.getInstance().mLoginUser.orgName);
    }

    @Override
    protected void initInjector() {
        DaggerNewQuestionComponent.builder().applicationComponent(
                getAppComponent()).newQuestionModule(new NewQuestionModule(this, mContext)).
                build().inject(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public void checkTitle(String result) {
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

    private void publish(String attach) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("pubType", "新建");
        params.put("orgName", RWETApplication.getInstance().mLoginUser.orgName);
        params.put("spon", RWETApplication.getInstance().mLoginUser.sponsor);
        params.put("createBy", RWETApplication.getInstance().mLoginUser.sponsor);
        params.put("source", "客户");
        params.put("state", "新建");
        params.put("product", production);
        params.put("title", title);
        params.put("urgentLevel", priority);
        params.put("problemType", questionType);
        params.put("description", description);
        params.put("reason", reason);
        params.put("solution", solution);
        if (isEmptyAdd) {
            params.put("service", service);
            params.put("serviceSubkey", subService);
            params.put("relatedEvent", relateEvent);
        } else {
            params.put("service", TextUtils.isEmpty(detailBean.serviceType) ? "" : detailBean.serviceType);
            params.put("serviceSubkey", TextUtils.isEmpty(detailBean.serviceSubType) ? "" : detailBean.serviceSubType);
            params.put("relatedEvent", TextUtils.isEmpty(detailBean.faultType) ? "" : detailBean.faultType);
        }
        params.put("relatedChange", relateChange);
        params.put("team", team);
        params.put("appointPerson", dealMan);
        if (attach.contains("\\")) {
            attach = attach.replace("\\", "/");
        }
        params.put("annexPath", attach);
        params.put("radioInline", addTo);

        mPresenter.newVarious(params);
    }


    @Override
    public void uploadAttachFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void uploadAttachSuccess(String filePath) {
        publish(filePath);
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
    public void getServiceList(ArrayList<String> list) {
        serviceList.clear();
        if (!ListUtil.isEmpty(list)) {
            serviceList.addAll(list);
        }
    }

    @Override
    public void getSubServiceList(ArrayList<String> list) {
        subServiceList.clear();
        if (!ListUtil.isEmpty(list)) {
            subServiceList.addAll(list);
        }
    }

    @Override
    public void getQuesTypeList(ArrayList<String> list) {
        quTyList.clear();
        if (!ListUtil.isEmpty(list)) {
            quTyList.addAll(list);
        }
    }

    @Override
    public void getRelateEventList(ArrayList<String> list) {
        reEventList.clear();
        if (!ListUtil.isEmpty(list)) {
            reEventList.addAll(list);
        }
    }

    @Override
    public void getRelateChangeList(ArrayList<String> list) {
        reChangeList.clear();
        if (!ListUtil.isEmpty(list)) {
            reChangeList.addAll(list);
        }
    }

    @Override
    public void getTeamList(ArrayList<String> list) {
        teamList.clear();
        if (!ListUtil.isEmpty(list)) {
            teamList.addAll(list);
        }
    }

    @Override
    public void getDealManList(ArrayList<String> list) {
        dealManList.clear();
        if (!ListUtil.isEmpty(list)) {
            dealManList.addAll(list);
        }
    }
}
