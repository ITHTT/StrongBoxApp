package com.htt.strongboxapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * 页面跳转工具
 * Created by Administrator on 2015/10/8.
 */
public class ActivitySkipUtils {
    public static void skipActivity(Activity activity, Class<?> cla) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        intent.setClass(activity, cla);
        activity.startActivity(intent);
    }

    public static void skipActivity(Fragment fragment, Class<?> cla){
        Intent intent=new Intent();
        intent.setClass(fragment.getActivity(), cla);
        fragment.startActivity(intent);
    }

    public static void skipActivity(Fragment fragment,Class<?>cla,String key,String value,int requestCode){
        Intent intent=new Intent(fragment.getContext(),cla);
        intent.putExtra(key, value);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void skipActivity(Fragment fragment,Class<?>cla,String key,long value,int requestCode){
        Intent intent=new Intent(fragment.getContext(),cla);
        intent.putExtra(key,value);
        fragment.startActivityForResult(intent,requestCode);
    }

    public static void skipActivity(Context context,Class<?>cla,Parcelable value){
        Intent intent=new Intent(context,cla);
        intent.putExtra(cla.getSimpleName(),value);
        context.startActivity(intent);
    }

    public static void skipActivityForResult(Fragment fragment,Class<?>cla,int requestCode){
        Intent intent=new Intent(fragment.getActivity(),cla);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void skipActivity(Context context,Class<?>cla,String value){
        Intent intent=new Intent(context,cla);
        intent.putExtra(cla.getSimpleName(),value);
        context.startActivity(intent);
    }

    public static void skipActivity(Activity activity, Class<?> cla, Bundle data) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        intent.setClass(activity, cla);
        intent.putExtras(data);
        activity.startActivity(intent);
    }

    public static void skipActivity(Fragment fragment,Class<?>cla,Bundle data){
        Intent intent=new Intent();
        intent.setClass(fragment.getActivity(), cla);
        intent.putExtras(data);
        fragment.startActivity(intent);
    }

    public static void skipActivityForResult(Activity activity, Class<?> cla,
                                             int requestCode, Bundle data) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        intent.setClass(activity, cla);
        if(data!=null)
            intent.putExtras(data);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void skipActivityForResult(Activity activity, Class<?> cla,
                                               int requestCode) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        intent.setClass(activity,cla);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void skipActivityForResult(Activity activity, Class<?> cla,String data,
                                             int requestCode) {
        // TODO Auto-generated method stub
        Intent intent=new Intent();
        intent.setClass(activity,cla);
        intent.putExtra(cla.getSimpleName(),data);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void skipActivityForResult(Fragment fragment,Class<?>cla,int requestCode,Bundle data){
        Intent intent=new Intent();
        intent.setClass(fragment.getActivity(),cla);
        if(data!=null)
            intent.putExtras(data);
        fragment.startActivityForResult(intent, requestCode);
    }

}
