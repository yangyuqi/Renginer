package com.qxmagic.railwayengineerternimal.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.model.ImageBean;
import com.qxmagic.railwayengineerternimal.ui.photo.adapter.ImageGridAdapter;
import com.qxmagic.railwayengineerternimal.ui.photo.model.Image;
import com.qxmagic.railwayengineerternimal.utils.ListUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片选择Fragment
 * Created by Nereo on 2015/4/7.
 */
public class PicturePickerFragment extends Fragment implements OnClickListener
{
    
    /** 最大图片选择次数，int类型 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    
    /** 图片选择模式，int类型 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    
    /** 是否显示相机，boolean类型 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    
    /** 默认选择的数据集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_result";
    
    /** 单选 */
    public static final int MODE_SINGLE = 0;
    
    /** 多选 */
    public static final int MODE_MULTI = 1;
    
    // 不同loader定义
    private static final int LOADER_ALL = 0;
    
    private static final int LOADER_CATEGORY = 1;
    
    // 请求加载系统照相机
    private static final int REQUEST_CAMERA = 100;
    
    // 请求加载系统照相机
    private static final int IMAGE_PERVIEW = 101;
    
    // 结果数据
    private ArrayList<String> resultList = new ArrayList<>();
    
    // 结果数据
    private ArrayList<String> mImageList = new ArrayList<>();
    
    // 图片Grid
    private GridView mGridView;
    
    private Callback mCallback;
    
    private ImageGridAdapter mImageAdapter;
    
    // 时间线
    private TextView mTimeLineText;
    
    private int mDesireImageCount;
    
    private boolean mIsShowCamera = false;
    
    private int mode;
    
    private File mTmpFile;
    
    private TextView mPreviewText;
    
    private TextView mSelectNumText;
    
    private TextView mConfirmText;
    
    private Handler mHandler = new Handler()
    {
        
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            Image image = (Image) msg.obj;
            selectImageFromGrid(image, mode);
        }
        
    };
    
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mCallback = (Callback) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    "The Activity must implement MultiImageSelectorFragment.Callback interface...");
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.yf_picture_picker_fragment,
                container,
                false);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        // 选择图片数量
        mDesireImageCount = getArguments().getInt(EXTRA_SELECT_COUNT);
        
        // 图片选择模式
        mode = getArguments().getInt(EXTRA_SELECT_MODE);
        
        // 默认选择
        if (mode == MODE_MULTI)
        {
            ArrayList<String> tmp = getArguments().getStringArrayList(EXTRA_DEFAULT_SELECTED_LIST);
            if (tmp != null && tmp.size() > 0)
            {
                ArrayList<String>list=new ArrayList<>();
                for(String url:tmp){
                    list.add(url);
                }
                resultList = list;
            }
        }
        
        // 是否显示照相机
        mIsShowCamera = getArguments().getBoolean(EXTRA_SHOW_CAMERA, true);
        mImageAdapter = new ImageGridAdapter(getActivity(), mIsShowCamera);
        // 是否显示选择指示器
        mImageAdapter.showSelectIndicator(mode == MODE_MULTI);
        mImageAdapter.setHandler(mHandler);
        
        mTimeLineText = (TextView) view.findViewById(R.id.timeline_area);
        // 初始化，先隐藏当前timeline
        mTimeLineText.setVisibility(View.GONE);
        
        mPreviewText = (TextView) view.findViewById(R.id.picker_image_preview);
        mSelectNumText = (TextView) view.findViewById(R.id.picker_image_select_num);
        mConfirmText = (TextView) view.findViewById(R.id.picker_confirm);
        
        mGridView = (GridView) view.findViewById(R.id.grid);
        //显示日期
