package com.qxmagic.railwayuserterminal.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/7/26 0026.
 * 根据组织名称获得各种数据的响应
 */

public class OrgDataResp implements Serializable{
    /**
     * 发起人
     */
    @SerializedName("lilist")
    public ArrayList<String> sponList;


    public ArrayList<String> sourceList;

    /**
     * 服务
     */
    @SerializedName("slist")
    public ArrayList<String> serviceList;


    public ArrayList<String> subServiceList;

    /**
     * 父级问题
     */
    @SerializedName("qlist")
    public ArrayList<String> pQuestionList;

    /**
     * 父级请求、事件
     */
    @SerializedName("elist")
    public ArrayList<String> pEventList;

    /**
     * 父级变更
     */
    @SerializedName("vlist")
    public ArrayList<String> relateChangeList;

    /**
     * 团队
     */
    @SerializedName("clist")
    public ArrayList<String> teamList;

    /**
     * 办理人
     */
    @SerializedName("itlist")
    public ArrayList<String> dealManList;
}
