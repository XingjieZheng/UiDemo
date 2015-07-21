package com.xingjiezheng.uidemo.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by XingjieZheng on 2015/7/21.
 */
public class SquareViewPager extends ViewPager {

    public SquareViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewPager(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
