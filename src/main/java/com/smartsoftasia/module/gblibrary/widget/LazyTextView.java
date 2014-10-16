package com.smartsoftasia.module.gblibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.smartsoftasia.module.gblibrary.imageView.Validator;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by gregoire on 9/21/14.
 */
public class LazyTextView extends TextView implements Target {
    public LazyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void downloadImageFromURL(String url){
        if (Validator.isValid(url)) {
            Picasso.with(getContext())
                    .load(url)
                    .into(this);
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        this.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(getResources(), bitmap), null, null, null);

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
