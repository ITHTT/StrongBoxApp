package com.htt.strongboxapp.utils;

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

}
