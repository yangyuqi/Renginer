package com.qxmagic.railwayengineerternimal.ui.receptiondesk.event.change;

import android.Manifest;
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
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.injector.components.DaggerNewChangeComponent;
import com.qxmagic.railwayengineerternimal.injector.modules.NewChangeModule;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.ui.BaseActivity;
import com.qxmagic.railwayengineerternimal.ui.iview.INewChangeView;
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
 * 新建变更界面
 */

public class NewChangeActivity extends BaseActivity<INewVariousPresenter> implements INewChangeView {
    //    private int mOrgPos, mPaChPos;
    @BindView(R.id.new_change_organization_text)
    TextView mOrganizationText;
    @BindView(R.id.new_change_sponsor_text)
    TextView mSponsorText;
    @BindView(R.id.new_change_state_text)
    TextView mRelateEvent;

    @BindView(R.id.new_change_type_text)
    TextView mChTypeText;
    @BindView(R.id.new_change_parent_change_text)
    TextView mPaChText;
    @BindView(R.id.new_change_approve_text)
    TextView mApproveText;

    @BindView(R.id.new_change_title_edit)
    EditText mTitleEdit;
    @BindView(R.id.new_change_description_edit)
    EditText mDescriptionEdit;

//    @BindView(R.id.new_change_images_recyclerView)
//    RecyclerView mImagesRecyclerView;
//    private RepairImagesAdapter mAdapter;
//
//    @BindView(R.id.new_change_attach_recyclerView)
//    RecyclerView mAttachRecyclerView;

    @BindView(R.id.new_publish_attach_name)
    TextView mAttachName;

    /**
     * 视频文件后缀名数组
     */
//    public static String[] VIDEO_SUFFIX_ARRAY = {"wmv", "avi", "dat", "asf",
//            "rm", "rmvb", "ram", "mpg", "mpeg", "mp4", "mov", "m4v", "mkv",
//            "flv", "vob", "qt", "divx", "cpk", "fli", "flc", "mod", "dvix",
//            "dv", "ts", "3gp"};

    /**
     * 视频文件后缀名集合
     */
//    private ArrayList<String> videoSuffixList = new ArrayList<>();
//
//    private ArrayList<String> attachList = new ArrayList<>();
//
//    private AttachAdapter mAttachAdapter;

    /**
     * 上传图片的id集合
     */
//    private ArrayList<String> imageIdList = new ArrayList<>();

    /**
     * 适配器中的path集合
     */
//    private ArrayList<String> imagePathList = new ArrayList<>();

    /**
     * 上传图片的path集合
     */
//    private ArrayList<String> uploadImageList = new ArrayList<>();

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

    private EventDetailBean detailBean;

    //    private ArrayList<String> parentChangeList=new ArrayList<>();
    private ArrayList<String> approveList = new ArrayList<>();

    private String title, description;

    private String changeType;
    //    private String parentChange;
    private String approve;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dealIntent();
        initInjector();
        initViews();
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


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_change;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "新建变更", 0, false, true);
        // 选择相机还是本地
//        mPhotoChooseDialog = new PhotoChooseDialog(mContext,
//                dialogClickListener);
//        //默认添加一张图片
//        imagePathList.clear();
//        imagePathList.add(RepairImagesAdapter.UPLOAD_PICTURE_ADD);
//        initUploadPathList(imagePathList);
//        mAdapter = new RepairImagesAdapter(imagePathList, mContext);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        mImagesRecyclerView.setLayoutManager(linearLayoutManager);
////        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
////        mImagesRecyclerView.setLayoutManager(manager);
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
        mSponsorText.setText(RWETApplication.getInstance().mLoginUser.sponsor);
        mRelateEvent.setText(detailBean.faultType);
        mPaChText.setText(detailBean.parentChange);
        hideSoftInputFromWindow(mTitleEdit);
        hideSoftInputFromWindow(mDescriptionEdit);
        mDescriptionEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.new_change_description_edit && canVerticalScroll(mDescriptionEdit))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;

            }
        });
