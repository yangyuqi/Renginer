package com.qxmagic.railwayengineerternimal.api;

import com.qxmagic.railwayengineerternimal.api.response.CommonListResp;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.api.response.OrgDataResp;
import com.qxmagic.railwayengineerternimal.api.response.UserResp;
import com.qxmagic.railwayengineerternimal.logic.model.ChangeDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.ConfigurationBean;
import com.qxmagic.railwayengineerternimal.logic.model.EventDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.KnowledgeBean;
import com.qxmagic.railwayengineerternimal.logic.model.PublishDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.QuestionDetailBean;
import com.qxmagic.railwayengineerternimal.logic.model.VersionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Christian on 2017/3/16 0016.
 * 请求方法接口
 */

public interface INetService {
    /**
     * 登录接口
     *
     * @param phone    登录用户名
     * @param password 登录密码
     * @return 返回公共对象
     */
//    @Headers(RetrofitService.CACHE_CONTROL_NETWORK)
    @POST(HttpAddressProperties.LOGIN)
    Observable<UserResp> login(@Query("loginName") String phone, @Query("password") String password);

    /**
     * 修改密码
     */
    @POST(HttpAddressProperties.MODIFY_PASSWORD)
    Observable<CommonResp> modifyPassword(@QueryMap HashMap<String, String> map);

    /**
     * 检测是否有版本更新
     */
    @POST(HttpAddressProperties.CHECK_UPDATE)
    Observable<VersionItem> checkUpdate(@QueryMap Map<String, String> params);

    /**
     * 已解决事件列表
     */
    @POST(HttpAddressProperties.GET_RESOLVED_EVENT_LIST)
    Observable<CommonListResp<EventDetailBean>> getResolvedList(@QueryMap HashMap<String, String> map);

    /**
     * 未解决事件列表
     */
    @POST(HttpAddressProperties.GET_UNRESOLVED_EVENT_LIST)
    Observable<CommonListResp<EventDetailBean>> getUnResolvedList(@QueryMap HashMap<String, String> map);

    /**
     * 受理当前事件
     */
    @POST(HttpAddressProperties.DEAL_CURRENT_EVENT)
    Observable<CommonResp> dealCurrentEvent(@QueryMap HashMap<String, String> map);

    /**
     * 重新指派事件列表
     */
    @POST(HttpAddressProperties.GET_RESIGN_LIST)
    Observable<CommonListResp<EventDetailBean>> getReSigndList(@QueryMap HashMap<String, String> map);

    /**
     * 事件详情
     */
    @POST(HttpAddressProperties.GET_EVENT_DETAIL)
    Observable<EventDetailBean> getEventDetail(@QueryMap HashMap<String, String> map);

    /**
     * 根据未解决事件详情title查询关联问题
     */
    @POST(HttpAddressProperties.GET_RELATE_QUESTION_LIST)
    Observable<ArrayList<QuestionDetailBean>> getRelateQuestionList(@QueryMap HashMap<String, String> map);

    /**
     * 根据未解决事件详情title查询关联变更
     */
    @POST(HttpAddressProperties.GET_RELATE_CHANGE_LIST)
    Observable<ArrayList<ChangeDetailBean>> getRelateChangeList(@QueryMap HashMap<String, String> map);

    /**
     * 根据未解决事件详情title查询关联发布
     */
    @POST(HttpAddressProperties.GET_RELATE_PUBLISH_LIST)
    Observable<ArrayList<PublishDetailBean>> getRelatePublishList(@QueryMap HashMap<String, String> map);

    /**
     * 新建问题--获得组织列表
     */
    @POST(HttpAddressProperties.GET_ORG_LIST)
    Observable<ArrayList<String>> getOrgList(@QueryMap HashMap<String, String> map);

    /**
     * 根据组织名称获得相应数据
     */
    @POST(HttpAddressProperties.GET_DATA_BY_ORG)
    Observable<OrgDataResp> getDataList(@QueryMap HashMap<String, String> map);

    /**
     * 获取团队列表
     */
    @POST(HttpAddressProperties.GET_TEAM_LIST)
    Observable<OrgDataResp> getTeamList(@QueryMap HashMap<String, String> map);

    /**
     * 根据团队名称获取办理人列表
     */
    @POST(HttpAddressProperties.GET_DEAL_MAN_LIST)
    Observable<OrgDataResp> getDealManList(@QueryMap HashMap<String, String> map);

    /**
     * 检测标题是否存在
     */
    @POST(HttpAddressProperties.CHECK_TITLE)
    Observable<CommonResp> checkTitle(@QueryMap HashMap<String, String> map);

    /**
     * 事件已解决
     */
    @POST(HttpAddressProperties.RESOLVE_EVENT)
    Observable<CommonResp> resolveEvent(@QueryMap HashMap<String, String> map);

    /**
     * 事件重新指派接口
     */
    @POST(HttpAddressProperties.REASSIGN_EVENT)
    Observable<CommonResp> reassignEvent(@QueryMap HashMap<String, String> map);

