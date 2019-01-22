package com.qxmagic.railwayuserterminal.api;

/**
 * Created by Christian on 2017/7/24 0024.
 * 用于存放程序所有接口请求地址
 */

public interface HttpAddressProperties {
    /**
     * 基础请求地址 地址后面需要跟上“/”
     */
    //线上环境
    String BASE_URL = "http://121.196.218.11:8081/";

    //开发环境
//    String BASE_URL = "http://192.168.75.5:8082/";

    /**
     * 登录接口
     */
    String LOGIN = "api/user/1.0.0/login";

    /**
     * 修改密码
     */
    String MODIFY_PASSWORD = "api/user/1.0.0/update";

    /**
     * 检查是否有更新接口
     */
    String CHECK_UPDATE = "edition/1.0.0/edition/checkVersion";

    /**
     * 已解决/未解决事件数量
     */
    String GET_EVENT_COUNT = "api/event/1.0.0/movevent/getResolveNum";

    /**
     * 已解决事件列表
     */
    String GET_RESOLVED_EVENT_LIST = "api/event/1.0.0/movevent/resolve1";

    /**
     * 未解决事件列表
     */
    String GET_UNRESOLVED_EVENT_LIST = "api/event/1.0.0/movevent/resolve2";

    /**
     * 重新指派事件列表
     */
    String GET_RESIGN_LIST = "api/event/1.0.0/eventsInt/getEvent3";

    /**
     * 根据uuid查询事件详情
     */
    String GET_EVENT_DETAIL = "api/event/1.0.0/eventsBase/get";

    /**
     * 根据未解决事件详情title查询关联问题
     */
    String GET_RELATE_QUESTION_LIST = "api/event/1.0.0/eventsInt/getQ";

    /**
     * 根据未解决事件详情title查询关联变更
     */
    String GET_RELATE_CHANGE_LIST = "api/event/1.0.0/eventsInt/getV";

    /**
     * 根据未解决事件详情title查询关联发布
     */
    String GET_RELATE_PUBLISH_LIST = "api/event/1.0.0/eventsInt/getR";

    /**
     * 报修--获得组织列表
     */
    String GET_ORG_LIST = "api/event/1.0.0/movevent/orgName";

    /**
     * 根据组织名称获得相应数据
     */
    String GET_DATA_BY_ORG = "api/event/1.0.0/eventsBase/queryModifyEvent";

    /**
     * 根据团队名称获取办理人列表
     */
    String GET_DEAL_MAN_LIST = "api/event/1.0.0/eventsBase/queryTeamContacts";

    /**
     * 检测标题是否存在
     */
    String CHECK_TITLE = "api/event/1.0.0/eventsBase/queryTitleCZ";

    /**
     * 报修上传图片
     */
    String UPLOAD_ATTACHMENT = "uploadFiles";

    /**
     * 报修
     */
    String REPAIR = "api/event/1.0.0/eventsBase/save";

    /**
     * 留言
     */
    String LEAVING_A_MESSAGE = "api/event/1.0.0/eventsBase/save1";

    /**
     * 合同列表
     */
    String GET_CONTRACTS = "api/contract/1.0.0/contractBase/allEventInfo";

    /**
     * 即将过期合同列表
     */
    String GET_OVERDUE_CONTRACTS = "api/contract/1.0.0/contractBase/getContract";
}
