package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/20 0020.
 * 问题详情对象
 */
public class QuestionDetailBean implements Serializable {
    @SerializedName("recordList")
    public ArrayList<QuestionDetailBean> commonList;
    //问题id
    @SerializedName("uuid")
    public String id;
    //问题类型
    @SerializedName("problemType")
    public String questionType;
    //问题标题
    @SerializedName("title")
    public String questionTitle;
    //故障类型
    public String faultType;
    //请求类型
    public String requestType;
    //服务类型
    @SerializedName("service")
    public String serviceType;
    //服务子项
    @SerializedName("serviceSubkey")
    public String serviceSubType;
    //问题开始时间
    @SerializedName("createTime")
    public String startTime;
    //问题单号
//    public String questionNumber;
    //问题最后修改时间
    @SerializedName("lastTime")
    public String lastChangeTime;
    //问题指派时间
    @SerializedName("assignTime")
    public String appointTime;
    //问题关闭时间
    @SerializedName("closeTime")
    public String endTime;
    //问题紧急程度
    @SerializedName("urgentLevel")
    public String urgencyLevel;
    //问题优先级
    public String priority;
    //指派人
    public String appointedPerson;
    //问题进度  0:已派送 1：到厂维护 2：完成
    public String questionProgress;
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
    //问题状态
    @SerializedName("state")
    public String questionState;
    //问题描述
    public String description;
    //TODO 图片集合
    public String[] images;
    //问题来源
    @SerializedName("source")
    public String questionSource;
    //问题产品
    @SerializedName("product")
    public String production;
    //问题原因
    @SerializedName("reason")
    public String questionReason;
    //解决方法
    @SerializedName("solution")
    public String questionResolvent;
    //是否加入知识库 0:是 1：否
    @SerializedName("addTo")
    public String isAddToKnowledge;
    //热门标记
    public String hotSign;
    //热门理由
    public String hotReason;
    //影响
    public String effect;
    //处理问题团队
    @SerializedName("team")
    public String dealquestionTeam;
    //办理人
    @SerializedName("appointPerson")
    public String dealMan;
    //父级请求
    public String parentRequest;
    //父级问题
    public String parentquestion;
    //父级问题
    public String parentQuestion;
    //父级变更
    public String parentChange;

    //关联事件
    public String relatedEvent;

    //关联变更
    public String relatedChange;

    //TTR日期
    public String ttrDate;
    //TTO超时 0:是 1：否
    public String isTTOOverTime;
    //TTO超时数
    public String ttoTime;

    //发起人
    @SerializedName("spon")
    public String sponsor;

    public String annexPath;

    public boolean check;
}
