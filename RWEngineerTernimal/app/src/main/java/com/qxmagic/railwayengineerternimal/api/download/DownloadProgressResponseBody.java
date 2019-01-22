package com.qxmagic.railwayengineerternimal.api.download;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Christian on 2017/5/18 0018.
 * 下载请求响应体
 */

public class DownloadProgressResponseBody extends ResponseBody {
    private final ResponseBody responseBody;
    private final DownLoadProgressListener pregressListener;
    private BufferedSource bufferedSource;

    public DownloadProgressResponseBody(ResponseBody responseBody, DownLoadProgressListener pregressListener) {
        this.responseBody = responseBody;
        this.pregressListener = pregressListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                pregressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }
}
