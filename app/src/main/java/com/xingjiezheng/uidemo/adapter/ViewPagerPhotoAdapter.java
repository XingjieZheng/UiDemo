package com.xingjiezheng.uidemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by XingjieZheng on 2015/7/21.
 */
public class ViewPagerPhotoAdapter extends PagerAdapter {

    ArrayList<View> views;

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views != null ? views.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }
}
