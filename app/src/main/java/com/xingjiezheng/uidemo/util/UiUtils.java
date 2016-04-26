package com.xingjiezheng.uidemo.util;

import android.content.Context;

/**
 * Created by xj
 * on 2016/4/6.
 */
public class UiUtils {

    private UiUtils() {

    }

    public static int dip2px(Context context, float dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip + 0.5f);
    }
}
