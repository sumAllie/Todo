package com.example.comp7506_1.todolist.Activity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.comp7506_1.todolist.R;
import com.example.comp7506_1.todolist.Receiver.NetworkReceiver;

import cn.bmob.v3.Bmob;
import es.dmoral.toasty.Toasty;

/**
 * Activity: Obtain network status in real time
 */
public class BasicActivity extends AppCompatActivity {
    private boolean isRegistered = false;
    private NetworkReceiver networkReceiver;
    private static final String APP_ID = "326f04efe6b199580a43c98b4ae5c396";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.resetDomain("https://open3.bmob.cn/8/");
        Bmob.initialize(getApplication(), APP_ID);
        //注册网络状态监听广播
        networkReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
        isRegistered = true;

        Toasty.Config.getInstance()
                .setSuccessColor(getResources().getColor(R.color.toastSuccess))
                .setErrorColor(getResources().getColor(R.color.toastError))
                .setInfoColor(getResources().getColor(R.color.toastInfo))
                .apply();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑
        if (isRegistered) {
            unregisterReceiver(networkReceiver);
        }
    }
}
