package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/27 0027.
 * 知识bean
 */
public class KnowledgeBean implements Serializable {
    @SerializedName("recordList")
    public ArrayList<KnowledgeBean> commonList;

    public String uuid;

    /**
     * 知识名称
     */
    @SerializedName("knowName")
    public String knowledgeTitle;

    /**
     * 知识类型
     */
    @SerializedName("knowType")
    public String knowledgeType;

    /**
     * 关联问题
     */
    @SerializedName("relatedProblem")
    public String relateQuestion;

    /**
     * 创建人
     */
    @SerializedName("createPerson")
    public String knowledgeUser;

    public String knowledgePassword;
    public String knowledgeServicePsw;

    /**
     * 创建时间
     */
    @SerializedName("ctTime")
    public String knowledgeStorageTime;

    public String knowledgeNumber;

    /**
     * 描述
     */
    @SerializedName("knowDes")
    public String repairInformation;
}
