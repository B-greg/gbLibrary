package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;


/**
 * Created by gregoire on 9/4/14.
 */
public abstract class AbstractImageView extends ImageView {
    protected static final String TAG = "AbstractImageView";

    protected float heightByWidth = 0;
    protected float widthByHeight = 0;

    public AbstractImageView(Context context) {
        super(context);
    }

    public AbstractImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

/*        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LayoutRatio);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.LayoutRatio_heightByWidth:
                    heightByWidth = a.getFloat(attr, 0);
                    break;
                case R.styleable.LayoutRatio_widthByHeight:
                    widthByHeight = a.getFloat(attr, 0);
                    break;
                case R.styleable.LayoutRatio_squareByWidth:
                    heightByWidth = 1;
                    break;
                case R.styleable.LayoutRatio_squareByHeight:
                    widthByHeight = 1;
                    break;
            }
        }
        a.recycle();*/

    }

    public AbstractImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);}


    public void setWidth(double w) {
        getLayoutParams().width = (int) w;
    }

    public void setHeight(double h) {
        getLayoutParams().height = (int) h;
    }

    public void setSize(double w, double h) {
        getLayoutParams().width = (int) w;
        getLayoutParams().height = (int) h;
    }

    public void downloadImageFromURL(String url) {
        if (Validator.isValid(url)) {
            PicassoHandler.getInstance(getContext()).getPicasso().with(getContext())
                    .load(url)
                    .into(this);
        }
    }

    public void downloadImageFromFile(File file) {
        if(file == null)return;
        PicassoHandler.getInstance(getContext()).getPicasso().with(getContext())
                    .load(file)
                    .into(this);
    }

    public void downloadImageFromURL(String url, ProgressBar downloadIndicator) {
        if (Validator.isValid(url)) {
            ImageDownloader.loadImage(url, this, downloadIndicator);
        } else {
            downloadIndicator.setVisibility(View.GONE);
        }
    }

    public void setHeightByWidth(float _heightByWidth) {
        heightByWidth = _heightByWidth;
    }

    public void setWidthByHeight(float _widthByHeight) {
        widthByHeight = _widthByHeight;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int nw, int nh, int ow, int oh) {
        super.onSizeChanged(nw, nh, ow, oh);
    }

    Transformation transformation = new Transformation() {

        @Override public Bitmap transform(Bitmap source) {
            int targetWidth = source.getHeight() / 4;

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "transformation" + " desiredWidth";
        }
    };

}
