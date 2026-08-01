package com.tnd.jinbiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.tnd.jinbiao.model.PeopleModel;
import com.tnd.jinbiao.model.ResultModel;
import com.tnd.multifuction.R;
import com.tnd.multifuction.db.DbHelper;
import com.tnd.multifuction.model.CheckOrg;
import com.tnd.multifuction.model.Inspector;
import com.tnd.multifuction.model.Print;
import com.tnd.multifuction.resource.SPResource;
import com.tnd.multifuction.util.Global;
import com.tnd.multifuction.util.SPUtils;
import com.tnd.multifuction.util.SerialUtils;
import com.tnd.multifuction.util.ToolUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class JbMainActivity extends Activity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jb_main);
        DbHelper.InitDb(getApplicationContext());
        try {
            SerialUtils.InitSerialPort(this);
        } catch (UnsatisfiedLinkError e) {
            Log.i("JbMainActivity", "onCreate: 串口打开失败");
        }
        initSP();

    }
    private String GetCurrentTime(){

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
        return dateFormat.format(now);

    }

    private void initSP() {
        sp = getSharedPreferences(SPResource.FILE_NAME, Context.MODE_PRIVATE);

//        try {
//            PeopleModel check = new PeopleModel();
//            check.source = 2;
//            check.name = "黄文祥";
//            DbHelper.GetInstance().save(check);
//            PeopleModel twh = new PeopleModel();
//            twh.source = 3;
//            twh.name = "10-32";
//            DbHelper.GetInstance().save(twh);
//        } catch (DbException e) {
//            throw new RuntimeException(e);
//        }
//        Global.uploadUrl = sp.getString(SPResource.KEY_UPLOAD_URL, "https://qzsp.leadall.net/data/reception/detectData");
//        Global.TESTING_UNIT_NAME = sp.getString(SPResource.KEY_UPLOAD_USERNAME, "cs002");
//        Global.TESTING_UNIT_NUMBER = sp.getString(SPResource.KEY_UPLOAD_PASSWORD, "testwe2023");
//        Global.ASSET_NAME = sp.getString(SPResource.ASSET_NAME,"1号");
        Global.ASSET_CODE = sp.getString(SPResource.ASSET_CODE, "001");
    }


    public void ClickCheck(View v) { //样品检测
        Intent intent = new Intent(this, CheckSelectProjectActivity.class);
        startActivity(intent);
    }

    public void ClickProject(View v) { //项目管理

        Intent intent = new Intent(this, ProjectManagerActivity.class);
        startActivity(intent);
    }

    public void ClickSelect(View v) {  //功能选项

        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void ClickExit(View v) {   //进出卡
        SerialUtils.CardOutOrIn();
//		ToolUtils.OpenSerialPort(getApplicationContext(), "Card_OutorIn",3);
    }

}
