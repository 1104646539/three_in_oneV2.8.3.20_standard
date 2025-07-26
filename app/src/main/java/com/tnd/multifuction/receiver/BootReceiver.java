package com.tnd.multifuction.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tnd.FirstMainActivity;
import com.tnd.multifuction.activity.MainActivity;
import com.tnd.multifuction.resource.SPResource;

public class BootReceiver extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

//        Log.i(TAG, "接收到开机广播.....");
        Intent in = new Intent(context, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        in.putExtra("isBoot", true);
        context.startActivity(in);
    }
}
