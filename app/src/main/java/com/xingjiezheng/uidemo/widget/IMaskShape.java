package com.xingjiezheng.uidemo.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.SparseArray;

/**
 * Created by xj
 * on 2016/4/25.
 */
public interface IMaskShape {

    int INDEX_0 = 0;
    int INDEX_1 = 1;

    Rect getRect();

    void draw(Canvas canvas);

    SparseArray<Rect> getIndexList();


}
