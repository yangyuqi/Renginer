package com.qxmagic.railwayuserterminal.presenter.spresenter;

import android.content.Context;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.qxmagic.railwayuserterminal.RWUTApplication;
import com.qxmagic.railwayuserterminal.api.RetrofitService;
import com.qxmagic.railwayuserterminal.api.response.CommonResp;
import com.qxmagic.railwayuserterminal.api.response.OrgDataResp;
import com.qxmagic.railwayuserterminal.logic.model.UploadImageBean;
import com.qxmagic.railwayuserterminal.presenter.ipresenter.IRepairPresenter;
import com.qxmagic.railwayuserterminal.ui.iview.IRepairView;
import com.qxmagic.railwayuserterminal.utils.GsonHelper;
import com.qxmagic.railwayuserterminal.utils.ListUtil;
import com.qxmagic.railwayuserterminal.utils.ProgressUtil;
import com.qxmagic.railwayuserterminal.utils.RequestUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by Christian on 2017/3/22 0022.
 * 报修presenter
 */
public class RepairPresenter implements IRepairPresenter {
    private IRepairView mView;
    private Context mContext;

    public RepairPresenter(IRepairView mView, Context mContext) {
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
    public void checkTitle(String title) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("title", title);
        RetrofitService.checkTitle(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在加载中...", false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.repairResult("0");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                if (null != commonResp) {
                    String status = commonResp.status;
                    if ("1".equals(status)) {
                        mView.repairResult("1");
                    } else {
                        mView.repairResult("0");
                    }
                }
            }
        });
    }

    @Override
    public void uploadAttachment(ArrayList<String> fileList) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < fileList.size(); i++) {
            String path = fileList.get(i);
            File file = new File(path);
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("files", file.getName(), body);
        }
        List<MultipartBody.Part> parts = builder.build().parts();
        RetrofitService.uploadAttachment(parts).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在上传附件...", false);
            }
        }).compose(mView.<CommonResp>bindToLife()).subscribe(new Subscriber<CommonResp>() {
            @Override
            public void onCompleted() {
//                ProgressUtil.dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                ProgressUtil.dismissProgressDialog();
                Logger.e(e.toString());
                mView.uploadAttachFail("上传图片失败");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                String status = commonResp.status;
                if ("1".equals(status)) {
                    String path = (String) commonResp.commonBean;
                    if (!TextUtils.isEmpty(path)) {
                        ArrayList<UploadImageBean> imageList = (ArrayList<UploadImageBean>) GsonHelper.convertEntities(path, UploadImageBean.class);
                        StringBuilder sbFile = new StringBuilder();
                        if (!ListUtil.isEmpty(imageList)) {
                            for (int i = 0; i < imageList.size(); i++) {
                                UploadImageBean bean = imageList.get(i);
                                sbFile.append(bean.filename).append("~").append(bean.filepath);
                                if (i != imageList.size() - 1) {
                                    sbFile.append("@");
                                }
                            }
                            mView.uploadAttachSuccess(sbFile.toString().trim());
                        }
                    } else {
                        mView.uploadAttachFail("文件上传失败");
                    }
                } else {
                    ProgressUtil.dismissProgressDialog();
                    mView.uploadAttachFail(commonResp.info);
                }
            }
        });
    }


    @Override
    public void repair(HashMap<String, String> params) {
        //发送报修请求并返回结果
        RetrofitService.repair(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
//                ProgressUtil.showProgressDialog(mContext, "正在报修中...", false);
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
                mView.repairResult("2");
            }

            @Override
            public void onNext(CommonResp commonResp) {
                if (null != commonResp) {
                    String status = commonResp.status;
                    if ("1".equals(status)) {
                        mView.repairResult("3");
                    } else {
                        mView.repairResult("2");
                    }
                }
            }
        });
    }

    @Override
    public void getDataByOrg() {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("orgName", RWUTApplication.getInstance().mLoginUser.orgName);
        RetrofitService.getDataList(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "请求数据中...", false);
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
                mView.repairResult("-1");
            }

            @Override
            public void onNext(OrgDataResp orgDataResp) {
                if (null != orgDataResp) {
                    mView.getServiceList(orgDataResp.serviceList);
                    mView.getSubServiceList(orgDataResp.serviceList);
                    mView.getTeamList(orgDataResp.teamList);
                    mView.getDealManList(orgDataResp.dealManList);
                    mView.getpRequestList(orgDataResp.pEventList);
                    mView.getpEventList(orgDataResp.pEventList);
                    mView.getpQuestiontList(orgDataResp.pQuestionList);
                    mView.getpChangeList(orgDataResp.relateChangeList);
                } else {
                    mView.repairResult("-1");
                }
            }
        });
    }

    @Override
    public void getDealManList(String teamName) {
        HashMap<String, String> params = RequestUtil.getCommonParams();
        params.put("appointTeam", teamName);
        RetrofitService.getDealManList(params).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                ProgressUtil.showProgressDialog(mContext, "正在获取指派人...", false);
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
                mView.repairResult("-2");
            }

            @Override
            public void onNext(OrgDataResp resp) {
                if (resp != null) {
                    mView.getDealManList(resp.dealManList);
                }
            }
        });
    }
}
