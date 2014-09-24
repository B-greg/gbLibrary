package com.smartsoftasia.module.gblibrary.imageView;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by gregoire on 9/4/14.
 */
public class GbSquareImageView extends AbstractImageView {

    public GbSquareImageView(Context context) {
        super(context);
    }

    public GbSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = getMeasuredWidth();

         setMeasuredDimension(measuredWidth, measuredWidth);

    }

}
