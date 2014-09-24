package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.smartsoftasia.module.gblibrary.R;


/**
 * Created by gui on 07/08/2014.
 */
public class ImageDownloader {
    private static final String TAG = "ImageDownloader";

    public static void init(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.riv_blank).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new FadeInBitmapDisplayer(250)).bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .delayBeforeLoading(100).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options).denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY + 3).memoryCache(new WeakMemoryCache()).memoryCacheExtraOptions(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                .discCacheFileCount(500).build();

        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(config);
        }
    }

    public static void init(Context context, Drawable drawable) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(drawable).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
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

    public static void loadImage(final String url, final ImageView imageView) {
        ImageLoader.getInstance().displayImage(url, imageView,null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.d("IMAGEVIEW","onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.d("IMAGEVIEW","onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.d("IMAGEVIEW","onLoadingComplete");
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.d("IMAGEVIEW","onLoadingCancelled");
            }
        });
    }

    public static void loadImage(final String url, final ImageView imageView,
                                 final ProgressBar downloadIndicator) {
        if (downloadIndicator == null) {
            ImageLoader.getInstance().displayImage(url, imageView);
        } else {
            ImageLoader.getInstance().displayImage(url, imageView, null,
                    new ImageLoadingListener() {

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
