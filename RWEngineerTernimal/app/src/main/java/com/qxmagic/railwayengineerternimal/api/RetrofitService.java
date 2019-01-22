package com.qxmagic.railwayengineerternimal.api;

import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.RWETApplication;
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
import com.qxmagic.railwayengineerternimal.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Christian on 2017/3/16 0016.
 * 网络请求，调用之前必须先初始化 Application中初始化一次即可
 */

public class RetrofitService {
    //日志标识
    private static final String TAG = "-----RetroficService----";

    //设缓存有效期为1天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 1;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    //查询网络的Cache-Control设置
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";

    private static INetService mNetService;

    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化该网络请求类
     */
    public static void init() {
        //指定缓存路径，设置缓存大小为100M
        Cache cache = new Cache(new File(RWETApplication.getInstance().getApplicationContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .addInterceptor(sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpAddressProperties.BASE_URL)
                .build();
        mNetService = retrofit.create(INetService.class);
    }

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer buffer = new Buffer();
            if (null != request.body()) {
                request.body().writeTo(buffer);
            } else {
                Logger.d(TAG + "request.body() == null");
            }
            //打印url信息
            Logger.w(request.url() + ""/* + (request.body() != null ? "?" + _parseParams(request.body(), buffer) : "")*/);
            return chain.proceed(request);
        }
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor sRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(RWETApplication.getInstance().getApplicationContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Logger.e("no network");
            }
            Response originalResponse = chain.proceed(request);

            if (NetUtil.isNetworkAvailable(RWETApplication.getInstance().getApplicationContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, " + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    //登录
    public static Observable<UserResp> login(String phone, String password) {
        return mNetService.login(phone, password).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //修改密码
    public static Observable<CommonResp> modifyPassword(HashMap<String, String> map) {
        return mNetService.modifyPassword(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //检测是否有版本更新
    public static Observable<VersionItem> checkUpdate(HashMap<String, String> params) {
        return mNetService.checkUpdate(params).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //已解决事件列表
    public static Observable<CommonListResp<EventDetailBean>> getResolvedList(HashMap<String, String> map) {
        return mNetService.getResolvedList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //未解决事件列表
    public static Observable<CommonListResp<EventDetailBean>> getUnResolvedList(HashMap<String, String> map) {
        return mNetService.getUnResolvedList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //受理当前事件
    public static Observable<CommonResp> dealCurrentEvent(HashMap<String, String> map) {
        return mNetService.dealCurrentEvent(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //重新指派事件列表
    public static Observable<CommonListResp<EventDetailBean>> getReSigndList(HashMap<String, String> map) {
        return mNetService.getReSigndList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //事件详情
    public static Observable<EventDetailBean> getEventDetail(HashMap<String, String> map) {
        return mNetService.getEventDetail(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据未解决事件详情title查询关联问题
    public static Observable<ArrayList<QuestionDetailBean>> getRelateQuestionList(HashMap<String, String> map) {
        return mNetService.getRelateQuestionList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据未解决事件详情title查询关联变更
    public static Observable<ArrayList<ChangeDetailBean>> getRelateChangeList(HashMap<String, String> map) {
        return mNetService.getRelateChangeList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据未解决事件详情title查询关联问题
    public static Observable<ArrayList<PublishDetailBean>> getRelatePublishList(HashMap<String, String> map) {
        return mNetService.getRelatePublishList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //新建问题--获得组织列表
    public static Observable<ArrayList<String>> getOrgList(HashMap<String, String> map) {
        return mNetService.getOrgList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据组织名称获得相应数据
    public static Observable<OrgDataResp> getDataList(HashMap<String, String> map) {
        return mNetService.getDataList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取团队列表
    public static Observable<OrgDataResp> getTeamList(HashMap<String, String> map) {
        return mNetService.getTeamList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据团队名称获取办理人列表
    public static Observable<OrgDataResp> getDealManList(HashMap<String, String> map) {
        return mNetService.getDealManList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //检测标题是否存在
    public static Observable<CommonResp> checkTitle(HashMap<String, String> map) {
        return mNetService.checkTitle(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //事件已解决
    public static Observable<CommonResp> resolveEvent(HashMap<String, String> map) {
        return mNetService.resolveEvent(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //事件重新指派接口
    public static Observable<CommonResp> reassignEvent(HashMap<String, String> map) {
        return mNetService.reassignEvent(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //配置项列表
    public static Observable<CommonResp<ConfigurationBean>> getConfigurationdList(HashMap<String, String> map) {
        return mNetService.getConfigurationdList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //配置项详情
    public static Observable<ConfigurationBean> getConfigurationdDetail(HashMap<String, String> map) {
        return mNetService.getConfigurationdDetail(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //知识库列表
    public static Observable<CommonResp<KnowledgeBean>> getKnowledgeList(HashMap<String, String> map) {
        return mNetService.getKnowledgeList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //知识库详情
    public static Observable<KnowledgeBean> getKnowledgeDetail(HashMap<String, String> map) {
        return mNetService.getKnowledgeDetail(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //知识库知识类型
    public static Observable<ArrayList<String>> getKnowledgeTypeList(HashMap<String, String> map) {
        return mNetService.getKnowledgeTypeList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //知识库关联问题
    public static Observable<ArrayList<String>> getKnowledgeQuestionList(HashMap<String, String> map) {
        return mNetService.getKnowledgeQuestionList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //添加到知识库
    public static Observable<CommonResp> addToBasic(HashMap<String, String> map) {
        return mNetService.addToBasic(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //发布上传附件
    public static Observable<CommonResp> uploadAttachment(MultipartBody.Part part) {
        return mNetService.uploadAttachment(part).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获得审批人列表
    public static Observable<CommonResp<ArrayList<String>>> getApproveList(HashMap<String, String> map) {
        return mNetService.getApproveList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获得关联知识列表
    public static Observable<CommonResp<ArrayList<String>>> getRelateKnowList(HashMap<String, String> map) {
        return mNetService.getRelateKnowList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //根据事件名称获得关联问题
    public static Observable<OrgDataResp> getRelateDataList(HashMap<String, String> map) {
        return mNetService.getRelateDataList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //检测标题是否存在
    public static Observable<CommonResp> checkPublishTitle(HashMap<String, String> map) {
        return mNetService.checkPublishTitle(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //新建发布
    public static Observable<CommonResp> newPublish(HashMap<String, String> map) {
        return mNetService.newPublish(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //检测变更标题是否存在
    public static Observable<CommonResp> checkChangeTitle(HashMap<String, String> map) {
        return mNetService.checkChangeTitle(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    //新建变更
    public static Observable<CommonResp> newChange(HashMap<String, String> map) {
        return mNetService.newChange(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //检测问题标题是否存在
    public static Observable<CommonResp> checkQuestionTitle(HashMap<String, String> map) {
        return mNetService.checkQuestionTitle(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //新建问题
    public static Observable<CommonResp> newQuestion(HashMap<String, String> map) {
        return mNetService.newQuestion(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //服务台处问题列表
    public static Observable<CommonResp<QuestionDetailBean>> getQuestionList(HashMap<String, String> map) {
        return mNetService.getQuestionList(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取事件总数接口
    public static Observable<CommonResp<String>> getAllEventCount(HashMap<String, String> map) {
        return mNetService.getAllEventCount(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取未完成事件数量接口
    public static Observable<CommonResp<String>> getUnresolvedEventCount(HashMap<String, String> map) {
        return mNetService.getUnresolvedEventCount(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取问题数量
    public static Observable<CommonResp<String>> getQuestionCount(HashMap<String, String> map) {
        return mNetService.getQuestionCount(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取配置项数量
    public static Observable<CommonResp<String>> getConfigurationCount(HashMap<String, String> map) {
        return mNetService.getConfigurationCount(map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
