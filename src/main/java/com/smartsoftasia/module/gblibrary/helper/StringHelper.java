package com.smartsoftasia.module.gblibrary.helper;

/**
 * Created by gregoire on 8/26/14.
 */
public class StringHelper {

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
}
