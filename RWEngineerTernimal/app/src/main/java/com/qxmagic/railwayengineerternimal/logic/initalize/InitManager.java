package com.qxmagic.railwayengineerternimal.logic.initalize;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.logic.global.GlobalVariable;

/**
 * 初始化工作类
 */
public final class InitManager implements IInitManager
{
    private static final String TAG = "===InitManager===";
    
    /**
     * 单实例对象
     */
    private static InitManager instance;
    
    /**
     * 私有构造，单例模式
     */
    private InitManager()
    {
    }
    
    /**
     * 创建MainManager实例
     * @return    实例对象
     */
    public static InitManager getInstance()
    {
        if (instance == null)
        {
            instance = new InitManager();
        }
        return instance;
    }
    
    /**
     * 初始化客户端信息，如版本号，版本名字，屏幕分辨率等    
     * @param context 上下文
     */
    public void initClient(Context context)
    {
        try
        {
            PackageInfo info = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            GlobalVariable.versionCode = info.versionCode;
            GlobalVariable.versionName = info.versionName;
        }
        catch (NameNotFoundException e)
        {
            Logger.e(e.getMessage());
        }
        
        getDisplay((Activity) context);
    }
    
    /**
     * 获取屏幕信息
     * 
     * @return String 分别率
     */
    public String getDisplay(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(dm);
        GlobalVariable.screenWidth = dm.widthPixels;
        GlobalVariable.screenHeight = dm.heightPixels;
        GlobalVariable.densityDPI = dm.densityDpi;
        GlobalVariable.density = dm.density;
        
        if (GlobalVariable.screenWidth > GlobalVariable.screenHeight)
        {
            GlobalVariable.screenWidth = dm.heightPixels;
            GlobalVariable.screenHeight = dm.widthPixels;
        }
        

        GlobalVariable.screenSize = dm.widthPixels + "*" + dm.heightPixels;
        return GlobalVariable.screenSize;
    }
}
