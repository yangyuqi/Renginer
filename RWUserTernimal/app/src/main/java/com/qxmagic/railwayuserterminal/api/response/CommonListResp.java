package com.qxmagic.railwayuserterminal.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/6/5 0005.
 * 公共响应体
 */

public class CommonListResp<T> implements Serializable {
    public String status;
    @SerializedName("recordList")
    public ArrayList<T> commonList;
    public String info;
    private String count;
}
