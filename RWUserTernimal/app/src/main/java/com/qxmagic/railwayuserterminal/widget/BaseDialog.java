package com.qxmagic.railwayuserterminal.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.logic.global.GlobalVariable;
import com.qxmagic.railwayuserterminal.utils.Util;


/**
 * 对话框的基类
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author xjf
 * @version [版本号, 2014-7-7]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public abstract class BaseDialog extends Dialog {

    /**
     * 布局id
     */
    protected int layoutId;

    /**
     * 上下文
     */
    protected Context mContext;

    public BaseDialog(Context context, int layoutId) {
        super(context, R.style.base_dialog);
        mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.layoutId);
        initViews();
        bindLinsenter();
    }

    /**
     * 获取dialog的views
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void initViews();

    /**
     * 绑定监听事件
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected abstract void bindLinsenter();

    /**
     * <一句话功能简述>
     * 显示软件盘
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    protected void showSoftInputFromWindow() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
    }

    /**
     * 显示对话框
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showDialog() {
        show();
        Window dialogWindow = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        if (0 == GlobalVariable.screenWidth) {
            dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVariable.screenWidth = dm.widthPixels;
            GlobalVariable.screenHeight = dm.heightPixels;
            GlobalVariable.densityDPI = dm.densityDpi;
        }
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int) (GlobalVariable.screenWidth - dm.density * 40);
        dialogWindow.setAttributes(params);
    }

    /**
     * 显示对话框
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showMiddleDialog() {
        show();
        Window dialogWindow = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        if (0 == GlobalVariable.screenWidth) {
            dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVariable.screenWidth = dm.widthPixels;
            GlobalVariable.screenHeight = dm.heightPixels;
            GlobalVariable.densityDPI = dm.densityDpi;
        }
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int) (GlobalVariable.screenWidth - Util.dpToPixel(mContext, 60));
        dialogWindow.setAttributes(params);
    }

    /**
     * 显示对话框
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showMiddle() {
        show();
        Window dialogWindow = getWindow();
        DisplayMetrics dm = new DisplayMetrics();
        if (0 == GlobalVariable.screenWidth) {
            dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
            GlobalVariable.screenWidth = dm.widthPixels;
            GlobalVariable.screenHeight = dm.heightPixels;
            GlobalVariable.densityDPI = dm.densityDpi;
        }
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = (int) (GlobalVariable.screenWidth - Util.dpToPixel(mContext, 10));
        params.height = GlobalVariable.screenHeight;
        dialogWindow.setAttributes(params);
    }

    /**
     * 在底部显示对话框
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showBottomDialog() {
        show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = GlobalVariable.screenWidth;
        params.gravity = Gravity.BOTTOM;
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setAttributes(params);
    }

    /**
     * 在顶部显示对话框
     * <功能详细描述> [参数说明]
     *
     * @return void [返回类型说明]
     * @throws throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public void showTopDialog() {
        show();
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        params.width = GlobalVariable.screenWidth;
        params.gravity = Gravity.TOP;
        dialogWindow.setWindowAnimations(R.style.AnimBottom);
        dialogWindow.setAttributes(params);
    }
}
