package com.xingjiezheng.uidemo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingjiezheng.uidemo.R;
import com.xingjiezheng.uidemo.adapter.ViewPagerPhotoAdapter;
import com.xingjiezheng.uidemo.widget.SquareViewPager;

import java.util.ArrayList;


public class ViewPagerActivity extends AppCompatActivity {

    private SquareViewPager viewPager;
    private ViewPagerPhotoAdapter adapter;

    private ArrayList<View> views;

    private LinearLayout layoutPagerNo;
    private ArrayList<TextView> viewsPagerNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        init();
        findView();
        setView();
        setListener();
    }

    private void init() {
        viewsPagerNo = new ArrayList<>();
        views = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.viewpager_item, null);
            views.add(view);
        }
    }

    private void findView() {
        layoutPagerNo = (LinearLayout) findViewById(R.id.layoutPagerNo);
        viewPager = (SquareViewPager) findViewById(R.id.viewPagePhoto);
        adapter = new ViewPagerPhotoAdapter();
        viewPager.setAdapter(adapter);
    }

    private void setView() {
        adapter.setViews(views);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < 5; i++) {
            TextView textView = new TextView(this);
            textView.setText(String.valueOf(i + 1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            layoutPagerNo.addView(textView, params);
            viewsPagerNo.add(textView);
        }
    }

    private void setListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < 5; i++) {
                    if (position == i) {
                        viewsPagerNo.get(i).setTextColor(0xffff0000);
                    } else {
                        viewsPagerNo.get(i).setTextColor(0xff000000);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