    /**
     * 配置项列表
     */
    @POST(HttpAddressProperties.GET_CONFIGURATION_LIST)
    Observable<CommonResp<ConfigurationBean>> getConfigurationdList(@QueryMap HashMap<String, String> map);

    /**
     * 配置项详情
     */
    @POST(HttpAddressProperties.GET_CONFIGURATION_DETAIL)
    Observable<ConfigurationBean> getConfigurationdDetail(@QueryMap HashMap<String, String> map);

    /**
     * 知识库列表
     */
    @POST(HttpAddressProperties.GET_KNOWLEDGE_LIST)
    Observable<CommonResp<KnowledgeBean>> getKnowledgeList(@QueryMap HashMap<String, String> map);

    /**
     * 知识库详情
     */
    @POST(HttpAddressProperties.GET_KNOWLEDGE_DETAIL)
    Observable<KnowledgeBean> getKnowledgeDetail(@QueryMap HashMap<String, String> map);

    /**
     * 知识库知识类型
     */
    @POST(HttpAddressProperties.KNOWLEDGE_RELATE_TYPE)
    Observable<ArrayList<String>> getKnowledgeTypeList(@QueryMap HashMap<String, String> map);

    /**
     * 知识库关联问题
     */
    @POST(HttpAddressProperties.KNOWLEDGE_RELATE_QUESTION)
    Observable<ArrayList<String>> getKnowledgeQuestionList(@QueryMap HashMap<String, String> map);

    /**
     * 添加到知识库
     */
    @POST(HttpAddressProperties.ADD_TO_BASIC)
    Observable<CommonResp> addToBasic(@QueryMap HashMap<String, String> map);

    /**
     * 发布上传附件
     */
    @Multipart
    @POST(HttpAddressProperties.UPLOAD_ATTACHMENT)
    Observable<CommonResp> uploadAttachment(@Part MultipartBody.Part file);

    /**
     * 获得审批人列表
     */
    @POST(HttpAddressProperties.GET_APPROVE_LIST)
    Observable<CommonResp<ArrayList<String>>> getApproveList(@QueryMap HashMap<String, String> map);

    /**
     * 获得关联知识列表
     */
    @POST(HttpAddressProperties.GET_RELATE_KNOW_LIST)
    Observable<CommonResp<ArrayList<String>>> getRelateKnowList(@QueryMap HashMap<String, String> map);

    /**
     * 根据事件名称获得关联问题
     */
    @POST(HttpAddressProperties.GET_RELATE_DATA)
    Observable<OrgDataResp> getRelateDataList(@QueryMap HashMap<String, String> map);

    /**
     * 检测发布标题是否存在
     */
    @POST(HttpAddressProperties.CHECK__PUBLISH_TITLE)
    Observable<CommonResp> checkPublishTitle(@QueryMap HashMap<String, String> map);

    /**
     * 新建发布
     */
    @POST(HttpAddressProperties.NEW_PUBLISH)
    Observable<CommonResp> newPublish(@QueryMap HashMap<String, String> map);

    /**
     * 检测变更标题是否存在
     */
    @POST(HttpAddressProperties.CHECK_CHANGE_TITLE)
    Observable<CommonResp> checkChangeTitle(@QueryMap HashMap<String, String> map);

    /**
     * 新建变更
     */
    @POST(HttpAddressProperties.NEW_CHANGE)
    Observable<CommonResp> newChange(@QueryMap HashMap<String, String> map);

    /**
     * 检测问题标题是否存在
     */
    @POST(HttpAddressProperties.CHECK_QUESTION_TITLE)
    Observable<CommonResp> checkQuestionTitle(@QueryMap HashMap<String, String> map);

    /**
     * 新建问题
     */
    @POST(HttpAddressProperties.NEW_QUESTION)
    Observable<CommonResp> newQuestion(@QueryMap HashMap<String, String> map);

    /**
     * 服务台处问题列表
     */
    @POST(HttpAddressProperties.GET_QUESTION_LIST)
    Observable<CommonResp<QuestionDetailBean>> getQuestionList(@QueryMap HashMap<String, String> map);

    /**
     * 获取事件总数接口
     */
    @POST(HttpAddressProperties.GET_ALL_EVENT_COUNT)
    Observable<CommonResp<String>> getAllEventCount(@QueryMap HashMap<String, String> map);

    /**
     * 获取未完成事件数量接口
     */
    @POST(HttpAddressProperties.GET_UNRESOLVED_EVENT_COUNT)
    Observable<CommonResp<String>> getUnresolvedEventCount(@QueryMap HashMap<String, String> map);

    /**
     * 获取问题数量
     */
    @POST(HttpAddressProperties.GET_QUESTION_COUNT)
    Observable<CommonResp<String>> getQuestionCount(@QueryMap HashMap<String, String> map);

    /**
     * 获取配置项数量
     */
    @POST(HttpAddressProperties.GET_CONFIGURATION_COUNT)
    Observable<CommonResp<String>> getConfigurationCount(@QueryMap HashMap<String, String> map);
}
