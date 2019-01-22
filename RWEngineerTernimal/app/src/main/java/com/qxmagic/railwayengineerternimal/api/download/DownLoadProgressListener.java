package com.qxmagic.railwayengineerternimal.api.download;

/**
 * Created by Christian on 2017/5/18 0018.
 * 下载进度监听
 */

public interface DownLoadProgressListener {
    /**
     * 更新下载进度
     *
     * @param byteRead      下载的当前进度
     * @param contentLength 总大小
     * @param done          是否下载完成
     */
    void update(long byteRead, long contentLength, boolean done);
}
