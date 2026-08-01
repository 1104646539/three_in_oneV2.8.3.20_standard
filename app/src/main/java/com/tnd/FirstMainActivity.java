package com.tnd;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tnd.bean.AppInfoBean;
import com.tnd.jinbiao.activity.JbMainActivity;
import com.tnd.multifuction.R;
import com.tnd.multifuction.activity.MainActivity;
import com.tnd.multifuction.model.YNMLoginModel;
import com.tnd.multifuction.util.Global;
import com.tnd.multifuction.util.LoggingInterceptor;
import com.tnd.multifuction.util.ToolUtils;
import com.tnd.util.APPUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FirstMainActivity extends Activity implements View.OnClickListener {
    private Button btn_open_1;// 农残模块
    private Button btn_open_2;// 比色法模块
    private Button btn_open_3;// 金标卡模块
    private Button btn_open_4;// ATP荧光模块
    private long exitTime = 0;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_first_main);
        initView();
//        loginYNM();
    }

    /**
     * 登录云农贸
     */
    private void loginYNM() {

        if (TextUtils.isEmpty(Global.YNM_BaseUrl)) {
            com.tnd.multifuction.util.APPUtils.showToast(this, "设备ID或上传地址为空，请输入后重新上传");
            return;
        }
        Log.d("FirstMainActivity", "YNM_BaseUrl:" + Global.YNM_BaseUrl);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("grant_type", "client_credentials")
                .addFormDataPart("client_id", Global.YNM_APP_ID)
                .addFormDataPart("client_secret", Global.YNM_APP_PW)
                .addFormDataPart("scope", "all")
                .build();

        Request request = new Request.Builder().url(Global.YNM_BaseUrl + Global.YNM_Login)
                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("失败", e.toString());
                com.tnd.multifuction.util.APPUtils.showToast(FirstMainActivity.this, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("onResponse", "onResponse=" + result);
                if (result == null || result.isEmpty()) {
                    com.tnd.multifuction.util.APPUtils.showToast(FirstMainActivity.this, "登录失败");
                } else {
                    try {
                        YNMLoginModel ynmLoginModel = new Gson().fromJson(result, YNMLoginModel.class);
                        if ((ynmLoginModel.getError() == null || ynmLoginModel.getError().isEmpty())
                                && (ynmLoginModel.getErrCode() == null || ynmLoginModel.getErrCode().isEmpty())) {
                            //登录成功
                            com.tnd.multifuction.util.APPUtils.showToast(FirstMainActivity.this, "登录成功");
                            Global.YNM_Token = ynmLoginModel.getAccess_token();
                            Log.d("登录成功", "Global.YNM_Token=" + Global.YNM_Token);
                        } else {
                            com.tnd.multifuction.util.APPUtils.showToast(FirstMainActivity.this, "登录失败:" + ynmLoginModel.getError_description() + ynmLoginModel.getErrMsg());
                        }
                    } catch (Exception e) {
                        com.tnd.multifuction.util.APPUtils.showToast(FirstMainActivity.this, "登录失败:" + e.toString());

                    }
                }
            }
        });

    }


    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("多功能食品安全检测仪" + " V" + ToolUtils.getLocalVersionName(this) );
        btn_open_1 = (Button) findViewById(R.id.btn_open_1);
        btn_open_2 = (Button) findViewById(R.id.btn_open_2);
        btn_open_3 = (Button) findViewById(R.id.btn_open_3);
        btn_open_4 = (Button) findViewById(R.id.btn_open_4);
        btn_open_1.setOnClickListener(this);
        btn_open_2.setOnClickListener(this);
        btn_open_3.setOnClickListener(this);
        btn_open_4.setOnClickListener(this);
    }

    /**
     * 四个按钮的点击时间
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        openApp(v);
    }

    /**
     * 打开app
     *
     * @param v
     */
    private void openApp(View v) {
        List<AppInfoBean> allAppInfo = APPUtils
                .getAllAppInfo(FirstMainActivity.this);
        switch (v.getId()) {
            case R.id.btn_open_1:// 打开农残
                // com.nong.nongcan:就是包名
                for (int i = 0; i < allAppInfo.size(); i++) {
                    if (allAppInfo.get(i).packageName.equals("com.val.pesticide")) {
                        // Intent intent = getPackageManager()
                        // .getLaunchIntentForPackage("com.nong.nongcan");
                        Intent intent = new Intent()
                                .setComponent(new ComponentName(
                                        "com.val.pesticide",
                                        "com.val.pesticide.activity.WelcomActivity"));
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }

                }

                break;
            case R.id.btn_open_2:// 打开多功能
//                for (int i = 0; i < allAppInfo.size(); i++) {
//                    if (allAppInfo.get(i).packageName
//                            .equals("com.tnd.multifuction")) {
//                        // Intent intent = getPackageManager()
//                        // .getLaunchIntentForPackage("com.more.morefunction");
//
//                        Intent intent = new Intent()
//                                .setComponent(new ComponentName(
//                                        "com.tnd.multifuction",
//                                        "com.tnd.multifuction.activity.MainActivity"));
//                        if (intent != null) {
//                            startActivity(intent);
//                        }
//                    }
//
//                }
                startActivity(new Intent(FirstMainActivity.this, MainActivity.class));
                break;
            case R.id.btn_open_3:// 打开金标
                startActivity(new Intent(FirstMainActivity.this, JbMainActivity.class));
//                for (int i = 0; i < allAppInfo.size(); i++) {
//                    if (allAppInfo.get(i).packageName.equals("com.example.usb")) {
//                        // Intent intent = getPackageManager()
//                        // .getLaunchIntentForPackage("com.example.r02_v1");
//                        Intent intent = new Intent()
//                                .setComponent(new ComponentName(
//                                        "com.example.usb",
//                                        "com.example.usb.activity.MainActivity"));
//                        if (intent != null) {
//                            startActivity(intent);
//                        }
//                    }
//
//                }

                break;
            case R.id.btn_open_4:// 打开ATP
                for (int i = 0; i < allAppInfo.size(); i++) {
                    if (allAppInfo.get(i).packageName
                            .equals("com.val.pesticide_atp")) {
                        // Intent intent = getPackageManager()
                        // .getLaunchIntentForPackage("com.atp.atp");
                        Intent intent = new Intent()
                                .setComponent(new ComponentName(
                                        "com.val.pesticide_atp",
                                        "com.val.pesticide.atp.activity.WelcomActivity"));
                        if (intent != null) {
                            startActivity(intent);
                        }
                    }

                }

                break;
            default:
                break;
        }
    }

    /**
     * 点击两次退出程序
     */
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
