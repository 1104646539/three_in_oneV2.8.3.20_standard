package com.tnd.multifuction.thread;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tnd.multifuction.activity.MainActivity;
import com.tnd.multifuction.bean.HjData;
import com.tnd.multifuction.db.DbHelper;
import com.tnd.multifuction.model.CheckResult;
import com.tnd.multifuction.net.ApiService;
import com.tnd.multifuction.net.BaseObserver;
import com.tnd.multifuction.net.RetrofitHelper;
import com.tnd.multifuction.net.RxSchedulers;
import com.tnd.multifuction.util.APPUtils;
import com.tnd.multifuction.util.Global;
import com.tnd.multifuction.util.JsonUtil;
import com.tnd.multifuction.util.ToolUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.tnd.multifuction.util.ToolUtils.isNumericZidai;

public class UploadThread extends Thread {

    private static final String TAG = "UploadThread";
    private Context context;
    private onUploadListener listener;
    private List<CheckResult> list;
    private List<HjData.Items> arr;

    public UploadThread(Context context, List<CheckResult> list, onUploadListener listener) {

        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public void run() {
        Log.d(TAG, "获取到的list=" + list.size());
        try {
            for (int i = 0; i < list.size(); i++) {
                Thread.sleep(300);
                String content = ToolUtils.assemblyUploadData(list.get(i));
                Log.d("",""+TextUtils.isEmpty(content));
                if(!TextUtils.isEmpty(content)){
                    if (TextUtils.isEmpty(content) && listener != null) {
                        listener.onFail("设备ID或上传地址为空，请输入后重新上传");
                        return;
                    }
                    Log.d("","String content:"+content);
//                APPUtils.showToast((Activity) context, content);
                    OkHttpClient okHttpClient = new OkHttpClient();

                    MediaType FORM_CONTENT_TYPE = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(FORM_CONTENT_TYPE,content);
//                RequestBody requestBody = new FormBody.Builder()
//                        .add("result", content)
//                        .build();
                    requestBody.contentType().charset(Charset.forName("gb2312"));
                    Request request = new Request.Builder().url(Global.uploadUrl)
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
                            Log.d("成功",result);
                            if (listener != null) {
                                if (result.contains("上传成功")) {
                                    listener.onSuccess(list, 1, finalI, result);
                                } else {
                                    listener.onFail(result);
                                }
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onFail("上传失败2");
            }
        }

    }

    public interface onUploadListener {

        void onSuccess(List<CheckResult> list, int returnId, int position, String result);

        void onFail(String failInfo);
    }

}
