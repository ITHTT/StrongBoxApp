package com.htt.strongboxapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/1/13.
 */
public class ToastMsgUtil {

    public static void toastMsg(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
