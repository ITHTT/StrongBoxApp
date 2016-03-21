package com.htt.strongboxapp.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by HTT on 2016/3/21.
 */
public class BluetoothService extends Service {
    private final String TAG=this.getClass().getSimpleName();

    // 创建一个接收ACTION_FOUND广播的BroadcastReceiver
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"响应蓝牙搜索广播...");
            String action = intent.getAction();
            if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d(TAG,"开始搜索蓝牙设备");
            }else if (BluetoothDevice.ACTION_FOUND.equals(action)) { // 发现设备
                // 从Intent中获取设备对象
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 将设备名称和地址放入array adapter，以便在ListView中显示
                Log.d(TAG, "找到设备:" + device.getName() + ":" + device.getAddress());
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                    Log.d(TAG, "搜索结束...");
            }
        }
    };

    public static void startBluetoothService(Context context){
        Intent intent=new Intent(context,BluetoothService.class);
        Log.e("BluetoothService","开启蓝牙搜索服务");
        context.startService(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "注册蓝牙搜索广播接收者...");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
    }



    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG,"开启蓝牙搜索...");
        BluetoothAdapter mAdapter= BluetoothAdapter.getDefaultAdapter();
        if(!mAdapter.isEnabled()){
            Log.d(TAG,"蓝牙不可用...");
            mAdapter.enable();
        }else{
            Log.d(TAG,"蓝牙可用...");
        }
        mAdapter.startDiscovery();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
