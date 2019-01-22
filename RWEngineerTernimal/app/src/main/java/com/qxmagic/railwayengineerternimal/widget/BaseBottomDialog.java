package com.qxmagic.railwayengineerternimal.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.logic.global.GlobalVariable;
import com.qxmagic.railwayengineerternimal.utils.Util;


/**
 * 底部弹出框
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author guoac
 * @version [版本号, 2015-1-20]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseBottomDialog extends Dialog {
    /**
     * 弹出dialog的Activity
     */
    private Context context;

    /**
     * 布局文件
     */
    private int layoutId;

    /**
     * <默认构造函数>
     */
    public BaseBottomDialog(Context context, int layoutId) {
        super(context, R.style.bottom_dialog_window_style);
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        initView();
        bindListener();
    }

    /**
     * 初始化view
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initView();

    /**
     * 设置监听事件
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void bindListener();

    /**
     * 自定义动画展示的Dialog
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showDialog() {
        show();
        Window window = getWindow();

        if (0 == GlobalVariable.screenWidth) {
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVariable.screenWidth = dm.widthPixels;
            GlobalVariable.screenHeight = dm.heightPixels;
            GlobalVariable.densityDPI = dm.densityDpi;
        }
        // 设置显示动画
        window.setWindowAnimations(R.style.bottom_dialog_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = GlobalVariable.screenHeight;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = GlobalVariable.screenWidth;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);
    }

    /**
     * 自定义动画展示的Dialog
     * <一句话功能简述>
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showRightTopDialog() {
        show();
        Window window = getWindow();

        if (0 == GlobalVariable.screenWidth) {
            DisplayMetrics dm = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVariable.screenWidth = dm.widthPixels;
            GlobalVariable.screenHeight = dm.heightPixels;
            GlobalVariable.densityDPI = dm.densityDpi;
        }
        // 设置显示动画
        //        window.setWindowAnimations(R.style.bottom_dialog_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = GlobalVariable.screenWidth;
        wl.y = -GlobalVariable.screenHeight;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = Util.dpToPixel(context, 150);
        wl.height = Util.dpToPixel(context, 200);

        // 设置显示位置
        onWindowAttributesChanged(wl);
        // 设置点击外围解散
        setCanceledOnTouchOutside(true);
    }

    //add by xjf 2016-09-01

    /**
     * 取消back键
     */
    public void cancelBackKey() {
        setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH
                        || keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        setCancelable(false);
    }
}
