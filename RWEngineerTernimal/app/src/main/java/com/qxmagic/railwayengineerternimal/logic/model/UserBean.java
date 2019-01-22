package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Christian on 2017/3/17 0017.
 * 用户bean
 */

public class UserBean implements Serializable {
    public String userPortrait;
    public String username;
    public String password;
    public String phone;
    @SerializedName("uid")
    public String userId;

    public String token;

    @SerializedName("org")
    public String orgName;

    @SerializedName("spon")
    public String sponsor;

    /**
     * 用于判断当前角色是否是当前应用端的用户
     * gcs:工程师端
     * kh:用户端
     */
    public String role;
}
