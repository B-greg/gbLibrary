package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

/**
 * Created by gregoire on 10/20/14.
 */
public class PicassoHandler {

    private Picasso picasso;
    private LruCache cache;
    private static PicassoHandler instance;


    private PicassoHandler(Context context) {
        cache = new LruCache(context);
        picasso = new Picasso.Builder(context)
                .memoryCache(cache)
                //.memoryCache(new LruCache(512))
                .executor(Executors.newSingleThreadExecutor())
                .build();
    }

    public static PicassoHandler getInstance(Context context) {
        if(instance == null){
            instance = new PicassoHandler(context);
        }
        return instance;
    }

    public Picasso getPicasso() {
        return picasso;
    }


    public LruCache getCache() {
        return cache;
    }
}
