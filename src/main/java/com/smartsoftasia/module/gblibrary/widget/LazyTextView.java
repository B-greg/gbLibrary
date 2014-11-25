package com.smartsoftasia.module.gblibrary.widget;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.smartsoftasia.module.gblibrary.helper.Validator;
import com.smartsoftasia.module.gblibrary.imageView.AbstractImageView;
import com.smartsoftasia.module.gblibrary.imageView.ImageDownloader;


/**
 * Created by gregoire on 9/21/14.
 */
public class LazyTextView extends TextView {
    public LazyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void downloadImageFromURL(String url) {
        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Drawable topImage = new BitmapDrawable(loadedImage);
                LazyTextView.this.setCompoundDrawablesWithIntrinsicBounds(null, topImage, null, null);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }


    public void downloadImageFromUri(String uri) {
        if (Validator.isValid(uri)) {
            ImageLoader.getInstance().loadImage("file:///" + uri, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    Drawable topImage = new BitmapDrawable(loadedImage);
                    LazyTextView.this.setCompoundDrawablesWithIntrinsicBounds(null, topImage, null, null);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
        }
    }
}


