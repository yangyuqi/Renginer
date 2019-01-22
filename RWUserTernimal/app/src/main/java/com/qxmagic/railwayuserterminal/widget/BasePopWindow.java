package com.qxmagic.railwayuserterminal.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.qxmagic.railwayuserterminal.R;

import butterknife.ButterKnife;

/**
 * <一句话功能简述>
 * popupwindow的父类窗体
 * <功能详细描述>
 *
 * @author houfangfang
 * @version [版本号, 2014-3-13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BasePopWindow {
    protected Context mContext;

    private int popupWidth;
    private int popupHeight;

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
    protected View view;

    /**
     * <默认构造函数>
     *
     * @param layoutId 内容布局id
     */
    public BasePopWindow(Context mContext, int layoutId) {
        this.mContext = mContext;
        this.layoutId = layoutId;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        if (popupWindow != null) {
            popupWindow.setOnDismissListener(onDismissListener);
        }
    }

    /**
     * 初始化popwindow
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("deprecation")
    public void initPopupWindow() {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutId, null);
            popupWindow = new PopupWindow(view,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    // 设置PopupWindow弹出窗体的高
                    WindowManager.LayoutParams.WRAP_CONTENT);
            initPopWindowSize();
        }

        // 使其聚集  
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失  
        popupWindow.setOutsideTouchable(true);

        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener()
        {
            View popView = null;

            public boolean onTouch(View v, MotionEvent event)
            {
                popView = view.findViewById(R.id.pop_window);
                if (null == popView)
                {
                    return true;
                }
                int height = popView.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (y < height)
                    {
                        popupWindow.dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 获得控件的长宽高
     */
    private void initPopWindowSize(){
        //获取自身的长宽高
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = view.getMeasuredHeight();
        popupWidth = view.getMeasuredWidth();
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
    public void showTopWindow(View parent) {
        popupWindow.showAtLocation(parent, Gravity.TOP, 0, 0);
    }
    /**
     * 显示在某控件的上方
     */
    public void showWindowUp(View parent)
    {
        // 获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        // 在控件上方显示
        int x = (location[0] + parent.getWidth() / 2) - popupWidth / 2;
        int y = location[1] - popupHeight;
        popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
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
    public void showWindowByXY(View parent, int gravity, int x, int y) {
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
     * 显示在某个布局下方
     * <一句话功能简述>
     * <功能详细描述>
     *
     * @param anchor [参数说明]
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popupWindow.showAsDropDown(anchor, xoff, yoff);
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
