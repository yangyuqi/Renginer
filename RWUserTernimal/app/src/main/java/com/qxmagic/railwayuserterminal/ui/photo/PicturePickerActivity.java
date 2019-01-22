package com.qxmagic.railwayuserterminal.ui.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.BaseActivity;
import com.qxmagic.railwayuserterminal.utils.CommonTitleUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * 多图选择
 * Created by Nereo on 2015/4/7.
 */
public class PicturePickerActivity extends BaseActivity implements
        PicturePickerFragment.Callback
{
    
    /** 单选 */
    public static final int MAX_SIZE = 7;
    
    /** 最大图片选择次数，int类型，默认9 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    
    /** 图片选择模式，默认多选 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    
    /** 是否显示相机，默认显示 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "list";
    
    /** 默认选择集 */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    
    /** 单选 */
    public static final int MODE_SINGLE = 0;
    
    /** 多选 */
    public static final int MODE_MULTI = 1;
    
    private ArrayList<String> resultList = new ArrayList<String>();
    
    private int mDefaultCount;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutResId());
        initViews();
        bindListener();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.yf_picture_picker_activity;
    }

    @Override
    public void onSingleImageSelected(String path)
    {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }
    
    @Override
    public void onImageSelected(String path)
    {
        if (!resultList.contains(path))
        {
            resultList.add(path);
        }
    }
    
    @Override
    public void onImageUnselected(String path)
    {
        if (resultList.contains(path))
        {
            resultList.remove(path);
        }
    }
    
    @Override
    public void onCameraShot(File imageFile)
    {
        if (imageFile != null)
        {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }
    
    @Override
    public void onConfirmClick(ArrayList<String> list)
    {
        Intent data = new Intent();
        data.putExtra("list", list);
        Bundle bundle=new Bundle();
        bundle.putSerializable(EXTRA_RESULT,list);
        data.putExtras(bundle);
//        data.putStringArrayListExtra(EXTRA_RESULT, list);
        setResult(RESULT_OK, data);
        finish();
    }
    
    @Override
    public void initViews()
    {
        CommonTitleUtil.initCommonTitle(this, "图片选择", 0, false, true);
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, MAX_SIZE);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, false);
        if (mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST))
        {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        
        Bundle bundle = new Bundle();
        bundle.putInt(PicturePickerFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(PicturePickerFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(PicturePickerFragment.EXTRA_SHOW_CAMERA, isShow);
        bundle.putStringArrayList(PicturePickerFragment.EXTRA_DEFAULT_SELECTED_LIST,
                resultList);
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid,
                        Fragment.instantiate(this,
                                PicturePickerFragment.class.getName(),
                                bundle))
                .commit();
    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    public void bindListener()
    {
    }
    


    public void initAdapter()
    {
    }
    
    public void getBundle()
    {
    }
}
