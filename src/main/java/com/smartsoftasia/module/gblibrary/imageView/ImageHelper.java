package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

    public static void loadImage(final String url, final ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView);
    }

    public static void loadImage(final String url, final ImageView imageView, final ImageDownloaderListener listener) {
        ImageLoader.getInstance().displayImage(url, imageView, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason reason) {
                if (listener != null) {
                    listener.onDone(false, IMAGE_DOWNLOAD_FAIL, reason);
                }
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
                if (listener != null) {
                    listener.onDone(true, IMAGE_DOWNLOAD_SUCCESS, bitmap);
                }
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                if (listener != null) {
                    listener.onDone(false, IMAGE_DOWNLOAD_CANCEL, null);
                }
            }
        });
    }


}
