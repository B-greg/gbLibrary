package com.smartsoftasia.module.gblibrary.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gregoire on 7/28/14.
 */
public class ActivityHelper {

    public static void displayToast(String message, Context context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void alertbox(Context context ,String title, String mymessage)
    {
        new AlertDialog.Builder(context)
                .setMessage(mymessage)
                .setTitle(title)
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){}
                        })
                .show();
    }

    public static SharedPreferences.Editor writeSharedPref(Context context, String sharedPref){
        SharedPreferences settings = context.getSharedPreferences(sharedPref, 0);
        return settings.edit();
    }

    public static SharedPreferences readSharedPref(Context context, String sharedPref){
        return context.getSharedPreferences(sharedPref, 0);
    }

    /* Hide Keyboard */
    public static void hideKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager)activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        View focus = activity.getCurrentFocus();
        if(focus != null)
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    public static void startActivity(Activity activity, Class classActivity){
        Intent intent = new Intent();
        intent.setClass(activity, classActivity);
        activity.startActivity(intent);
    }
    public static void startActivityNoHistory(Activity activity, Class classActivity){
        Intent intent = new Intent();
        intent.setClass(activity, classActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }




    public static ProgressDialog displayProgressDialog(Context context,String title, String content){
        return ProgressDialog.show(context, title, content, true);
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog){
        progressDialog.dismiss();
    }


    public static void displayErrorDialog(Context context, Object respond){
        if(respond instanceof JSONObject){
            String displayError;
            try {
                JSONArray jsonArray = ((JSONObject)respond).getJSONArray("errors");
                displayError = JSONHelper.jsonStringFromJsonArray(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                displayError = "Error read json";
            }
            ActivityHelper.alertbox(context, "ERROR"  ,displayError);
        }
    }


}
