package com.qxmagic.railwayuserterminal.ui.event.repair;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.injector.components.DaggerRepairImagesComponent;
import com.qxmagic.railwayuserterminal.injector.modules.RepairImagesModule;
import com.qxmagic.railwayuserterminal.logic.global.GlobalConstant;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairImagesPresenter;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.event.adapter.RepairImagesAdapter;
import com.qxmagic.railwayuserterminal.ui.event.repair.view.PhotoChooseDialog;
import com.qxmagic.railwayuserterminal.ui.iview.IRepairImagesView;
import com.qxmagic.railwayuserterminal.ui.photo.CompressPhotoActivity;
import com.qxmagic.railwayuserterminal.ui.photo.ImagesLocalActivity;
import com.qxmagic.railwayuserterminal.ui.photo.PicturePickerActivity;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.utils.Util;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class RepairImagesActivity extends BaseActivity<IRepairImagesPresenter> implements IRepairImagesView {
    @BindView(R.id.repair_images_recyclerView)
    RecyclerView mRecyclerView;
    private RepairImagesAdapter mAdapter;
//    private ArrayList<ImageBean> mList = new ArrayList<>();

    /**
     * 上传图片的id集合
     */
    private ArrayList<String> imageIdList = new ArrayList<>();

    /**
     * 适配器中的path集合
     */
    private ArrayList<String> imagePathList = new ArrayList<>();

    /**
     * 上传图片的path集合
     */
    private ArrayList<String> uploadImageList = new ArrayList<>();

    /**
     * 图片 类型
     */
    private static final String TYPE_PHOTO = "image/*";

    /**
     * 图片PATH
     */
    private String picPath = "";

    /**
     * 调用系统相机 指定输出文件
     */
    public static File PHOTO_FILE;

    /**
     * 调用系统相机 指定输出文件的 Uri
     */
    private static Uri PHOTO_URI;

    /**
     * 相册选取图片请求码
     */
    private final int LOCAL_PHOTO = 1001;

    /**
     * 拍照请求码
     */
    private final int CAMERA_PHOTO = 1002;

    /**
     * 图片处理压缩
     */
    private final int COMPRESS_PHOTO = 1003;

    int size = 1;

    /**
     * 图片选择框
     */
    private PhotoChooseDialog mPhotoChooseDialog;

    /**
     * 选择框监听事件
     */
    private View.OnClickListener dialogClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            mPhotoChooseDialog.dismiss();
            switch (v.getId()) {
                case R.id.photo_choose_camera:
                    if (ContextCompat.checkSelfPermission(RepairImagesActivity.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RepairImagesActivity.this, new String[]{Manifest.permission.CAMERA},
                                10);
                    } else {
                        cameraPhoto();
                    }
                    break;
                case R.id.photo_choose_local:
                    if (ContextCompat.checkSelfPermission(RepairImagesActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RepairImagesActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                11);
                    } else {
                        Intent intent = new Intent(mContext,
                                PicturePickerActivity.class);
                        int count = PicturePickerActivity.MAX_SIZE - size;
                        intent.putExtra(PicturePickerActivity.EXTRA_SELECT_COUNT, count);
                        startActivityForResult(intent,
                                LOCAL_PHOTO);
                    }
                    break;
                case R.id.photo_choose_cancel:
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO 这边的监听看看怎么做，是添加图片还是提交图片  暂且写为提交
            if (ListUtil.isEmpty(uploadImageList)) {
                showToast("请选择需要添加的图片");
            } else {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imagesList", uploadImageList);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
//                showToast("图片正在上传中...");
//                mPresenter.uploadRepairImages(uploadImageList);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        initViews();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_repair_images;
    }

    @Override
    protected void initViews() {
        CommonTitleUtil.initCommonTitle(this, "故障图片", "提交", false, false, listener);
        // 选择相机还是本地
        mPhotoChooseDialog = new PhotoChooseDialog(mContext,
                dialogClickListener);
        //默认添加一张图片
        imagePathList.clear();
        imagePathList.add(RepairImagesAdapter.UPLOAD_PICTURE_ADD);
        if (!ListUtil.isEmpty(getIntent().getStringArrayListExtra("imagesList"))) {
            imagePathList.addAll(imagePathList.size() - 1, getIntent().getStringArrayListExtra("imagesList"));
        }
        //TODO
        initUploadPathList(imagePathList);
        mAdapter = new RepairImagesAdapter(imagePathList, mContext);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, Object item, int pos) {
                String bean = imagePathList.get(pos);
                //添加图片
                if (RepairImagesAdapter.UPLOAD_PICTURE_ADD.equals(bean)) {
                    if (size > RepairImagesAdapter.UPLOAD_PICTURE_MAXSIZE) {
                        showToast("最多只能上传6张照片");
                        return;
                    }
                    if (null != mPhotoChooseDialog) {
                        mPhotoChooseDialog.showDialog();
                    }
                    return;
                }

                if (uploadImageList != null
                        && uploadImageList.size() > pos) {
                    String bean1 = uploadImageList.get(pos);
                    if (!TextUtils.isEmpty(bean1)) {
                        File file = new File(bean1);
                        if (file.exists() || null != Uri.parse(bean1)) {
                            Intent intent = new Intent(mContext,
                                    ImagesLocalActivity.class);
                            intent.putExtra("isDelete", true);
                            intent.putExtra("index", pos);
                            intent.putExtra("imageUrlList", uploadImageList);
                            startActivityForResult(intent, 0);
                        }
                    }
                }
            }
        });
    }

    /**
     * 封装上传的头像list
     */
    private void initUploadPathList(ArrayList<String> imagePathList) {
        ArrayList<String> newList = new ArrayList<>();
        newList.addAll(imagePathList);
        if (!ListUtil.isEmpty(newList)
                && newList.contains(RepairImagesAdapter.UPLOAD_PICTURE_ADD)) {
            newList.remove(RepairImagesAdapter.UPLOAD_PICTURE_ADD);
        }
        uploadImageList.clear();
        uploadImageList.addAll(newList);
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {
        DaggerRepairImagesComponent.builder().applicationComponent(
                getAppComponent()).repairImagesModule(new RepairImagesModule(this)).
                build().inject(this);
    }


    /**
     * 相机拍照图片
     */
    private void cameraPhoto() {
        if (!Util.isHasSDcard()) {
            showToast("无存储卡，暂时使用该功能");
            return;
        }
        File file = Util.makeDirs(GlobalConstant.IMAGE_CACHE_DIR);
        PHOTO_FILE = new File(file, System.currentTimeMillis() + ".jpg");
        if (Build.VERSION.SDK_INT >= 24) {
            PHOTO_URI = FileProvider.getUriForFile(mContext, "com.qxmagic.railwayuserterminal.fileprovider", PHOTO_FILE);
        } else {
            PHOTO_URI = Uri.fromFile(PHOTO_FILE);
        }
        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        // 设置系统相机拍照后文件的输出位置
        intent.putExtra(MediaStore.EXTRA_OUTPUT, PHOTO_URI);
        startActivityForResult(intent, CAMERA_PHOTO);
    }

    /**
     * 调用系统相册
     */
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, LOCAL_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        Intent intent;
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_PHOTO) {
                bundle = new Bundle();
                bundle.putString("path", PHOTO_FILE.getAbsolutePath());
                bundle.putString("uri", PHOTO_URI.toString());
                intent = new Intent(mContext, CompressPhotoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, COMPRESS_PHOTO);
//                if (!TextUtils.isEmpty(PHOTO_FILE.getAbsolutePath())) {
//                    ImageBean bean = new ImageBean();
//                    bean.imageUrl = PHOTO_FILE.getAbsolutePath();
//                    mList.add(bean);
//                    mAdapter.setList(mList);
//                }
            } else if (requestCode == LOCAL_PHOTO) {
                // 获取系统相册图片的uri
                if (null != data) {
                    bundle = data.getExtras();
                    if (null != bundle) {
                        ArrayList<String> imagesList = data.getStringArrayListExtra(PicturePickerActivity.EXTRA_RESULT);
                        if (!ListUtil.isEmpty(imagesList)) {
                            imagePathList.addAll(imagePathList.size() - 1, imagesList);
                            size = imagePathList.size();
                        }
                    }
//                    ArrayList<String> imagesList = data.getStringArrayListExtra(PicturePickerActivity.EXTRA_RESULT);
//                    if (!ListUtil.isEmpty(imagesList)) {
//                        ArrayList<ImageBean> list = new ArrayList<>();
//                        for (String url : imagesList) {
//                            ImageBean bean = new ImageBean();
//                            bean.imageUrl = url;
//                            list.add(bean);
//                        }
//                        imagePathList.addAll(imagePathList.size() - 1, list);
//                        size = imagePathList.size();
//                    }
                    Uri originalUri = data.getData();
                    if (null != originalUri) {
                        imagePathList.add(imagePathList.size() - 1, originalUri.toString());
                        size = imagePathList.size();
                    }
                    mAdapter.setList(imagePathList);
                    initUploadPathList(imagePathList);
                }
            } else if (requestCode == COMPRESS_PHOTO) {
                if (null != data) {
                    picPath = data.getStringExtra("picPath");
                    if (picPath != null) {
                        imagePathList.add(imagePathList.size() - 1, picPath);
                        size = imagePathList.size();
//                        if (!ListUtil.isEmpty(imagePathList)
//                                && imagePathList.size() > RepairImagesAdapter.UPLOAD_PICTURE_MAXSIZE) {
//                            imagePathList.remove(imagePathList.size() - 1);
//                        }
                        mAdapter.setList(imagePathList);
                        initUploadPathList(imagePathList);
                    }
                }
            }
        } else if (resultCode == 2000) {
            if (requestCode == 0 && null != data) {
                int index = data.getIntExtra("position", Integer.MAX_VALUE);
                if (!ListUtil.isEmpty(imagePathList) && imagePathList.size() > index) {
                    imagePathList.remove(index);
                }
                size = imagePathList.size();
                mAdapter.setList(imagePathList);
                initUploadPathList(imagePathList);
                String bean = data.getStringExtra("bean");
                if (!TextUtils.isEmpty(bean) && new File(bean).exists()) {
                    boolean isDelete = new File(bean).delete();
                    Logger.i("是否删除成功：" + isDelete);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPhoto();
            } else {
                Toast.makeText(this, "未获得拍照权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 11) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectPhoto();
            } else {
                Toast.makeText(this, "未获得文件读取权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void imagesUploadSuccess(String result) {
        showToast(result);
        finish();
    }
}
