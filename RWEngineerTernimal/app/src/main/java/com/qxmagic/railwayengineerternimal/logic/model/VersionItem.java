/*
 * 文 件 名:  VersionModel.java
 * 描    述:  版本实体类
 * 创 建 人:  李晨光
 * 创建时间:  2014年7月19日
 */
package com.qxmagic.railwayengineerternimal.logic.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 版本实体类
 *
 * @author 李晨光
 * @version [版本号, 2014年7月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class VersionItem implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    public String status;

    /**
     * 版本号
     */
    @SerializedName("versionNo")
    public String versionCode = "";

    /**
     * 版本名
     */
    @SerializedName("info")
    public String versionName = "";

    /**
     * 升级类型
     */
    @SerializedName("mustUpdate")
    public String type = "";

    /**
     * 版本描述
     */
    @SerializedName("versionRemark")
    public String description = "";

    /**
     * APK地址
     */
    @SerializedName("data")
    public String apkUrl = "";

    /**
     * 版本描述
     */
    @SerializedName("platform")
    public String platform = "";

    @Override
    public String toString() {
        return "VersionItem{" +
                "versionCode='" + versionCode + '\'' +
                ", versionName='" + versionName + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}
