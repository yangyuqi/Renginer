package com.qxmagic.railwayuserterminal.api.response;

import com.google.gson.annotations.SerializedName;
import com.qxmagic.railwayuserterminal.logic.model.UserBean;

import java.io.Serializable;

/**
 * Created by Christian on 2017/6/5 0005.
 * 用户登录请求响应对象
 */

public class UserResp implements Serializable {
    public String status;

    public String info;

    @SerializedName("data")
    public UserBean userBean;
}
