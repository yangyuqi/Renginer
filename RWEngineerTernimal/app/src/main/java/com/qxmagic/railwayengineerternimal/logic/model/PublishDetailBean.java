package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Christian on 2017/3/20 0020.
 * 发布详情对象
 */
public class PublishDetailBean implements Serializable {
    //发布id
    @SerializedName("uuid")
    public String id;
    //组织
    @SerializedName("effect")
    public String organization;
    //发布开始时间
    @SerializedName("startTime")
    public String startTime;

    //发布优先级
    public String priority;

    //发布状态
    @SerializedName("pubState")
    public String publishState;

    //发布类型
    @SerializedName("pubType")
    public String publishType;
    //请求人
    @SerializedName("petioner")
    public String publishRequestMan;
    //发布请求时间
    @SerializedName("petionerTime")
    public String publishRequestTime;
    //发布标题
    @SerializedName("title")
    public String publishTitle;
    //发布内容
    @SerializedName("context")
    public String publishContent;
    //发布计划关闭时间
    @SerializedName("endTime")
    public String endTime;
    //审批人
    public String approver;
    //审批意见
    @SerializedName("approverOpinion")
    public String publishApprovalOpinion;
    //审批时间
    @SerializedName("approverTime")
    public String publishApprovalTime;

    //发布关联事件
    @SerializedName("relatedEvents")
    public String publishRelationEvent;
    //发布关联问题
    @SerializedName("relatedProblem")
    public String publishRelationQuestion;
    //发布关联变更
    @SerializedName("relatedChange")
    public String publishRelationChange;
    //发布关联知识
    @SerializedName("relatedKnowledge")
    public String publishRelationKnowledge;

    //附件地址
    @SerializedName("annex")
    public String publishEnclosure;

    public boolean check;
}