//        Collections.addAll(videoSuffixList, VIDEO_SUFFIX_ARRAY);

//        mAttachAdapter=new AttachAdapter(attachList,mContext);
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
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     * 调用系统文件
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
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
//                MediaStore.ACTION_IMAGE_CAPTURE);
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

    @OnClick({R.id.new_change_type_pop,/* R.id.new_change_parent_change_pop,*/R.id.new_change_approve_pop,
            R.id.new_change_select_file_text, R.id.new_change_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_change_type_pop:
                RepairPullDownPop mPop = getPop(R.array.new_change_type,
                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
                        new PopWindowChoiceItemCallback() {
                            @Override
                            public void getChoiceItem(int position, String content) {
//                                mOrgPos = position;
                                changeType = content;
                                if (TextUtils.isEmpty(content))
                                    mChTypeText.setText("请选择");
                                else
                                    mChTypeText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_change_type_pop));
//                mPop.setSelectedPosition(mOrgPos);
                break;
//            case R.id.new_change_parent_change_pop:
//                mPop = new RepairPullDownPop(mContext, parentChangeList,
//                        (int) getResources().getDimension(R.dimen._repair_pop_small_width),
//                        new PopWindowChoiceItemCallback() {
//                            @Override
//
//                            public void getChoiceItem(int position, String content) {
////                                mPaChPos = position;
//                                mPaChText.setText(content);
//                                parentChange=content;
//                            }
//                        });
//                mPop.showAsDropDown(findViewById(R.id.new_change_parent_change_pop));
////                mPop.setSelectedPosition(mPaChPos);
//                break;
            case R.id.new_change_approve_pop:
                mPop = new RepairPullDownPop(mContext, approveList,
                        (int) getResources().getDimension(R.dimen._repair_pop_middle_width),
                        new PopWindowChoiceItemCallback() {
                            @Override

                            public void getChoiceItem(int position, String content) {
//                                mPaChPos = position;
                                approve = content;
                                if (TextUtils.isEmpty(content))
                                    mApproveText.setText("请选择");
                                else
                                    mApproveText.setText(content);
                            }
                        });
                mPop.showAsDropDown(findViewById(R.id.new_change_approve_pop));
//                mPop.setSelectedPosition(mPaChPos);
                break;
            case R.id.new_change_select_file_text:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(NewChangeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
            case R.id.new_change_confirm:
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

    @Override
    protected void updateViews() {
        mPresenter.getData();
    }

    @Override
    protected void initInjector() {
        DaggerNewChangeComponent.builder().applicationComponent(
                getAppComponent()).newChangeModule(new NewChangeModule(this, mContext)).
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

    private void checkNull() {
        title = mTitleEdit.getText().toString().trim();
        description = mDescriptionEdit.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("请选择标题");
            return;
        }
        if (TextUtils.isEmpty(description)) {
            showToast("请选择内容");
            return;
        }
        if (TextUtils.isEmpty(approve)) {
            showToast("请选择审批人");
            return;
        }
        mPresenter.checkTitle(title);
    }

    private void publish(String attach) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("state", "新建");
        params.put("changeType", changeType);
        params.put("orgName", RWETApplication.getInstance().mLoginUser.orgName);
        params.put("title", title);
        params.put("description", description);
        params.put("relatedEvent", TextUtils.isEmpty(detailBean.faultType) ? "" : detailBean.faultType);
        params.put("parentChange", detailBean.hotReason == null ? "" : detailBean.hotReason);
        params.put("sponsor", RWETApplication.getInstance().mLoginUser.sponsor);
        if (attach.contains("\\")) {
            attach = attach.replace("\\", "/");
        }
        params.put("annexPath", attach);
        params.put("approver", approve);
        mPresenter.newVarious(params);
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
    public void getApproveList(ArrayList<String> list) {
        approveList.clear();
        if (!ListUtil.isEmpty(list)) {
            approveList.addAll(list);
        }
    }

    @Override
    public void getPaChangeList(ArrayList<String> list) {

    }
}
