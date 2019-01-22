package com.qxmagic.railwayuserterminal.api;

import com.qxmagic.railwayuserterminal.api.response.CommonListResp;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.api.response.OrgDataResp;
import com.qxmagic.railwayuserterminal.api.response.UserResp;
import com.qxmagic.railwayuserterminal.logic.model.ContractDetailBean;
import com.qxmagic.railwayuserterminal.logic.model.EventDetailBean;
import com.qxmagic.railwayuserterminal.logic.model.VersionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 *请求方法接口
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
     * 已解决/未解决事件数量
     */
    @POST(HttpAddressProperties.GET_EVENT_COUNT)
    Observable<CommonResp<ArrayList<String>>> getEventCount(@QueryMap HashMap<String, String> map);


    /**
     * 已解决事件列表
     */
    @POST(HttpAddressProperties.GET_RESOLVED_EVENT_LIST)
    Observable<ArrayList<EventDetailBean>> getResolvedList(@QueryMap HashMap<String, String> map);

    /**
     * 未解决事件列表
     */
    @POST(HttpAddressProperties.GET_UNRESOLVED_EVENT_LIST)
    Observable<ArrayList<EventDetailBean>> getUnResolvedList(@QueryMap HashMap<String, String> map);

    /**
     * 重新指派事件列表
     */
    @POST(HttpAddressProperties.GET_RESIGN_LIST)
    Observable<CommonListResp<EventDetailBean>> getReSigndList(@QueryMap HashMap<String, String> map);

    /**
     * 事件详情
     */
    @POST(HttpAddressProperties.GET_EVENT_DETAIL)
    Observable<CommonResp<EventDetailBean>> getEventDetail(@QueryMap HashMap<String, String> map);

    /**
     * 报修-获得组织列表
     */
    @POST(HttpAddressProperties.GET_ORG_LIST)
    Observable<ArrayList<String>> getOrgList(@QueryMap HashMap<String, String> map);

    /**
     * 根据组织名称获得相应数据
     */
    @POST(HttpAddressProperties.GET_DATA_BY_ORG)
    Observable<OrgDataResp> getDataList(@QueryMap HashMap<String, String> map);

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
     * 发布上传附件
     */
    @Multipart
    @POST(HttpAddressProperties.UPLOAD_ATTACHMENT)
    Observable<CommonResp> uploadAttachment(@Part List<MultipartBody.Part> files);

    /**
     * 报修
     */
    @POST(HttpAddressProperties.REPAIR)
    Observable<CommonResp> repair(@QueryMap HashMap<String, String> map);

    /**
     * 留言
     */
    @POST(HttpAddressProperties.LEAVING_A_MESSAGE)
    Observable<CommonResp> leavingAMessage(@QueryMap HashMap<String, String> map);

    /**
     * 合同列表
     */
    @POST(HttpAddressProperties.GET_CONTRACTS)
    Observable<CommonResp<ContractDetailBean>> getContracts(@QueryMap HashMap<String, String> map);

    /**
     * 即将过期合同列表
     */
    @POST(HttpAddressProperties.GET_OVERDUE_CONTRACTS)
    Observable<CommonResp<ContractDetailBean>> getOverdueContracts(@QueryMap HashMap<String, String> map);
}
