package com.htt.strongboxapp.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.socks.library.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/28.
 */
public class CommonUtils {

    public static boolean isCardId(String cardId)
    {
        Pattern pattern15=Pattern.compile("\"^[1-9]\\d{7}((0\\[1-9])|(1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{2}([0-9]|x|X){1}$\"");
        Pattern pattern18=Pattern.compile("\"^[1-9]\\d{5}[1-9]\\d{3}((0\\[1-9]))|((1[0-2]))(([0\\[1-9]|1\\d|2\\d])|3[0-1])\\d{3}([0-9]|x|X){1}$");

        if(pattern15.matcher(cardId).matches()||pattern18.matcher(cardId).matches()){
            return true;
        }
        return false;
    }

    public static final boolean setStatusBarDarkMode(boolean darkmode, Activity activity) {
        if ("Xiaomi".equalsIgnoreCase(Build.BRAND)) {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            try {
                int darkModeFlag = 0;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
                return true;
            } catch (Exception e) {
                KLog.e("Xiaomi setStatusBarDarkIcon: failed");
                return false;
            }
        } else if ("MeiZu".equalsIgnoreCase(Build.BRAND)) {
            Window window = activity.getWindow();
            if (window != null) {
                try {
                    WindowManager.LayoutParams lp = window.getAttributes();
                    Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                    Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                    darkFlag.setAccessible(true);
                    meizuFlags.setAccessible(true);
                    int bit = darkFlag.getInt(null);
                    int value = meizuFlags.getInt(lp);
                    if (darkmode) {
                        value |= bit;
                    } else {
                        value &= ~bit;
                    }
                    meizuFlags.setInt(lp, value);
                    window.setAttributes(lp);
                    return true;
                } catch (Exception e) {
                    KLog.e("MeiZu setStatusBarDarkIcon: failed");
                    return false;
                }
            }
        }
        return false;
    }


}
