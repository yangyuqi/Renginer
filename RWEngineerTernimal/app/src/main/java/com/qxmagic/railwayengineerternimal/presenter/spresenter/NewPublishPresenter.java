package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.logic.model.UploadImageBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.INewPublishView;
import com.qxmagic.railwayengineerternimal.utils.GsonHelper;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建发布presenter
 */
public class NewPublishPresenter implements INewVariousPresenter {
    private INewPublishView mView;
    private Context mContext;

    public NewPublishPresenter(INewPublishView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {
        RetrofitService.getApproveList(RequestUtil.getCommonParams()).
                compose(mView.<CommonResp<ArrayList<String>>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ArrayList<String>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                mView.publishFail("获取审批人列表失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp<ArrayList<String>> strings) {
                mView.getApproveList(strings.commonBean);
            }
        });
        RetrofitService.getRelateKnowList(RequestUtil.getCommonParams()).
                compose(mView.<CommonResp<ArrayList<String>>>bindToLife()).
                subscribe(new Subscriber<CommonResp<ArrayList<String>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                mView.publishFail("获取关联知识列表失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp<ArrayList<String>> strings) {
                mView.getKnowLedgeList(strings.commonBean);
            }
        });
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void newVarious(HashMap<String, String> params) {
        //发送发布请求并返回结果
        RetrofitService.newPublish(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在发布中...",false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.publishFail("发布失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status=commonResp.status;
                if("1".equals(status)){
                    mView.publishSuccess();
                }else{
                    mView.publishFail(commonResp.info);
                }
            }
        });
    }

    @Override
    public void uploadAttachment(String filePath) {
        File file = new File(filePath);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        RetrofitService.uploadAttachment(part).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在上传附件...", false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
//                if (e.getMessage().equals("timeout")) {
//                    mView.uploadAttachFail("连接超时，请检查您的网咯");
//                }
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    String path = (String) commonResp.commonBean;
                    if (!TextUtils.isEmpty(path)) {
                        UploadImageBean bean = GsonHelper.convertEntity(path, UploadImageBean.class);
                        if (null != bean) {
                            String portraitPath = bean.filepath + "/" + bean.filename;
                            mView.uploadAttachSuccess(bean.filepath);
                        }
                    }
                } else {
                    mView.uploadAttachFail("文件上传失败");
                }
            }
        });
    }

    @Override
    public void checkTitle(String title) {
        HashMap<String,String> map=RequestUtil.getCommonParams();
        map.put("title",title);
        RetrofitService.checkPublishTitle(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在校验标题中...",false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.checkTitle("校验标题失败，请检查您的网络");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status=commonResp.status;
                if("1".equals(status)){
                    mView.checkTitle("okay");
                }else{
                    mView.checkTitle("标题已存在");
                }
            }
        });
    }

    @Override
    public void getOgList() {

    }

    @Override
    public void getDataByOrgName(String orgName) {
//        HashMap<String,String> params=RequestUtil.getCommonParams();
//        params.put("event",orgName);
//        RetrofitService.getRelateDataList(params).doOnSubscribe(new Action0() {
//            @Override
//            public void call() {
//                ProgressUtil.showProgressDialog(mContext,"正在加载中...",false);
//            }
//        }).compose(mView.<OrgDataResp>bindToLife()).subscribe(new Subscriber<OrgDataResp>() {
//            @Override
//            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ProgressUtil.dismissProgressDialog();
//                Logger.e(e.toString());
//                mView.publishFail("获取关联内容列表失败，请检查您的网络");
//            }
//
//            @Override
//            public void onNext(OrgDataResp dataResp) {
//
//            }
//        });
    }

    @Override
    public void getDealManByTeam(String team) {

    }

}