//        mGridView.setOnScrollListener(new PauseOnScrollListener(
//                ImageLoader.getInstance(), true, false,
//                new AbsListView.OnScrollListener()
//                {
//
//                    @Override
//                    public void onScrollStateChanged(AbsListView view, int state)
//                    {
//                        if (state == SCROLL_STATE_IDLE)
//                        {
//                            // 停止滑动，日期指示器消失
//                            mTimeLineText.setVisibility(View.GONE);
//                        }
//                        else if (state == SCROLL_STATE_FLING)
//                        {
//                            mTimeLineText.setVisibility(View.VISIBLE);
//                        }
//                    }
//
//                    @Override
//                    public void onScroll(AbsListView view,
//                            int firstVisibleItem, int visibleItemCount,
//                            int totalItemCount)
//                    {
//                        if (mTimeLineText.getVisibility() == View.VISIBLE)
//                        {
//                            int index = firstVisibleItem + 1 == view.getAdapter()
//                                    .getCount() ? view.getAdapter().getCount() - 1
//                                    : firstVisibleItem + 1;
//                            Image image = (Image) view.getAdapter()
//                                    .getItem(index);
//                            if (image != null)
//                            {
//                                mTimeLineText.setText(DateTools.formatPhotoDate(image.path));
//                            }
//                        }
//                    }
//                }));
        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                    int i, long l)
            {
                if (mImageAdapter.isShowCamera())
                {
                    // 如果显示照相机，则第一个Grid显示为照相机，处理特殊逻辑
                    //                    if (i == 0)
                    //                    {
                    //                        showCameraAction();
                    //                    }
                    //                    else
                    //                    {
                    //                        // 正常操作
                    //                        Intent intent = new Intent(getActivity(),
                    //                                ImagesLocalActivity.class);
                    //                        intent.putExtra("imageUrlList", mImageList);
                    //                        intent.putExtra("index", i);
                    //                        intent.putExtra("resultList", resultList);
                    //                        intent.putExtra("isDelete", true);
                    //                        intent.putExtra("showBottom", true);
                    //                        startActivityForResult(intent, IMAGE_PERVIEW);
                    //                    }
                }
                else
                {
                    // 正常操作
                    Intent intent = new Intent(getActivity(),
                            ImagesLocalActivity.class);
                    intent.putExtra("imageUrlList", mImageList);
                    intent.putExtra("index", i);
                    startActivity(intent);
                }
            }
        });
        
        mPreviewText.setOnClickListener(this);
        mConfirmText.setOnClickListener(this);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_ALL,
                null,
                mLoaderCallback);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // 相机拍照完成后，返回图片路径
        if (requestCode == REQUEST_CAMERA)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (mTmpFile != null)
                {
                    if (mCallback != null)
                    {
                        mCallback.onCameraShot(mTmpFile);
                    }
                }
            }
            else
            {
                if (mTmpFile != null && mTmpFile.exists())
                {
                    mTmpFile.delete();
                }
            }
        }
        else if (requestCode == IMAGE_PERVIEW)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                boolean send = data.getBooleanExtra("send", false);
                resultList = data.getStringArrayListExtra("list");
                mImageAdapter.setDefaultSelected(resultList);
                if (resultList.size() != 0)
                {
                    mSelectNumText.setText("已选择" + resultList.size() + "张图");
                    mPreviewText.setEnabled(true);
                    mConfirmText.setEnabled(true);
                    mConfirmText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                    mPreviewText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                }
                else
                {
                    mSelectNumText.setText("");
                    mPreviewText.setEnabled(false);
                    mConfirmText.setEnabled(false);
                    mPreviewText.setTextColor(getResources().getColor(R.color.gray_686868_color));
                    mConfirmText.setTextColor(getResources().getColor(R.color.gray_686868_color));
                }
                if (send)
                {
                    if (mCallback != null)
                    {
                        mCallback.onConfirmClick(resultList);
                    }
                }
            }
        }
    }
    
    /**
     * 选择相机
     */
    private void showCameraAction()
    {
        // 跳转到系统照相机
        //        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null)
        //        {
        //            // 设置系统相机拍照后的输出路径
        //            // 创建临时文件
        //            mTmpFile = FileUtils.createTmpFile(getActivity());
        //            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                    Uri.fromFile(mTmpFile));
        //            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        //        }
        //        else
        //        {
        //            Toast.makeText(getActivity(),
        //                    R.string.msg_no_camera,
        //                    Toast.LENGTH_SHORT).show();
        //        }
    }
    
    /**
     * 选择图片操作
     * @param image
     */
    private void selectImageFromGrid(Image image, int mode)
    {
        if (image != null)
        {
            // 多选模式
            if (mode == MODE_MULTI)
            {
                if (resultList.contains(image.path))
                {
                    resultList.remove(image.path);
                    if (resultList.size() != 0)
                    {
                        mSelectNumText.setText("已选择" + resultList.size() + "张图");
                        mPreviewText.setEnabled(true);
                        mConfirmText.setEnabled(true);
                        mConfirmText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                        mPreviewText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                    }
                    else
                    {
                        mSelectNumText.setText("");
                        mPreviewText.setEnabled(false);
                        mConfirmText.setEnabled(false);
                        mPreviewText.setTextColor(getResources().getColor(R.color.gray_686868_color));
                        mConfirmText.setTextColor(getResources().getColor(R.color.gray_686868_color));
                    }
                    if (mCallback != null)
                    {
                        mCallback.onImageUnselected(image.path);
                    }
                }
                else
                {
                    // 判断选择数量问题
                    if (mDesireImageCount == resultList.size())
                    {
                        Toast.makeText(getActivity(),
                                "最多只能选择9张图片",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    resultList.add(image.path);
                    mSelectNumText.setText("已选择" + resultList.size() + "张图");
                    mPreviewText.setEnabled(true);
                    mConfirmText.setEnabled(true);
                    mConfirmText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                    mPreviewText.setTextColor(getResources().getColor(R.color.green_35C384_color));
                    if (mCallback != null)
                    {
                        mCallback.onImageSelected(image.path);
                    }
                }
                mImageAdapter.select(image);
            }
            else if (mode == MODE_SINGLE)
            {
                // 单选模式
                if (mCallback != null)
                {
                    mCallback.onSingleImageSelected(image.path);
                }
            }
        }
    }
    
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>()
    {
        
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID };
        
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args)
        {
            if (id == LOADER_ALL)
            {
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[2]
                                + " DESC");
                return cursorLoader;
            }
            else if (id == LOADER_CATEGORY)
            {
                CursorLoader cursorLoader = new CursorLoader(getActivity(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION, IMAGE_PROJECTION[0] + " like '%"
                                + args.getString("path") + "%'", null,
                        IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }
            
            return null;
        }
        
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data)
        {
            if (data != null)
            {
                List<Image> images = new ArrayList<Image>();
                int count = data.getCount();
                if (count > 0)
                {
                    data.moveToFirst();
                    do
                    {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        Image image = new Image(path, name, dateTime);
                        mImageList.add(path);
                        images.add(image);
                    } while (data.moveToNext());
                    
                    mImageAdapter.setData(images);
                    
                    // 设定默认选择
                    if (resultList != null && resultList.size() > 0)
                    {
                        mImageAdapter.setDefaultSelected(resultList);
                    }
                }
            }
        }
        
        @Override
        public void onLoaderReset(Loader<Cursor> loader)
        {
            
        }
    };
    
    /**
     * 回调接口
     */
    public interface Callback
    {
        void onSingleImageSelected(String path);
        
        void onImageSelected(String path);
        
        void onImageUnselected(String path);
        
        void onCameraShot(File imageFile);
        
        void onConfirmClick(ArrayList<String> list);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.picker_image_preview:
                if (!ListUtil.isEmpty(resultList))
                {
                    Intent intent = new Intent(getActivity(),
                            ImagesLocalActivity.class);
                    intent.putExtra("imageUrlList", resultList);
                    intent.putExtra("resultList", resultList);
                    //                intent.putExtra("isDelete", true);
                    //                intent.putExtra("showBottom", true);
                    startActivityForResult(intent, IMAGE_PERVIEW);
                }
                break;
            case R.id.picker_confirm:
                if (mCallback != null)
                {
                    mCallback.onConfirmClick(resultList);
                }
                break;
            default:
                break;
        }
    }
}
