package com.qxmagic.railwayengineerternimal.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.qxmagic.railwayengineerternimal.R;
import com.qxmagic.railwayengineerternimal.RWETApplication;
import com.qxmagic.railwayengineerternimal.ui.configurationitems.ConfigurationFragment;
import com.qxmagic.railwayengineerternimal.ui.knowledge_base.KnowledgeBaseFragment;
import com.qxmagic.railwayengineerternimal.ui.mine.MineFragment;
import com.qxmagic.railwayengineerternimal.ui.receptiondesk.ReceptionDeskFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener ,ReceptionDeskFragment.putDataInterface {
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
                    RWETApplication.getInstance().isInstalling = false;
                    Intent it = new Intent(Intent.ACTION_VIEW);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    File file = new File(Environment.getExternalStoragePublicDirectory
                            (Environment.DIRECTORY_DOWNLOADS), "file.apk");
                    Uri APK_URI;
                    if (Build.VERSION.SDK_INT >= 24) {
//                        it.setAction(Intent.ACTION_INSTALL_PACKAGE);
                        it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        APK_URI = FileProvider.getUriForFile(mContext, "com.qxmagic.railwayengineerternimal.fileprovider", file);
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
//        EventBus.getDefault().register(this);
        _initFragment();
//        _requestPermission();
//        FileHelper.makeDir();
//        InitManager.getInstance().initClient(mContext);
        registerFilter();
    }

    private void registerFilter() {
        IntentFilter filter = new IntentFilter(UPDATE_SUCCESS);
        registerReceiver(receiver, filter);
    }

    /**
     * 请求权限
     */
    private void _requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA}, 1000);
        }
    }

    private void _initFragment() {
        mFragmentManager = getSupportFragmentManager();
        navigateMap.clear();
        mapNaviToFragment(R.id.main_reception_layout, new ReceptionDeskFragment());
        mapNaviToFragment(R.id.main_knowledge_layout, new KnowledgeBaseFragment());
        mapNaviToFragment(R.id.main_configuration_layout, new ConfigurationFragment());
        mapNaviToFragment(R.id.main_mine_layout, new MineFragment());

        replaceFragment(R.id.main_reception_layout);
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
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
        if (null != receiver) {
            unregisterReceiver(receiver);
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

    @Override
    public void putData(int data) {
        switch (data){
            case 1 :
                replaceFragment(R.id.main_knowledge_layout);
                break;
            case 2 :
                replaceFragment(R.id.main_configuration_layout);
                break;
        }
    }

//    @Subscribe
//    public void onEvent(Integer place){
//        switch (place){
//            case 1 :
//                replaceFragment(R.id.main_knowledge_layout);
//                break;
//            case 2 :
//                replaceFragment(R.id.main_configuration_layout);
//                break;
//        }
//    }

}
