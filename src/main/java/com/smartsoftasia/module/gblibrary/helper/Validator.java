package com.smartsoftasia.module.gblibrary.helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by gui on 07/08/2014.
 */
public class Validator {
    private static final String TAG = "Validator";

    public static final String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";

    public static boolean isValid(String string) {
        return string != null && !string.trim().equals("") && !string.trim().equals("null");
    }

    public static String validate(String string) {
        if( string == null || string.trim().equals("") || string.trim().equals("null")){
            return "";
        }else{
            return string;
        }
    }

    public static String validateUrl(String url){
        if(url !=null){
            if (!url.startsWith("http://") && !url.startsWith("https://")){
                url = "http://" + url;
            }
        }else{
            url = "http://";
        }
        return url;
    }

    public static boolean isValidEmail(String email, boolean allowBlank) {
        return !isValid(email) ? allowBlank : android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password){
        char[] aC = password.toCharArray();
        for(char c : aC)
            if (SPECIAL_CHARACTERS.indexOf(String.valueOf(c)) >= 0) return true;
        return false;
    }

    public static boolean isValidLenght(String value, int maxValue, int minValue){
        if((maxValue != 0 && value.length() > maxValue) || (minValue != 0 && value.length() < minValue)) return false;
        return true;
    }

    /**
     * Delete + and spaces in the phone number
     * @param phone
     * @return
     */
    public static String changePhone(String phone){
        if(phone.startsWith("+")) return phone.substring(1).replaceAll(" ", "");
        else return phone.replaceAll(" ", "");
    }

    public static boolean isSmallScreen(Context c){
        return (c.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_SMALL;
    }
    public static boolean isNormalScreen(Context c){
        return (c.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_NORMAL;
    }

    public static boolean isLandscape(Context c){
        return c.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
    }
}
