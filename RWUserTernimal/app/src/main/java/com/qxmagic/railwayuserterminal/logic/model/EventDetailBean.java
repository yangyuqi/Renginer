package com.qxmagic.railwayuserterminal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/20 0020.
 * 事件详情对象
 */
public class EventDetailBean implements Serializable {
    //事件id
    @SerializedName("uuid")
    public String id;
    //事件类型 0：设备故障 1.。。 2.。。
    public String eventType;
    //故障类型
    @SerializedName("title")
    public String faultType;
    //请求类型
    public String requestType;
    //服务类型
    @SerializedName("service")
    public String serviceType;
    //服务子项
    @SerializedName("serviceSubkey")
    public String serviceSubType;
    //事件开始时间
    @SerializedName("createTime")
    public String startTime;
    //事件单号
    public String eventNumber;
    //事件最后修改时间
    @SerializedName("lastTime")
    public String lastChangeTime;
    //事件指派时间
    @SerializedName("assignTime")
    public String appointTime;
    //事件关闭时间
    public String endTime;
    //事件紧急程度
    @SerializedName("urgencyLevel")
    public String urgencyLevel;
    //事件优先级
    public String priority;
    //指派人
    @SerializedName("sponsor")
    public String appointedPerson;
    //事件进度  0:已派送 1：到厂维护 2：完成
    public String eventProgress;
    //派送时间
    public String deliveryTime;
    //到场时间
    public String arriveTime;
    //完成时间
    public String finishTime;
    //约定时间
    public String appointmentTime;
    //组织
    @SerializedName("orgName")
    public String organization;
    //事件状态
    @SerializedName("state")
    public String eventState;
    //事件描述
    public String description;
    //TODO 图片集合
//    @SerializedName("str2")
    @SerializedName("list")
    public ArrayList<String> images;
    //热门标记  关联问题
    @SerializedName("hotTag")
    public String hotSign;
    //热门理由  关联变更
    public String hotReason;
    //影响  评价
    public String effect;
    //处理事件团队
    @SerializedName("appointTeam")
    public String dealEventTeam;
    //办理人
    @SerializedName("appointPerson")
    public String dealMan;
    //父级请求
    public String parentRequest;
    //父级事件
    public String parentEvent;
    //父级问题
    @SerializedName("parentProblem")
    public String parentQuestion;
    //父级变更
    public String parentChange;

    //TTR日期
    public String ttrDate;
    //TTO超时 0:是 1：否
    public String isTTOOverTime;
    //TTO超时数
    public String ttoTime;

    //留言
    public String nature;

    public String source;

    public boolean check;
}
