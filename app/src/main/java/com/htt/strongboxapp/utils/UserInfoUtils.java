package com.htt.strongboxapp.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class UserInfoUtils {
    private static final String userFileName="user_info";

    private static final String USER_TOKEN="user_token";
    private static final String USER_ACCOUNT="user_account";
    private static final String USER_ID="usr_id";

    private static final String USER_PHONE="user_phone";
    private static final String USER_NAME="user_name";

    private static final String USER_IDCARD="user_idcard";

    public static void setUserToken(Context context,String token){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_TOKEN,token);
    }

    public static String getUserToken(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_TOKEN);
    }

    public static void setUserIDCard(Context context ,String IDCard){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_IDCARD,IDCard);
    }

    public static String getUserIDCard(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_IDCARD);
    }

    public static void setUserId(Context context,String id){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_ID,id);
    }

    public static String getUserId(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_ID);
    }

    public static void setUserName(Context context,String userName){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_NAME,userName);
    }

    public static String getUserName(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_NAME);
    }

    public static void setUserPhone(Context context,String userPhone){
        SharedPreferencesUtil.setStringValue(context,userFileName,USER_PHONE,userPhone);
    }

    public static String getUserPhone(Context context){
        return SharedPreferencesUtil.getStringValue(context,userFileName,USER_PHONE);
    }


    public static void exitLogin(Context context){
        //setUserAccount(context,"");
        setUserId(context, "");
        setUserToken(context, "");
        setUserIDCard(context, "");
        setUserName(context,"");
        setUserPhone(context,"");
    }

    public static boolean isLogined(Context context){
        String userId=getUserId(context);
        String cardId=getUserId(context);
        String userName=getUserName(context);
        String userPhone=getUserPhone(context);
        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(cardId)&&!TextUtils.isEmpty(userName)&&!TextUtils.isEmpty(userPhone)){
            return true;
        }
        return false;
    }




}
