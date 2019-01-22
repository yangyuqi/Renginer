package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Christian on 2017/3/20 0020.
 * 变更详情对象
 */
public class ChangeDetailBean implements Serializable {
    //变更id
    @SerializedName("uuid")
    public String id;

    //变更类型
    public String changeType;

    //变更标题
    @SerializedName("title")
    public String changeTitle;

    //服务类型
//    public String serviceType;
    //服务子项
//    public String serviceSubType;

    //变更开始时间
    @SerializedName("createTime")
    public String startTime;

    //发布人
    @SerializedName("lastBy")
    public String author;

    //变更单号
    public String changeNumber;
    //变更最后修改时间
    @SerializedName("lastTime")
    public String lastChangeTime;
    //变更指派时间
    @SerializedName("assignTime")
    public String appointTime;

    //变更关闭时间
    @SerializedName("closeTime")
    public String endTime;
    //变更紧急程度
    public String urgencyLevel;
    //变更优先级
    public String priority;

    //组织
    @SerializedName("orgName")
    public String organization;

    //变更状态
    @SerializedName("state")
    public String changeState;

    //变更描述
    public String description;
    //TODO 图片集合
    public String[] images;
    //变更来源
    public String changeSource;
    //回退计划
    @SerializedName("fallback")
    public String rejectPlan;
    //拒绝原因
    @SerializedName("rejReason")
    public String changeReason;
    //是否停机
    @SerializedName("stop")
    public String machineHalt;
    //影响
    public String effect;
    //处理变更团队
    @SerializedName("team")
    public String dealchangeTeam;
    //办理人
    @SerializedName("appointPerson")
    public String dealMan;
    //关联事件
    public String relatedEvent;

    //父级变更
    public String parentChange;

    //TTR日期
//    public String ttrDate;
    //TTO超时 0:是 1：否
//    public String isTTOOverTime;
    //TTO超时数
//    public String ttoTime;

    @SerializedName("annexPath")
    public String attach;

    public String sponsor;

    @SerializedName("appointedPerson")
    public String approve;

    public boolean check;
}
