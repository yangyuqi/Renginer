package com.qxmagic.railwayuserterminal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Christian on 2017/3/20 0020.
 * 服务合同对象
 */

public class ContractDetailBean implements Serializable {
    @SerializedName("recordList")
    public ArrayList<ContractDetailBean> contracts;
    @SerializedName("uuid")
    public String id;
    @SerializedName("conName")
    public String title;
    // 客户名称
    @SerializedName("customerName")
    public String clientName;
    //付款周期
    public String payCycle;
    //供应商
    public String supplier;
   //状态
    public String state;
    //货币单位
    public String moneyUnit;
    //描述
    public String description;
    //开始日期
    public String startTime;
    //结束日期
    public String endTime;
    //合同类型
    public String contractType;
    //合同额
    public String contractAmount;
    //货币类型
    public String currencyType;
    //添加选项
    public String addOptions;
    //合同类别(客户合同或供应商合同)
    public String contractClass;
    //SLA(保修期)
    public String sla;
    //服务耗时
    public String serviceTime;

}
