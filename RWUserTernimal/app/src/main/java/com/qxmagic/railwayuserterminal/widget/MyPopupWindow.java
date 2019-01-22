package com.qxmagic.railwayuserterminal.widget;


import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.utils.Util;

/**
 * <一句话功能简述>
 * popupwindow的父类窗体,弹出在界面底部
 * <功能详细描述>
 *
 * @author houfangfang
 * @version [版本号, 2014-3-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class MyPopupWindow {
    private int popupWidth;

    private int popupHeight;

    protected Context mContext;

    /**
     * popwindow的内容布局
     */
    protected int layoutId;

    /**
     * popwindow的句柄
     */
    protected PopupWindow popupWindow;

    /**
     * popwindow的内容view
     */
    public View view;

    /**
     * <默认构造函数>
     *
     * @param layoutId 内容布局id
     */
    public MyPopupWindow(Context mContext, int layoutId) {
        this.mContext = mContext;
        this.layoutId = layoutId;
    }

    /**
     * 初始化popwindow
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void initPopupWindow() {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutId, null);
            popupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            initPopWindowSize();
        }

        popupWindow.setAnimationStyle(R.style.popWindowAnimBottom);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new OnTouchListener() {
            View popView = null;

            public boolean onTouch(View v, MotionEvent event) {
                popView = view.findViewById(R.id.pop_window);
                if (null == popView) {
                    return true;
                }
                int height = popView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 初始化popwindow
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void initSharePopupWindow(int width) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutId, null);
            popupWindow = new PopupWindow(view,
                    width,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            initPopWindowSize();
        }

//        popupWindow.setAnimationStyle(R.style.popWindowAnimBottom);

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new OnTouchListener() {
            View popView = null;

            public boolean onTouch(View v, MotionEvent event) {
                popView = view.findViewById(R.id.pop_window);
                if (null == popView) {
                    return true;
                }
                int height = popView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 初始化指定大小popwindow，宽度不全屏显示
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void initPopupWindow(int width, int height) {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutId, null);
            popupWindow = new PopupWindow(view, width, height);
            initPopWindowSize();
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        popupWindow.setOnDismissListener(onDismissListener);
    }

    /**
     * 获得控件的长宽高
     */
    private void initPopWindowSize() {
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
    }

    /**
     * 显示在某控件的上方
     */
    public void showWindowUp(View parent) {
        // 获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        // 在控件上方显示
        initPopWindowSize();
        int x = (location[0] + parent.getWidth() / 2) - popupWidth / 2;
        int y = location[1] - popupHeight;
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], y);
    }

    /**
     * 显示在某控件的右边
     */
    public void showWindowRight(View parent) {
        // 获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        // 在控件右边显示
        int x = location[0] + parent.getWidth();
        int y = location[1];
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
    }

    /**
     * 显示底部popWindow
     * <功能详细描述>
     *
     * @param parent popwindow 的父view
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showWindow(View parent) {
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 显示中间popWindow
     * <功能详细描述>
     *
     * @param parent popwindow 的父view
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showWindowCenter(View parent) {
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    /**
     * 显示顶部左边还是右边popWindow
     * <功能详细描述>
     *
     * @param parent popwindow 的父view
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showWindow(View parent, int gravity, int x, int y) {
        popupWindow.showAtLocation(parent, gravity, x, y);
    }

    /**
     * 显示在某个布局下方
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @param anchor [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showAsDropDown(View anchor) {
        popupWindow.showAsDropDown(anchor);
    }

    /**
     * 隐藏popwindow
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void dismissWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
    }

    /**
     * 初始化views
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void initViews();

    /**
     * 绑定事件
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public abstract void bindListener();
}
