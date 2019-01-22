package com.qxmagic.railwayuserterminal.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.model.CommonDetailBean;
import com.qxmagic.railwayuserterminal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayuserterminal.ui.adapter.CommonDetailAdapter;
import com.qxmagic.railwayuserterminal.ui.event.adapter.RepairImagesAdapter;
import com.qxmagic.railwayuserterminal.ui.photo.ImagesLocalActivity;
import com.qxmagic.railwayuserterminal.utils.ListUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Christian on 2017/3/29 0029.
 * 公共的view 详情界面时直接用
 */

public class CommonView extends LinearLayout {
    @BindView(R.id.common_detail_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.common_detail_images_layout)
    LinearLayout mImagesLayout;
    @BindView(R.id.common_detail_image_text)
    TextView mImagesTypeTextView;
    @BindView(R.id.common_detail_images_recyclerView)
    RecyclerView mImagesRecyclerView;

    private Context mContext;

    //列表集合
    private ArrayList<CommonDetailBean> mList = new ArrayList<>();

    public void setList(ArrayList<CommonDetailBean> mList) {
        this.mList = mList;
    }

    //是否显示图片布局
    private boolean isShowImagesLayout;

    public void setShowImagesLayout(boolean showImagesLayout) {
        isShowImagesLayout = showImagesLayout;
    }

    private String mImagesType;

    public void setImagesType(String mImagesType) {
        this.mImagesType = mImagesType;
    }

    private ArrayList<String> mImagesList = new ArrayList<>();

    public void setImagesList(ArrayList<String> mImagesList) {
        this.mImagesList = mImagesList;
    }

    public CommonView(Context context) {
        super(context);
        this.mContext = context;
        initView();
        init();
    }

    public CommonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        init();
    }

    public CommonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        init();
    }

    private void initView() {
        inflate(mContext, R.layout.common_detail_layout, this);
        ButterKnife.bind(this);
    }


    public void init() {
        //设置列表项
        if (!ListUtil.isEmpty(mList)) {
            CommonDetailAdapter mAdapter = new CommonDetailAdapter(mList, mContext);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
        if (isShowImagesLayout && !ListUtil.isEmpty(mImagesList)) {
            mImagesLayout.setVisibility(View.VISIBLE);
            mImagesTypeTextView.setText(mImagesType);
            RepairImagesAdapter mImagesAdapter = new RepairImagesAdapter(mImagesList, mContext);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3){
                @Override
                public boolean canScrollHorizontally() {
                    return false;
                }

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
            mImagesRecyclerView.setLayoutManager(gridLayoutManager);
            mImagesRecyclerView.setAdapter(mImagesAdapter);
            mImagesAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View itemView, Object item, int pos) {
                    if (mImagesList.size() > pos) {
                        String bean1 = mImagesList.get(pos);
                        if (!TextUtils.isEmpty(bean1)) {
                            File file = new File(bean1);
                            if (file.exists() || null != Uri.parse(bean1)) {
                                Intent intent = new Intent(mContext,
                                        ImagesLocalActivity.class);
                                intent.putExtra("isDelete", false);
                                intent.putExtra("index", pos);
                                intent.putExtra("imageUrlList", mImagesList);
                                mContext.startActivity(intent);
                            }
                        }
                    }
                }
            });
        } else {
            mImagesLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expanSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expanSpec);
    }

}
