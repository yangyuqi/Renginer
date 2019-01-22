package com.qxmagic.railwayuserterminal.api.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 下载请求拦截器
 */
public class DownloadProgressInterceptor implements Interceptor {

    private DownLoadProgressListener listener;

    public DownloadProgressInterceptor(DownLoadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                .build();
    }
}