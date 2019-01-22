package com.qxmagic.railwayengineerternimal.api;

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
//		String BASE_URL = "http://192.168.75.5:8082/";

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
     * 已解决事件列表
     */
    String GET_RESOLVED_EVENT_LIST = "api/event/1.0.0/eventsInt/getEvent1";

    /**
     * 未解决事件列表
     */
    String GET_UNRESOLVED_EVENT_LIST = "api/event/1.0.0/eventsInt/getEvent2";

    /**
     * 受理当前事件
     */
    String DEAL_CURRENT_EVENT = "api/event/1.0.0/eventsBase/changeState";

    /**
     * 重新指派事件列表
     */
    String GET_RESIGN_LIST = "api/event/1.0.0/eventsInt/getEvent3";

    /**
     * 根据uuid查询事件详情
     */
    String GET_EVENT_DETAIL = "api/event/1.0.0/eventsInt/getUuid";

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
     * 新建问题--获得组织列表
     */
    String GET_ORG_LIST = "api/configure/1.0.0/configureBase/queryOrgName";

    /**
     * 根据组织名称获得相应数据
     */
    String GET_DATA_BY_ORG = "api/question/1.0.0/questionBase/queryXGCondition";

    /**
     * 获取团队列表
     */
    String GET_TEAM_LIST = "api/event/1.0.0/eventsBase/spoandappoi";

    /**
     * 根据团队名称获取办理人列表
     */
    String GET_DEAL_MAN_LIST = "api/event/1.0.0/eventsBase/queryTeamContacts";

    /**
     * 检测标题是否存在
     */
    String CHECK_TITLE = "api/event/1.0.0/eventsBase/queryTitleCZ";

    /**
     * 事件解决接口
     */
    String RESOLVE_EVENT = "api/event/1.0.0/eventsBase/save";

    /**
     * 事件重新指派接口
     */
    String REASSIGN_EVENT = "api/event/1.0.0/movevent/getZP";

    /**
     * 配置项列表
     */
    String GET_CONFIGURATION_LIST = "api/configure/1.0.0/configureBase/list";

    /**
     * 配置项详情
     */
    String GET_CONFIGURATION_DETAIL = "api/configure/1.0.0/configureBase/get";

    /**
     * 知识库列表
     */
    String GET_KNOWLEDGE_LIST = "api/knowledge/1.0.0/knowledgeBase/allEventInfo";

    /**
     * 知识库详情
     */
    String GET_KNOWLEDGE_DETAIL = "api/knowledge/1.0.0/knowledgeBase/get";

    /**
     * 知识库知识类型
     */
    String KNOWLEDGE_RELATE_TYPE = "api/serve/1.0.0/serveBase/queryServiceType";

    /**
     * 知识库关联问题
     */
    String KNOWLEDGE_RELATE_QUESTION = "api/knowledge/1.0.0/knowledgeBase/queryRequestType";

    /**
     * 添加到知识库
     */
    String ADD_TO_BASIC = "api/knowledge/1.0.0/knowledgeBase/save";

    /**
     * 发布上传附件
     */
    String UPLOAD_ATTACHMENT = "uploadFile";

    /**
     * 获得审批人列表
     */
    String GET_APPROVE_LIST = "api/event/1.0.0/movevent/getApp";

    /**
     * 获得关联知识列表
     */
    String GET_RELATE_KNOW_LIST = "api/knowledge/1.0.0/knowledgeBase/getName1";

    /**
     * 根据事件名称获得关联问题/变更
     */
    String GET_RELATE_DATA = "api/event/1.0.0/eventsBase/queryEventW";

    /**
     * 检测发布标题是否存在
     */
    String CHECK__PUBLISH_TITLE = "api/release/1.0.0/releaseBase/queryTitleCZ";

    /**
     * 新建发布
     */
    String NEW_PUBLISH = "api/release/1.0.0/releaseBase/save";

    /**
     * 检测变更标题是否存在
     */
    String CHECK_CHANGE_TITLE = "api/variation/1.0.0/variationBase/queryTitleCZ";

    /**
     * 新建变更
     */
    String NEW_CHANGE = "api/variation/1.0.0/variationBase/save";

    /**
     * 检测问题标题是否存在
     */
    String CHECK_QUESTION_TITLE = "api/question/1.0.0/questionBase/queryTitleCZ";

    /**
     * 新建问题
     */
    String NEW_QUESTION = "api/question/1.0.0/questionBase/save";

    /**
     * 服务台处问题列表
     */
    String GET_QUESTION_LIST = "api/question/1.0.0/questionBase/listQu";

    /**
     * 获取事件总数接口
     */
    String GET_ALL_EVENT_COUNT = "api/event/1.0.0/eventsInt/getNume1";

    /**
     * 获取未完成事件数量接口
     */
    String GET_UNRESOLVED_EVENT_COUNT = "api/event/1.0.0/eventsInt/getNume2";

    /**
     * 获取问题数量
     */
    String GET_QUESTION_COUNT = "api/event/1.0.0/eventsInt/getNume3";

    /**
     * 获取配置项数量
     */
    String GET_CONFIGURATION_COUNT = "api/configure/1.0.0/configureBase/getPnumber";
}
