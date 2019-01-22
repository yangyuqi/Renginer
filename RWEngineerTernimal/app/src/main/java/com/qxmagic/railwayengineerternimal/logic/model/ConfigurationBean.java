package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/27 0027.
 * 配置项bean
 */
public class ConfigurationBean implements Serializable {
    @SerializedName("recordList")
    public ArrayList<ConfigurationBean> commonList;

    public String uuid;

    public String configurationCount;

    //配置项名称
    @SerializedName("configName")
    public String configurationName;

    //配置项类型
    @SerializedName("configType")
    public String type;

    @SerializedName("orgName")
    public String organization;

    //db服务器
    public String dbServer;

    public String state;

    @SerializedName("serPrio")
    public String priority;

    //所在位置
    @SerializedName("position")
    public String location;

    public String brand;
    //型号
    @SerializedName("model")
    public String configurationModel;

    //机架
    @SerializedName("frame")
    public String frameUnit;

    //序列号
    @SerializedName("serialNum")
    public String serialNumber;

    //资产编号
    @SerializedName("assetNum")
    public String assetLabel;

    @SerializedName("startTime")
    public String startUseTime;
    //采购时间
    @SerializedName("purchaseTime")
    public String purchasingTime;
    //过保日期
    @SerializedName("warTime")
    public String insuredDate;

    public String description;

    //所在系统
    public String system;
    //软件
    public String software;
    //软件许可
    public String softPermit;
    //路径
    public String path;


    public String key;
    public String value;
}
