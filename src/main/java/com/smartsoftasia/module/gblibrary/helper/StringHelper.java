package com.smartsoftasia.module.gblibrary.helper;

import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by gregoire on 8/26/14.
 */
public class StringHelper {

    public static final String TAG = "StringHelper";

    public static String replaceNull(String input) {
        return input == null ? "" : input;
    }

    public static Boolean isNull(String input) {
        if(input == null || input.equals("null")){
            return true;
        }else{
            return false;
        }
    }

    public static String byteArrayToString(byte[] content){
        String stringContent = "";
        if(content != null){
            try {
                stringContent = new String(content, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return stringContent;
    }

    public static String getFirstWord(String input){
        String s = input;
        if (input!=null){
            if(input.contains(" ")) s=input.substring(0,  input.indexOf(" "));
        } else {
            s="";
        }
        return s;
    }
}
