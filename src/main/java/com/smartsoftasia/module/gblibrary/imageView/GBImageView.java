package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by gui on 07/08/2014.
 */
public class GBImageView extends AbstractImageView{
    private static final String TAG = "GBImageView";

    public GBImageView(Context context) {
        super(context);
    }

    public GBImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (widthByHeight != 0) {
            width = (int) (height * widthByHeight);
        } else if (heightByWidth != 0) {
            height = (int) (width * heightByWidth);
        }

        setMeasuredDimension(width, height);
    }

}
