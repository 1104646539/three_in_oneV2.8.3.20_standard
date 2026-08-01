package com.tnd.multifuction.thread;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tnd.multifuction.bean.HjData;
import com.tnd.multifuction.model.CheckResult;
import com.tnd.multifuction.model.YNMUploadItemModel;
import com.tnd.multifuction.model.YNMUploadResultModel;
import com.tnd.multifuction.util.APPUtils;
import com.tnd.multifuction.util.Global;
import com.tnd.multifuction.util.OAuthAuthenticator;
import com.tnd.multifuction.util.ToolUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 云农贸
 */
public class UploadThread2 extends Thread {

    private static final String TAG = "UploadThread2";
    private Context context;
    private onUploadListener listener;
    private List<CheckResult> list;

    public UploadThread2(Context context, List<CheckResult> list, onUploadListener listener) {

        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public void run() {
        Log.d(TAG, "获取到的list=" + list.size());
        try {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).isSelected) continue;
                Thread.sleep(300);
                String content = transToUploadModel(list.get(i));
                Log.d("UploadThread2", "" + TextUtils.isEmpty(content));
                if (TextUtils.isEmpty(content) && listener != null) {
                    listener.onFail("设备ID或上传地址为空，请输入后重新上传");
                    return;
                }
                Log.d("", "String content:" + content);
//                APPUtils.showToast((Activity) context, content);
                OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

                MediaType FORM_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
                RequestBody requestBody = RequestBody.create(FORM_CONTENT_TYPE, content);
                requestBody.contentType().charset(Charset.forName("gb2312"));
                Request request = new Request.Builder().url(Global.YNM_BaseUrl)
                        .post(requestBody).build();
                Call call = okHttpClient.newCall(request);
                int finalI = i;
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (listener != null) {
                            listener.onFail(e.getMessage());
                        }
                        Log.d("失败", e.toString());
                        APPUtils.showToast((Activity) context, e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.d("成功", result);
//                        try {
                            YNMUploadResultModel um = new Gson().fromJson(result, YNMUploadResultModel.class);
                            if (listener != null) {
                                if (um.getStatus() == 1) {
                                    listener.onSuccess(list, 1, finalI, result);
                                } else {
                                    listener.onFail("上传失败:" + um.getMessage());
                                }
                            }
//                        } catch (Exception e) {
//                            if (listener != null) {
//                                listener.onFail("失败：" + result);
//                            }
//                        }

                    }
                });
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onFail("上传失败2");
            }
        }
    }

    public static String transToUploadModel(CheckResult checkResult) {
        if (Global.YNM_BaseUrl == null || Global.YNM_BaseUrl.isEmpty() || Global.ASSET_CODE == null || Global.ASSET_CODE.isEmpty()) {
            return "";
        }
        String time = com.tnd.jinbiao.ToolUtils.dateToString(new Date(checkResult.testTime), "yyyy-MM-dd");
        int result = 0;
        if (checkResult.resultJudge.contains("不合格") || checkResult.resultJudge.contains("阳性")) {
            result = 1;
        }
        YNMUploadItemModel ynmUploadItemModel = new YNMUploadItemModel(Global.ASSET_CODE, checkResult.bcheckedOrganization, checkResult.sampleSource,
                checkResult.sampleType, checkResult.sampleName, checkResult.projectName, time, "1", result, "");
        return new Gson().toJson(ynmUploadItemModel);
    }

    public interface onUploadListener {

        void onSuccess(List<CheckResult> list, int returnId, int position, String result);

        void onFail(String failInfo);
    }
}
