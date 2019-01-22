package com.qxmagic.railwayengineerternimal.ui.receptiondesk.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.ui.adapter.BaseRecyclerViewAdapter;
import com.qxmagic.railwayengineerternimal.ui.adapter.RecyclerViewHolder;
import com.qxmagic.railwayengineerternimal.utils.ImageLoader;

import java.util.List;

/**
 * Created by Christian on 2017/3/20 0020.
 * 报修图片适配器
 */

public class RepairImagesAdapter extends BaseRecyclerViewAdapter<String> {
    /**
     * 添加标示
     */
    public static final String UPLOAD_PICTURE_ADD = "upload_picture_add";

    /**
     * 添加图片的最大限制
     */
    public static final int UPLOAD_PICTURE_MAXSIZE = 9;

    public RepairImagesAdapter(List<String> mList, Context mContext) {
        super(mList, mContext);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_gridview_images;
    }

    @Override
    protected void bindData(RecyclerViewHolder holder, String item) {
        ImageView imageView = holder.getImageView(R.id.repair_image);
        if (!TextUtils.isEmpty(item)) {
            if (UPLOAD_PICTURE_ADD.equals(item)) {
                imageView.setImageResource(R.mipmap.upload_image_icon);
            } else {
                ImageLoader.loadCenterCrop(mContext, item, holder.getImageView(R.id.repair_image), R.mipmap.default_icon);
            }

        } else {
            holder.getImageView(R.id.repair_image).setImageResource(R.mipmap.default_icon);
        }
    }

}
