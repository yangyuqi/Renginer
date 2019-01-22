package com.qxmagic.railwayuserterminal.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.ui.event.EventFragment;
import com.qxmagic.railwayuserterminal.ui.mine.MineFragment;
import com.qxmagic.railwayuserterminal.ui.service.ServiceFragment;

import java.io.File;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 后台更新下载apk文件成功
     */
    public static final String UPDATE_SUCCESS = "upload_success";
    /**
     * 存放所有的Fragment页面
     */
    private SparseArray<Fragment> navigateMap = new SparseArray<>();

    private FragmentManager mFragmentManager;

    private long mExitTime = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UPDATE_SUCCESS.equals(action)) {
                String path = intent.getStringExtra("path");
                if (!TextUtils.isEmpty(path)) {
                    RWUTApplication.getInstance().isInstalling = false;
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    File file = new File(Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS), "file.apk");
                    Uri APK_URI;
                    if (Build.VERSION.SDK_INT >= 24) {
//                        it.setAction(Intent.ACTION_INSTALL_PACKAGE);
                        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        APK_URI = FileProvider.getUriForFile(mContext, "com.qxmagic.railwayuserterminal.fileprovider", file);
                        it.setDataAndType(APK_URI,
                                "application/vnd.android.package-archive");
                    } else {
//                        it.setAction(Intent.ACTION_VIEW);
//                        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        APK_URI = Uri.parse("file://" + path);
                        APK_URI = Uri.fromFile(file);
                        it.setDataAndType(APK_URI,
                                "application/vnd.android.package-archive");
                    }
                    startActivity(it);
                }
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _initFragment();
        registerFilter();
//        FileHelper.makeDir();
//        InitManager.getInstance().initClient(mContext);
    }

    private void _initFragment() {
        mFragmentManager = getSupportFragmentManager();
        navigateMap.clear();
        mapNaviToFragment(R.id.main_event_layout, new EventFragment());
        mapNaviToFragment(R.id.main_service_layout, new ServiceFragment());
        mapNaviToFragment(R.id.main_mine_layout, new MineFragment());
        replaceFragment(R.id.main_event_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void updateViews() {

    }

    @Override
    protected void initInjector() {

    }

    private void registerFilter() {
        IntentFilter filter = new IntentFilter(UPDATE_SUCCESS);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != receiver) {
            unregisterReceiver(receiver);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (navigateMap.indexOfKey(id) < 0) {
            // 点击非导航view
            return;
        }
        if (!view.isSelected()) {
            // 当前非选中状态：需切换到新内容
            replaceFragment(id);
        }
    }

    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    /**
     * 初始化map
     *
     * @param id       控件id
     * @param fragment fragment
     */
    private void mapNaviToFragment(int id, Fragment fragment) {
        View view = findViewById(id);
        view.setOnClickListener(this);
        view.setSelected(false);
        navigateMap.put(id, fragment);
    }

    /**
     * 执行内容切换
     *
     * @param id 所点击view的id
     */
    private void replaceFragment(int id) {
        String tag = String.valueOf(id);
        FragmentTransaction trans = mFragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(trans);
        if (null == mFragmentManager.findFragmentByTag(tag)) {
            trans.add(R.id.content_frame, navigateMap.get(id), tag);
        } else {
            trans.show(mFragmentManager.findFragmentByTag(tag));
        }
        trans.commitAllowingStateLoss();

        // 重置导航选中状态
        for (int i = 0, size = navigateMap.size(); i < size; i++) {
            int curId = navigateMap.keyAt(i);
            if (curId == id) {
                findViewById(id).setSelected(true);
            } else {
                findViewById(curId).setSelected(false);
            }
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (null != mFragmentManager.getFragments()) {
            for (int i = 0, size = mFragmentManager.getFragments()
                    .size(); i < size; i++) {
                if (mFragmentManager.getFragments().get(i) != null) {
                    transaction.hide(mFragmentManager.getFragments().get(i));
                }
            }
        }
    }
}
