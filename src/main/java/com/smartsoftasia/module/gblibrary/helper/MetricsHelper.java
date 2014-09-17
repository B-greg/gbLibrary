package com.smartsoftasia.module.gblibrary.helper;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by gregoire on 9/9/14.
 */
public class MetricsHelper {

    public static int getScreenWidth(Context context){
        Display display = MetricsHelper.getScreenDisplay(context);
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenHeight(Context context){
        Display display = MetricsHelper.getScreenDisplay(context);
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static Display getScreenDisplay(Context context){
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay();
    }

}
