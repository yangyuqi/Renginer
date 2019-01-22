package com.qxmagic.railwayengineerternimal.api.response;

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
    @SerializedName("service")
    public ArrayList<String> serviceList;


//    public ArrayList<String> subServiceList;

    /**
     * 问题分类
     */
    @SerializedName("slist")
    public ArrayList<String> questionTypeList;

    /**
     * 关联事件
     */
    @SerializedName("event")
    public ArrayList<String> relateEventList;

    /**
     * 关联变更
     */
    @SerializedName("variation")
    public ArrayList<String> relateChangeList;

    /**
     * 团队
     */
    @SerializedName("listTeam")
    public ArrayList<String> teamList;

    /**
     * 办理人
     */
    @SerializedName("itlist")
    public ArrayList<String> dealManList;
}
