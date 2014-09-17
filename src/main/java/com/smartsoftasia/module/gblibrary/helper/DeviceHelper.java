package com.smartsoftasia.module.gblibrary.helper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public final class DeviceHelper extends Application {
    private static Context context;
    private static boolean phone;
    private static double widthRatio = 0;
    private static double heightRatio = 0;

    protected static final String PREFS_FILE = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";

    protected volatile static UUID uuid;

    public static void init(Context _context, boolean _phone) {
        context = _context;
        phone = _phone;
    }

    public static boolean isPhone() {
        return phone;
    }

    public static boolean isTablet() {
        return !phone;
    }

    public static UUID getUniqueIdentifier() {

        if (uuid == null) {
            final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
            final String id = prefs.getString(PREFS_DEVICE_ID, null);

            if (id != null) {
                uuid = UUID.fromString(id);
            } else {

                final String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

                try {
                    if (!"9774d56d682e549c".equals(androidId)) {
                        uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                    } else {
                        final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                        uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

                prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();

            }

        }

        return uuid;
    }

    public static boolean hasOnScreenSystemBar() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = wm.getDefaultDisplay();
        int deviceDisplayHeight = 0;
        try {
            Method getRawHeight = Display.class.getMethod("getRawHeight");
            deviceDisplayHeight = (Integer) getRawHeight.invoke(d);
        } catch (Exception ex) {

        }

        Point s = new Point();
        d.getSize(s);
        int windowHeight = s.y;

        return deviceDisplayHeight - windowHeight > 0;
    }

    public static int getStatusBarHeight() {
        int result = 0;

        if (isPhone() || !hasOnScreenSystemBar()) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }

        return result;
    }

    public static int getDisplayWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getDisplayHeight() {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayableHeight() {
        return getDisplayHeight() - getStatusBarHeight();
    }

    public static float getDisplayDensity() {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getDisplayDPI() {
        return (int) (getDisplayDensity() * 160);
    }

    public static String getBundleVersion() {
        PackageInfo pInfo;
        String version = new String();
        try {
            PackageManager packageManager = context.getPackageManager();
            pInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public static String getDeviceName() {
        String deviceName = android.os.Build.MODEL;
        if (deviceName.equals("sdk")) {
            return "Android Simulator";
        } else {
            return deviceName;
        }
    }

    public static String getAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static int getAndroidSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static double getWidthRatio() {
        if (widthRatio == 0) {
            widthRatio = getDisplayWidth() / (isPhone() ? 320.0 : 1024.0);
        }
        return widthRatio;
    }

    public static double getHeightRatio() {
        if (heightRatio == 0) {
            heightRatio = getDisplayHeight() / (isPhone() ? 568.0 : 768.0);
        }
        return heightRatio;
    }

    public static void hideKeyboard(Window window) {
        if (window != null && window.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isAppInstalled(String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

}
