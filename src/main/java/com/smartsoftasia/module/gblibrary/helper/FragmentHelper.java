package com.smartsoftasia.module.gblibrary.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.util.Log;

/**
 * Created by gregoire on 9/18/14.
 */
public class FragmentHelper extends ActivityHelper {

    public final static String TAG = "FragmentHelper";

    protected Fragment fragment;

    public FragmentHelper(Activity activity, Fragment fragment) {
        super(activity);
        this.fragment = fragment;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void actionBarSetup(String string) {
        if(activity == null || fragment == null){
            Log.e(TAG, "Fragment Helper not instantiate" );
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.app.ActionBar ab = activity.getActionBar();
            if(ab != null){
                ab.setTitle(StringHelper.replaceNull(string));
                ab.setHomeButtonEnabled(true);
                ab.setDisplayHomeAsUpEnabled(true);
            }
        }
        fragment.setHasOptionsMenu(true);
    }

    public static void replaceFragmentWithoutStack(Activity activity, Fragment fragment, int id, String tag) {
        if(activity == null){
            Log.e(TAG, "Fragment Helper not instantiate" );
            return;
        }
        activity.getFragmentManager()
                .beginTransaction()
                .replace(id, fragment, tag)
                .commit();
    }

    public static void replaceFragmentStack(Activity activity, Fragment fragment, int id, String tag) {
        if(activity == null){
            Log.e(TAG, "Fragment Helper not instantiate");
            return;
        }
        activity.getFragmentManager()
                .beginTransaction()
                .replace(id, fragment, tag)
                .addToBackStack(tag)
                .commit();
    }

}
