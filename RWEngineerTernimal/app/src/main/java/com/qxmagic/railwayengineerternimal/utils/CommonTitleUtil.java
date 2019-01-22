package com.qxmagic.railwayengineerternimal.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qxmagic.railwayengineerternimal.R;


public class CommonTitleUtil {

    /**
     * 初始化所有activity公共标题栏布局
     *
     * @param activity      当前activity
     * @param titleStr      中间标题栏文字
     * @param rightImageId  右边图片资源id,否则为0
     * @param isHiddenLeft  是否隐藏左边view
     * @param isHiddenRight 是否隐藏右边view
     */
    public static void initCommonTitle(final Activity activity,
                                       String titleStr, int rightImageId, final boolean isHiddenLeft,
                                       boolean isHiddenRight) {
        //中间标题
        TextView titleView = (TextView) activity.findViewById(R.id.common_title_center_text);
        //左边布局
        RelativeLayout leftBackLayout = (RelativeLayout) activity.findViewById(R.id.common_title_left);
        final ImageView leftBackView = (ImageView) activity.findViewById(R.id.common_left_back_imageview);
        //右边布局
        RelativeLayout rightLayout = (RelativeLayout) activity.findViewById(R.id.common_title_right);
        ImageView rightImageView = (ImageView) activity.findViewById(R.id.common_title_right_imageview);
        if (!TextUtils.isEmpty(titleStr)) {
            titleView.setText(titleStr);
        }
        //是否隐藏左边
        if (!isHiddenLeft) {
            leftBackView.setVisibility(View.VISIBLE);
            // 监听左边返回    
            leftBackLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
        //是否隐藏右边view
        if (isHiddenRight) {
            rightLayout.setVisibility(View.INVISIBLE);
        } else {
            //图片
            if (rightImageId != 0) {
                rightLayout.setVisibility(View.VISIBLE);
                rightImageView.setVisibility(View.VISIBLE);
                rightImageView.setImageResource(rightImageId);
            }
        }
    }

    /**
     * 初始化所有activity公共标题栏布局
     *
     * @param activity      当前activity
     * @param titleStr      中间标题栏文字
     * @param isHiddenLeft  是否隐藏左边view
     * @param isHiddenRight 是否隐藏右边view
     */
    public static void initCommonTitle(final Activity activity,
                                       String titleStr, int leftImgResId, int rightImgResId,
                                       final boolean isHiddenLeft, boolean isHiddenRight, OnClickListener leftListener, OnClickListener rightListener) {
        //中间标题
        TextView titleView = (TextView) activity.findViewById(R.id.common_title_center_text);
        //左边布局
        RelativeLayout leftLayout = (RelativeLayout) activity.findViewById(R.id.common_title_left);
        final ImageView leftBackView = (ImageView) activity.findViewById(R.id.common_left_back_imageview);
        //右边布局
        RelativeLayout rightLayout = (RelativeLayout) activity.findViewById(R.id.common_title_right);
        TextView rightTextView = (TextView) activity.findViewById(R.id.common_title_right_text);
        ImageView rightImageView = (ImageView) activity.findViewById(R.id.common_title_right_imageview);
        if (!TextUtils.isEmpty(titleStr)) {
            titleView.setText(titleStr);
        }
        //是否隐藏左边
        if (!isHiddenLeft) {
            if (leftImgResId != 0) {
                leftBackView.setImageResource(leftImgResId);
            }
            leftBackView.setVisibility(View.VISIBLE);
            // 监听左边返回    
            leftLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        } else {
            leftBackView.setVisibility(View.INVISIBLE);
        }
        //是否隐藏右边view
        if (isHiddenRight) {
            rightLayout.setVisibility(View.INVISIBLE);
        } else {
            if (rightImgResId != 0) {
                rightLayout.setVisibility(View.VISIBLE);
                rightTextView.setVisibility(View.GONE);
                rightImageView.setVisibility(View.VISIBLE);
                rightImageView.setBackgroundResource(rightImgResId);
            } else {
                rightLayout.setVisibility(View.INVISIBLE);
            }
            if (null != rightListener) {
                rightLayout.setOnClickListener(rightListener);
            }
        }
    }

    /**
     * <功能详细描述>
     *
     * @param activity      当前activity
     * @param titleStr      中间标题栏文字
     * @param rightText     右边文字
     * @param isHiddenLeft  是否隐藏左边view
     * @param isHiddenRight 是否隐藏右边view
     */
    public static void initCommonTitle(final Activity activity,
                                       String titleStr, String rightText, final boolean isHiddenLeft,
                                       boolean isHiddenRight, OnClickListener rightListener) {
        //中间标题
        TextView titleView = (TextView) activity.findViewById(R.id.common_title_center_text);
        //左边布局
        RelativeLayout leftLayout = (RelativeLayout) activity.findViewById(R.id.common_title_left);
        final ImageView leftBackView = (ImageView) activity.findViewById(R.id.common_left_back_imageview);
        //右边布局
        RelativeLayout rightLayout = (RelativeLayout) activity.findViewById(R.id.common_title_right);
        TextView rightTextView = (TextView) activity.findViewById(R.id.common_title_right_text);
        ImageView rightImageView = (ImageView) activity.findViewById(R.id.common_title_right_imageview);
        if (!TextUtils.isEmpty(titleStr)) {
            titleView.setText(titleStr);
        }
        //是否隐藏左边
        if (!isHiddenLeft) {
            leftBackView.setVisibility(View.VISIBLE);
            // 监听左边返回    
            leftLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
        //是否隐藏右边view
        if (isHiddenRight) {
            rightLayout.setVisibility(View.INVISIBLE);
        } else {
            if (!TextUtils.isEmpty(rightText)) {
                rightLayout.setVisibility(View.VISIBLE);
                rightTextView.setVisibility(View.VISIBLE);
                rightImageView.setVisibility(View.GONE);
                rightTextView.setText(rightText);
            } else {
                rightLayout.setVisibility(View.INVISIBLE);
            }
            if (null != rightListener) {
                rightLayout.setOnClickListener(rightListener);
            }
        }
    }

    /**
     * 初始化所有fragment标题
     *
     * @param view               所在的view
     * @param titleStr           中间标题
     * @param rightImageId       右边图片资源id
     * @param leftImageId        左边图片资源id
     * @param rightClickListener 右边监听 可为null
     * @param leftClickListener  左边监听 可为null
     * @param isHiddenRight      是否隐藏右边
     * @param isHiddenLeft       是否隐藏左边
     */
    public static void initFragmentTitle(View view, String titleStr,
                                         int rightImageId, int leftImageId, OnClickListener rightClickListener, OnClickListener leftClickListener, boolean isHiddenRight, boolean isHiddenLeft) {
        //中间标题
        TextView titleView = (TextView) view.findViewById(R.id.common_title_center_text);
        //左边布局
        RelativeLayout leftLayout = (RelativeLayout) view.findViewById(R.id.common_title_left);
        final ImageView leftBackView = (ImageView) view.findViewById(R.id.common_left_back_imageview);
        //右边布局
        RelativeLayout rightLayout = (RelativeLayout) view.findViewById(R.id.common_title_right);
        ImageView rightImageView = (ImageView) view.findViewById(R.id.common_title_right_imageview);
        if (!TextUtils.isEmpty(titleStr)) {
            titleView.setText(titleStr);
        }
        //是否隐藏左边
        if (isHiddenLeft) {
            leftLayout.setVisibility(View.INVISIBLE);
        } else {
            //图片
            if (leftImageId != 0) {
                leftLayout.setVisibility(View.VISIBLE);
                leftBackView.setVisibility(View.VISIBLE);
                leftBackView.setImageResource(leftImageId);
            }
            if (null != leftClickListener) {
                leftLayout.setOnClickListener(leftClickListener);
            }
        }
        //是否隐藏右边view
        if (isHiddenRight) {
            rightLayout.setVisibility(View.INVISIBLE);
        } else {
            //图片
            if (rightImageId != 0) {
                rightLayout.setVisibility(View.VISIBLE);
                rightImageView.setVisibility(View.VISIBLE);
                rightImageView.setImageResource(rightImageId);
            }
            if (null != rightClickListener) {
                rightLayout.setOnClickListener(rightClickListener);
            }
        }
    }

}
