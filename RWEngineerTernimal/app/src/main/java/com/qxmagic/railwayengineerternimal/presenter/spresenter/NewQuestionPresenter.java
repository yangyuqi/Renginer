package com.qxmagic.railwayengineerternimal.presenter.spresenter;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayengineerternimal.api.RetrofitService;
import com.qxmagic.railwayengineerternimal.api.response.CommonResp;
import com.qxmagic.railwayengineerternimal.api.response.OrgDataResp;
import com.qxmagic.railwayengineerternimal.logic.model.UploadImageBean;
import com.qxmagic.railwayengineerternimal.presenter.ipresenter.INewVariousPresenter;
import com.qxmagic.railwayengineerternimal.ui.iview.INewVariousView;
import com.qxmagic.railwayengineerternimal.utils.GsonHelper;
import com.qxmagic.railwayengineerternimal.utils.ProgressUtil;
import com.qxmagic.railwayengineerternimal.utils.RequestUtil;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/30 0030.
 * 新建问题presenter
 */
public class NewQuestionPresenter implements INewVariousPresenter {
    private INewVariousView mView;
    private Context mContext;

    public NewQuestionPresenter(INewVariousView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }


    @Override
    public void newVarious(HashMap<String, String> params) {
        RetrofitService.newQuestion(params).doOnSubscribe(new Action0() {
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
        RetrofitService.checkQuestionTitle(map).doOnSubscribe(new Action0() {
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
        RetrofitService.getTeamList(RequestUtil.getCommonParams()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
            }
        }).compose(mView.<OrgDataResp>bindToLife()).subscribe(new Subscriber<OrgDataResp>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
            }

            @Override
            public void onNext(OrgDataResp resp) {
                if(resp!=null){
                    mView.getTeamList(resp.teamList);
                }
            }
        });
    }

    @Override
    public void getDataByOrgName(String orgName) {
        HashMap<String,String>map=RequestUtil.getCommonParams();
        map.put("orgName",orgName);
        RetrofitService.getDataList(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"获取数据中...",false);
            }
        }).compose(mView.<OrgDataResp>bindToLife()).subscribe(new Subscriber<OrgDataResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
            }

            @Override
            public void onNext(OrgDataResp dataResp) {
                if(null!=dataResp){
                    mView.getServiceList(dataResp.serviceList);
                    mView.getSubServiceList(dataResp.serviceList);
                    mView.getQuesTypeList(dataResp.questionTypeList);
                    mView.getRelateEventList(dataResp.relateEventList);
                    mView.getRelateChangeList(dataResp.relateChangeList);
//                    mView.getTeamList(dataResp.teamList);
//                    mView.getDealManList(dataResp.dealManList);
                }
            }
        });
    }

    @Override
    public void getDealManByTeam(String team) {
        HashMap<String,String> map=RequestUtil.getCommonParams();
        map.put("appointTeam",team);
        RetrofitService.getDealManList(map).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext,"正在获取办理人列表...",false);
            }
        }).compose(mView.<OrgDataResp>bindToLife()).subscribe(new Subscriber<OrgDataResp>() {
            @Override
            public void onCompleted() {
                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                Logger.e(e.toString());
                ProgressUtil.dismissProgressDialog();
                mView.publishFail("获取办理人列表失败，请检查您的网络");
            }

            @Override
            public void onNext(OrgDataResp resp) {
                if(null!=resp){
                    mView.getDealManList(resp.dealManList);
                }
            }
        });
    }

}
