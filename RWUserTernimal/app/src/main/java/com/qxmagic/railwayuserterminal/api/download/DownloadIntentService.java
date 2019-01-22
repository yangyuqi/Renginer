package com.qxmagic.railwayuserterminal.api.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.R;
import com.qxmagic.railwayuserterminal.ui.MainActivity;
import com.qxmagic.railwayuserterminal.utils.StringUtil;

import java.io.File;

import rx.Subscriber;

/**
 * Created by Christian on 2017/5/18 0018.
 * 下载的intentService
 */

public class DownloadIntentService extends IntentService {
    private static final String TAG = "DownloadService";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;


    private String apkUrl = "http://192.168.75.57:8080/resources/version/NAVECO_v1.1_c2_dev_Build20170518-release.apk";

    private File outputFile;

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        apkUrl = intent.getStringExtra("apkUrl");
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.setting_version_update_icon)
                .setContentTitle("中铁用户端")
                .setContentText("正在下载中...")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());
        Logger.e(getClass().getSimpleName());
        download();
    }

    private void download() {
        DownLoadProgressListener listener = new DownLoadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                Download download = new Download();
                download.setTotalFileSize(contentLength);
                download.setCurrentFileSize(bytesRead);
                int progress = (int) ((bytesRead * 100) / contentLength);
                download.setProgress(progress);

                sendNotification(download);
            }
        };
        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "file.apk");
        String baseUrl = StringUtil.getHostName(apkUrl);

        new DownloadApi(baseUrl, listener).downloadAPK(apkUrl, outputFile, new Subscriber() {
            @Override
            public void onCompleted() {
                downloadCompleted();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted();
                Logger.e(TAG + "onError: " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Logger.i(o.toString());
                install();
            }
        });
    }

    private void install() {
        if (outputFile.exists()) {
            Intent it = new Intent(MainActivity.UPDATE_SUCCESS);
            it.putExtra("path", outputFile.getPath());
//            it.setDataAndType(Uri.parse("file://" + outputFile.getPath()),
//                    "application/vnd.android.package-archive");
//            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendBroadcast(it);
        } else {
            Logger.i(TAG, "download upgrade apk fail1");
        }
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("下载完成");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                StringUtil.getDataSize(download.getCurrentFileSize()) + "/" +
                        StringUtil.getDataSize(download.getTotalFileSize()));
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendIntent(Download download) {

//        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
//        intent.putExtra("download", download);
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
