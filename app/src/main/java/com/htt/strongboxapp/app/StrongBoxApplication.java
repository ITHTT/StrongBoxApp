package com.htt.strongboxapp.app;

import android.app.Application;
import android.content.Intent;

import com.htt.strongboxapp.service.BluetoothService;

/**
 * Created by Administrator on 2016/3/21.
 */
public class StrongBoxApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothService.startBluetoothService(this);
    }
}
