package com.htt.strongboxapp.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/3/28.
 */
public class SoftInputUtil {

    public static void hideSoftInputFromWindow(Context context,EditText editText){
        InputMethodManager imm =(InputMethodManager)context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}
