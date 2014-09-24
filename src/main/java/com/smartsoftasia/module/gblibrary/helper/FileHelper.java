package com.smartsoftasia.module.gblibrary.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public final class FileHelper {

    private static String externalDirectoryPath(Context context, String directoryName) {
        return context.getExternalFilesDir(null) + "/" + directoryName;
    }

    private static boolean outputStringToFile(Context context, String fileName, String value, int mode) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, mode);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(value);
            osw.flush();
            osw.close();
        }

        catch (IOException e) {
            return false;
        }

        return true;
    }

    public static boolean fileExists(Context context, String fileName) {
        File file = context.getFileStreamPath(fileName);
        return file.exists();
    }

    public static boolean fileExists(Context context, String directoryName, String fileName) {
        File file = new File(externalDirectoryPath(context, directoryName), fileName);
        return file.exists();
    }

    public static String readStringFromFile(Context context, String fileName) {

        StringBuffer sb = new StringBuffer();
        String inputLine = new String();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
                sb.append("\n");
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

    }

    public static boolean appendStringToFile(Context context, String fileName, String value) {
        return outputStringToFile(context, fileName, value, Context.MODE_APPEND);
    }

    public static boolean writeStringToFile(Context context, String fileName, String value) {
        return outputStringToFile(context, fileName, value, Context.MODE_PRIVATE);
    }

    public static void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
    }

    public static void saveImageToFile(Context context, String directoryName, String fileName, Bitmap image) throws IOException {
        if (directoryName == null) {
            directoryName = "";
        }

        File directory = new File(externalDirectoryPath(context, directoryName));
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory, fileName);
        BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream(file));
        image.compress(Bitmap.CompressFormat.JPEG, 100, buf);
        buf.flush();
        buf.close();
    }

    public static Bitmap getImageFromFile(Context context, String directoryName, String fileName) throws IOException {
        if (directoryName == null) {
            directoryName = "";
        }

        File file = new File(externalDirectoryPath(context, directoryName), fileName);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        try {
            Bitmap image = BitmapFactory.decodeStream(bis);
            bis.close();
            return image;
        } catch (Throwable e) {
            e.printStackTrace();
            if (e instanceof OutOfMemoryError) {
                e.printStackTrace();
            }

            bis.reset();
            return null;
        }
    }

    public static void writeSerializableObjectsToFile(Context context, ArrayList<Serializable> objects, String fileName) throws IOException {
        FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutput oo = new ObjectOutputStream(fos);
        oo.writeObject(objects);
        oo.close();
    }

    /**
     * Create the Directory in the external sdcard if he not exist.
     * @param path Full path of the directory witout external sdcard part.
     * @return True if file exist or create / false if error
     */
    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;
        Log.d("createDirIfNotExists", "path : " + path);
        Log.d("createDirIfNotExists", "Full path : "+ Environment.getExternalStorageDirectory().getPath()+path);
        File file = new File(Environment.getExternalStorageDirectory(), path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Image folder");
                ret = false;
            }
        }
        return ret;
    }



}
