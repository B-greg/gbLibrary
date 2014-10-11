package com.smartsoftasia.module.gblibrary.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.smartsoftasia.module.gblibrary.imageView.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class ImageHelper {

    public interface ImageDownloaderListener {
        public void onDone(boolean success, int result, Object response);
    }

    public static final int IMAGE_DOWNLOAD_SUCCESS = 1;
    public static final int IMAGE_DOWNLOAD_FAIL = 2;
    public static final int IMAGE_DOWNLOAD_CANCEL = 3;

    public static void init(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().imageScaleType(ImageScaleType.IN_SAMPLE_INT).displayer(new FadeInBitmapDisplayer(250)).bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY + 3).memoryCache(new WeakMemoryCache()).discCacheFileCount(1000).build();

        ImageLoader.getInstance().init(config);
    }

    public static Bitmap applyGaussianBlur(Drawable drawable, int radius) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }

        return applyGaussianBlur(bitmap, radius);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height, boolean keepRatio) {
        if (keepRatio) {
            float widthRatio = width / bitmap.getWidth();
            float heightRatio = height / bitmap.getHeight();

            if ((widthRatio > 0 && widthRatio < 1) || (heightRatio > 0 && heightRatio < 1)) {
                float ratio;
                if (widthRatio > heightRatio) {
                    ratio = heightRatio;
                } else {
                    ratio = widthRatio;
                }

                width = (int) (ratio * width);
                height = (int) (ratio * height);
            }
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public static Bitmap applyGaussianBlur(Bitmap bitmap, int radius) {
        if (radius < 1 || bitmap == null) {
            return bitmap;
        }

        int maxWidth;
        int maxHeight;

        if (DeviceHelper.isPhone()) {
            maxWidth = 320;
            maxHeight = 568;
        } else {
            maxWidth = 568;
            maxHeight = 320;
        }

        // Bitmap bm = scaleBitmap(bitmap.copy(bitmap.getConfig(), true),
        // maxWidth, maxHeight, true);
        Bitmap bm = scaleBitmap(bitmap.copy(bitmap.getConfig(), true), maxWidth, maxHeight, true);

        int w = bm.getWidth();
        int h = bm.getHeight();

        int[] pix = new int[w * h];
        bm.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bm.setPixels(pix, 0, w, 0, 0, w, h);

        return bm;
    }


    public static void downloadImagesToSdCard(String downloadUrl, String path) throws IOException {
            URL url = new URL(downloadUrl);
            File file;
            file = new File(path);

            file.getParentFile().mkdirs();
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);


                             /* Open a connection */
            URLConnection ucon = url.openConnection();
            InputStream inputStream = null;
            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }

            FileOutputStream fos = new FileOutputStream(file);
            int totalSize = httpConn.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            try {
                while ((bufferLength = inputStream.read(buffer))  > 0) {
                    fos.write(buffer, 0, bufferLength);
                    downloadedSize += bufferLength;
                    Log.i("downloadImagesToSdCard::Progress", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
                }

            }catch (Exception e){
                Log.e("downloadImagesToSdCard::Error",e.toString());
            }


            fos.close();
            Log.d("downloadImagesToSdCard::Final", "Image Saved in sdcard..");
    }
}
