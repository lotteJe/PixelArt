package com.example.android.pixelart.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by lottejespers.
 */
// source http://www.gadgetsaint.com/tips/dynamic-square-rectangular-layouts-android/
public class DynamicSquareLayout extends RelativeLayout {

    public DynamicSquareLayout(Context context) {
        super(context);
    }


    public DynamicSquareLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DynamicSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            int size = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(size, size);
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
            int size = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(size, size);
        }
    }


}