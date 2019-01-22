package com.qxmagic.railwayengineerternimal.api.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Christian on 2017/5/18 0018.
 * 下载apk接口
 */

public interface DownLoadService {
    /**
     * 下载apk
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadApk(@Url String apkUrl);
}
