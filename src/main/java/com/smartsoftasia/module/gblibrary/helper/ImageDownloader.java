package com.smartsoftasia.module.gblibrary.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by gui on 07/08/2014.
 */
public class ImageDownloader {
    private static final String TAG = "ImageDownloader";

    public static void init(Context context) {
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.blank).showImageForEmptyUri(R.drawable.blank)
//                .showImageOnFail(R.drawable.blank).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
//                .displayer(new FadeInBitmapDisplayer(250)).bitmapConfig(Bitmap.Config.RGB_565)
//                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
//                .delayBeforeLoading(100).build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .defaultDisplayImageOptions(options).denyCacheImageMultipleSizesInMemory()
//                .threadPriority(Thread.NORM_PRIORITY + 3).memoryCache(new WeakMemoryCache()).memoryCacheExtraOptions(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
//                .discCacheFileCount(500).build();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .delayBeforeLoading(100)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(250))
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY + 2) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheExtraOptions(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                .diskCache(new UnlimitedDiscCache(cacheDir)) // default
                .writeDebugLogs()
                .build();

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(config);
        }
    }

    public static void init(Context context, Drawable drawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable).showImageForEmptyUri(drawable)
                .showImageOnFail(drawable).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new FadeInBitmapDisplayer(250)).bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true).cacheOnDisc(true).delayBeforeLoading(100).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options).denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY + 3).memoryCache(new WeakMemoryCache())
                .discCacheFileCount(500).build();

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(config);
        }
    }

    public static void loadImage(final String url, final ImageView imageView, final Drawable drawable) {
        ImageLoader.getInstance().displayImage(url, imageView,null, new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.d("IMAGEVIEW","onLoadingStarted");
                ImageView imageView = (ImageView) view;
                if(imageView!=null)imageView.setImageDrawable(drawable);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.d("IMAGEVIEW","onLoadingFailed");
                ImageView imageView = (ImageView) view;
                if(imageView!=null)imageView.setImageDrawable(drawable);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.d("IMAGEVIEW","onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.d("IMAGEVIEW","onLoadingCancelled");
                //ImageView imageView = (ImageView) view;
                //if(imageView!=null)imageView.setImageDrawable(drawable);
            }
        });
    }

    public static void loadImage(final String url, final ImageView imageView,
                                 final ProgressBar downloadIndicator) {
        if (downloadIndicator == null) {
            ImageLoader.getInstance().displayImage(url, imageView);
        } else {
            ImageLoader.getInstance().displayImage(url, imageView, null,
                    new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {

                        @Override
                        public void onLoadingStarted(String arg0, View arg1) {

                        }

                        @Override
                        public void onLoadingFailed(String arg0, View arg1, FailReason fail) {
                            downloadIndicator.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                            downloadIndicator.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingCancelled(String arg0, View arg1) {
                            downloadIndicator.setVisibility(View.GONE);
                        }
                    });
        }
    }


}
