package com.htt.strongboxapp.utils;

import android.content.Context;
import android.util.TypedValue;


/**
 * Created by HTT on 2015/5/25.
 * 像素单位转换
 */
public class DensityUtil {
    public static int dip2px(Context context,int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale + 0.5f);
    }

    public static int px2dip(Context context,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale + 0.5f);
    }

    public static float px2sp(Context context, float pxValue){
        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int sp2px(Context context, int spValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }
}
