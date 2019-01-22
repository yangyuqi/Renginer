package com.qxmagic.railwayengineerternimal.ui.photo.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.global.GlobalVariable;
import com.qxmagic.railwayengineerternimal.logic.initalize.InitManager;
import com.qxmagic.railwayengineerternimal.logic.model.ImageBean;
import com.qxmagic.railwayengineerternimal.ui.photo.model.Image;
import com.qxmagic.railwayengineerternimal.utils.ImageLoader;
import com.qxmagic.railwayengineerternimal.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片Adapter
 * Created by Nereo on 2015/4/7.
 */
public class ImageGridAdapter extends BaseAdapter
{

    private static final int TYPE_CAMERA = 0;

    private static final int TYPE_NORMAL = 1;

    private LayoutInflater mInflater;

    private boolean showCamera = true;

    private boolean showSelectIndicator = true;

    private List<Image> mImages = new ArrayList<Image>();

    private List<Image> mSelectedImages = new ArrayList<Image>();

    private GridView.LayoutParams mItemLayoutParams;

    private Context mContext;

    private Handler mHandler;

//    private DisplayImageOptions options;

    public ImageGridAdapter(Context context, boolean showCamera)
    {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
//        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.common_transparent_img)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
    }

    public void setHandler(Handler handler)
    {
        this.mHandler = handler;
    }

    /**
     * 显示选择指示器
     * @param b
     */
    public void showSelectIndicator(boolean b)
    {
        showSelectIndicator = b;
    }

    public void setShowCamera(boolean b)
    {
        if (showCamera == b)
        {
            return;
        }
        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera()
    {
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     * @param image
     */
    public void select(Image image)
    {
        if (mSelectedImages.contains(image))
        {
            mSelectedImages.remove(image);
        }
        else
        {
            mSelectedImages.add(image);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList)
    {
        mSelectedImages.clear();
        for (String path : resultList)
        {
            Image image = getImageByPath(path);
            if (image != null)
            {
                mSelectedImages.add(image);
            }
        }
        notifyDataSetChanged();
    }


    private Image getImageByPath(String path)
    {
        if (mImages != null && mImages.size() > 0)
        {
            for (Image image : mImages)
            {
                if (image.path.equalsIgnoreCase(path))
                {
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     * @param images
     */
    public void setData(List<Image> images)
    {
        mSelectedImages.clear();

        if (images != null && images.size() > 0)
        {
            mImages = images;
        }
        else
        {
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (showCamera)
        {
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount()
    {
        return showCamera ? mImages.size() + 1 : mImages.size();
    }

    @Override
    public Image getItem(int i)
    {
        if (showCamera)
        {
            if (i == 0)
            {
                return null;
            }
            return mImages.get(i - 1);
        }
        else
        {
            return mImages.get(i);
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {

        int type = getItemViewType(i);
        if (type == TYPE_CAMERA)
        {
            //            view = mInflater.inflate(R.layout.list_item_camera,
            //                    viewGroup,
            //                    false);
            //            view.setTag(null);
            view = mInflater.inflate(R.layout.yf_picture_picker_grid_item,
                    viewGroup,
                    false);
        }
        else if (type == TYPE_NORMAL)
        {
            ViewHolder holder;
            if (view == null)
            {
                holder = new ViewHolder();
                view = mInflater.inflate(R.layout.yf_picture_picker_grid_item,
                        viewGroup,
                        false);
                if (0 == GlobalVariable.screenWidth)
                {
                    InitManager.getInstance().initClient((Activity) mContext);
                }
                int with = (GlobalVariable.screenWidth - 2 * Util.dpToPixel(mContext,
                        2)) / 3;
                mItemLayoutParams = new GridView.LayoutParams(with, with);
                view.setLayoutParams(mItemLayoutParams);
                holder.image = (ImageView) view.findViewById(R.id.image);
                holder.indicator = (ImageView) view.findViewById(R.id.checkmark);
                holder.checkView = view.findViewById(R.id.item_check_layout);
                holder.mask = view.findViewById(R.id.mask);
                view.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) view.getTag();
            }
            Image image = getItem(i);
            if (showSelectIndicator)
            {
                holder.indicator.setVisibility(View.VISIBLE);
                if (mSelectedImages.contains(image))
                {
                    // 设置选中状态
                    holder.indicator.setImageResource(R.mipmap.yf_picture_picker_checked);
                    holder.mask.setVisibility(View.VISIBLE);
                }
                else
                {
                    // 未选择
                    holder.indicator.setImageResource(R.mipmap.yf_picture_picker_unchecked);
                    holder.mask.setVisibility(View.GONE);
                }
            }
            else
            {
                holder.indicator.setVisibility(View.GONE);
            }
            holder.checkView.setTag(image);
            holder.checkView.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    if (null != mHandler)
                    {
                        Message message = new Message();
                        message.obj = v.getTag();
                        mHandler.sendMessage(message);
                    }
                }
            });
            ImageLoader.loadCenterCrop(mContext,image.path,holder.image,R.mipmap.default_icon);
//            ImageLoader.getInstance().displayImage("file://" + image.path,
//                    holder.image,
//                    options);
        }
        return view;
    }

    private class ViewHolder
    {
        ImageView image;

        ImageView indicator;

        View checkView;

        View mask;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

}
