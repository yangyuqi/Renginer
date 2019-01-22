package com.qxmagic.railwayuserterminal.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Christian on 2017/5/23 0023.
 * 公共响应体
 */

public class CommonResp<T> implements Serializable {

    public String status;

    public String info;

    @SerializedName("data")
    public T commonBean;

    public String Count;

    /**
     * 验证码
     */
    public String authCode;

    @Override
    public String toString() {
        return "CommonResp{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", commonBean=" + commonBean +
                ", Count='" + Count + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
